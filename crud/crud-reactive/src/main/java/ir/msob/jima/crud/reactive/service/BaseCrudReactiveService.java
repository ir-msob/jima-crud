package ir.msob.jima.crud.reactive.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.crud.api.service.BaseCrudServiceParent;
import ir.msob.jima.crud.reactive.service.domain.operation.ReactiveLifecycleOperationComponent;
import ir.msob.jima.platform.api.element.criteria.BaseElementCriteria;
import ir.msob.jima.platform.api.element.dto.BaseElementDto;
import ir.msob.jima.platform.api.element.element.BaseElement;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.exception.validation.ValidationException;
import ir.msob.jima.platform.api.safemodify.IdempotencyKeyUtil;
import ir.msob.jima.platform.api.safemodify.SafeSave;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import ir.msob.jima.platform.reactive.operation.BaseReactiveDomainLifecycleOperation;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.reactive.service.BaseReactiveService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * This interface defines common CRUD operations for a domain entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user context.
 * @param <D>    The type of domain entity.
 * @param <DTO>  The type of DTO (Data Transfer Object) associated with the domain entity.
 * @param <C>    The type of criteria used for querying domain entities.
 * @param <R>    The type of repository for the domain entity.
 */
public interface BaseCrudReactiveService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseElement<ID>,
        DTO extends BaseElementDto<ID>,
        C extends BaseElementCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>>
        extends BaseReactiveService<ID, USER, D, DTO, C, R>,
        BaseCrudServiceParent<ID, USER, D, DTO, C, R> {

    // ---------------------------
    // CRUD entry points
    // ---------------------------

    /**
     * Wrap a blocking supplier in a Mono and schedule on boundedElastic.
     */
    private static <T> Mono<T> runBlocking(Callable<T> callable) {
        return Mono.fromCallable(callable)
                .subscribeOn(Schedulers.boundedElastic());
    }

    default Mono<@NonNull Long> doCount(C criteria, USER user) {
        return beforeCount(criteria, user)
                .then(this.getRepository().count(criteria))
                .flatMap(count -> afterCount(criteria, user).thenReturn(count));
    }

    default Mono<@NonNull DTO> doGetOne(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return beforeGet(criteria, user)
                .then(this.preGet(criteria, user))
                .then(this.getRepository().getOne(criteria))
                .flatMap(domain -> convertDomainToDto(domain, user)
                        .flatMap(dto -> {
                            Collection<ID> ids = Collections.singleton(domain.getId());
                            Collection<DTO> dtos = Collections.singleton(dto);
                            return postGet(ids, dtos, criteria, user)
                                    .then(afterGet(ids, dtos, criteria, user))
                                    .thenReturn(dto);
                        })
                );
    }

    default Mono<@NonNull Collection<DTO>> doGetMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return beforeGet(criteria, user)
                .then(this.preGet(criteria, user))
                .thenMany(this.getRepository().getMany(criteria))
                .concatMap(domain -> convertDomainToDto(domain, user))
                .collectList()
                .flatMap(dtos -> {
                    Collection<ID> ids = prepareIds(dtos);
                    return postGet(ids, dtos, criteria, user)
                            .then(afterGet(ids, dtos, criteria, user))
                            .thenReturn(dtos);
                });
    }

    default Mono<@NonNull Page<@NonNull DTO>> doGetPage(C criteria, Pageable pageable, USER user) throws DomainNotFoundException, BadRequestException {
        return beforeGet(criteria, user)
                .then(this.preGet(criteria, user))
                .then(this.getRepository().getPage(criteria, pageable))
                .flatMap(domainPage ->
                        runBlocking(() -> domainPage.stream().map(domain -> toDto(domain, user)).toList())
                                .flatMap(dtos -> {
                                    Page<DTO> dtoPage = new PageImpl<>(dtos, domainPage.getPageable(), domainPage.getTotalElements());
                                    Collection<ID> ids = prepareIds(dtos);
                                    return postGet(ids, dtos, criteria, user)
                                            .then(afterGet(ids, dtos, criteria, user))
                                            .thenReturn(dtoPage);
                                })
                );
    }

    default Flux<@NonNull DTO> doGetStream(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return beforeGet(criteria, user)
                .thenMany(this.preGet(criteria, user))
                .thenMany(this.getRepository().getMany(criteria))
                .concatMap(domain -> convertDomainToDto(domain, user)
                        .flatMap(dto -> {
                            Collection<DTO> dtos = Collections.singleton(dto);
                            Collection<ID> ids = Collections.singleton(domain.getId());
                            return postGet(ids, dtos, criteria, user)
                                    .then(afterGet(ids, dtos, criteria, user))
                                    .thenReturn(dto);
                        })
                );
    }

    default Mono<@NonNull ID> doDelete(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return beforeDelete(criteria, user)
                .then(doGetOne(criteria, user))
                .flatMap(dto -> this.preDelete(criteria, user).thenReturn(dto))
                .flatMap(dto -> Mono.defer(() -> {
                    C criteriaId = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId());
                    return this.getRepository().removeOne(criteriaId).thenReturn(dto);
                }))
                .flatMap(deletedDto -> this.postDelete(deletedDto, criteria, user).thenReturn(deletedDto))
                .flatMap(deletedDto -> afterDelete(deletedDto, criteria, user).thenReturn(deletedDto))
                .map(BaseElementDto::getId);
    }

    default Mono<@NonNull Collection<ID>> doDeleteMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return beforeDelete(criteria, user)
                .thenMany(doGetStream(criteria, user))
                .concatMap(dto -> this.preDelete(criteria, user)
                        .then(Mono.defer(() -> {
                            C criteriaId = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId());
                            return this.getRepository().removeOne(criteriaId).thenReturn(dto);
                        }))
                        .flatMap(deletedDto -> this.postDelete(deletedDto, criteria, user).thenReturn(deletedDto))
                        .flatMap(deletedDto -> afterDelete(deletedDto, criteria, user).thenReturn(deletedDto))
                )
                .map(BaseElementDto::getId)
                .collectList()
                .map(ArrayList::new);
    }

    default Mono<@NonNull DTO> doEdit(C criteria, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        return doGetOne(criteria, user)
                .flatMap(dto ->
                        Mono.zip(
                                        runBlocking(() -> SerializationUtils.clone(dto)),
                                        runBlocking(() -> applyJsonPatch(jsonPatch, dto, getObjectMapper()))
                                )
                                .flatMap(tuple -> doUpdate(tuple.getT1(), tuple.getT2(), user))
                );
    }

    default Mono<@NonNull Collection<DTO>> doEditMany(C criteria, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        return doGetMany(criteria, user)
                .flatMap(dtos ->
                        Mono.zip(
                                        runBlocking(() -> dtos.stream().map(SerializationUtils::clone).toList()),
                                        runBlocking(() -> applyJsonPatch(dtos, jsonPatch, getObjectMapper()))
                                )
                                .flatMap(tuple -> doUpdateMany(tuple.getT1(), tuple.getT2(), user))
                );
    }

    default Mono<@NonNull DTO> doSave(DTO dto, USER user) {
        return safeSave(dto, user).switchIfEmpty(save(dto, user));
    }

    private Mono<@NonNull DTO> save(DTO dto, USER user) {
        return beforeSave(dto, user)
                .then(this.preSave(dto, user))
                .then(toDomainAsync(dto, user)
                        .flatMap(domain -> this.getRepository().insertOne(domain))
                )
                .flatMap(savedDomain -> this.postSave(dto, savedDomain, user).thenReturn(savedDomain))
                .flatMap(savedDomain -> doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), savedDomain.getId()), user))
                .flatMap(savedDto -> afterSave(dto, savedDto, user).thenReturn(savedDto));
    }

    private Mono<@NonNull DTO> safeSave(DTO dto, USER user) {
        if (SafeSave.info.hasAnnotation(getDtoClass())) {
            String idempotencyKey = IdempotencyKeyUtil.idempotencyKey(getDtoClass(), dto);
            C criteria = CriteriaUtil.uniqueCriteria(getCriteriaClass(), idempotencyKey);
            return this.doGetOne(criteria, user);
        }
        return Mono.empty();
    }

    default Mono<@NonNull DTO> doUpdate(DTO previousDto, @Valid DTO dto, USER user) throws BadRequestException, ValidationException, DomainNotFoundException {
        return beforeUpdate(previousDto, dto, user)
                .then(this.preUpdate(dto, user))
                .then(toDomainAsync(dto, user).flatMap(domain -> this.getRepository().updateOne(domain)))
                .flatMap(updatedDomain -> this.postUpdate(dto, updatedDomain, user).thenReturn(updatedDomain))
                .flatMap(updatedDomain -> doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), updatedDomain.getId()), user))
                .flatMap(updatedDto -> afterUpdate(previousDto, updatedDto, user).thenReturn(updatedDto));
    }

    // ---------------------------
    // Helper wrappers: keep blocking off the event-loop & simplify main flows
    // ---------------------------

    default Mono<@NonNull DTO> doUpdate(@Valid DTO dto, USER user) {
        return doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId()), user)
                .flatMap(previousDto -> this.doUpdate(previousDto, dto, user));
    }

    default Mono<@NonNull Collection<DTO>> doUpdateMany(Collection<DTO> previousDtos, Collection<@Valid DTO> dtos, USER user) {
        return Flux.fromIterable(dtos)
                .concatMap(dto -> findPreviousDto(previousDtos, dto.getId()).flatMap(prev -> doUpdate(prev, dto, user)))
                .collectList()
                .map(ArrayList::new);
    }

    /**
     * Convert domain -> DTO off the event-loop.
     */
    private Mono<DTO> convertDomainToDto(D domain, USER user) {
        return Mono.fromCallable(() -> toDto(domain, user)).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Convert DTO -> domain off the event-loop.
     */
    private Mono<D> toDomainAsync(DTO dto, USER user) {
        return Mono.fromCallable(() -> toDomain(dto, user)).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Find previous DTO by id from collection as a reactive Mono.
     * If not found, returns a DomainNotFoundException wrapped in Mono.error.
     */
    private Mono<DTO> findPreviousDto(Collection<DTO> previousDtos, ID id) {
        return Mono.justOrEmpty(previousDtos.stream().filter(pd -> pd.getId().equals(id)).findFirst())
                .switchIfEmpty(Mono.error(new DomainNotFoundException("previous dto not found for id: " + id)));
    }

    // ---------------------------
    // Small helpers to call LifecycleOperationComponent for readability
    // ---------------------------

    private Mono<Void> beforeCount(C criteria, USER user) {
        return getReactiveLifecycleOperationComponent().beforeCount(criteria, user, getDomainLifecycleOperation());
    }

    private Mono<Void> afterCount(C criteria, USER user) {
        return getReactiveLifecycleOperationComponent().afterCount(criteria, user, getDomainLifecycleOperation());
    }

    private Mono<Void> beforeGet(C criteria, USER user) {
        return getReactiveLifecycleOperationComponent().beforeGet(criteria, user, getDomainLifecycleOperation());
    }

    private Mono<Void> afterGet(Collection<ID> ids, Collection<DTO> dtos, C criteria, USER user) {
        return getReactiveLifecycleOperationComponent().afterGet(ids, dtos, criteria, user, getDomainLifecycleOperation());
    }

    private Mono<Void> beforeSave(DTO dto, USER user) {
        return getReactiveLifecycleOperationComponent().beforeSave(dto, user, getDomainLifecycleOperation());
    }

    private Mono<Void> afterSave(DTO dto, DTO savedDto, USER user) {
        return getReactiveLifecycleOperationComponent().afterSave(dto, savedDto, user, getDomainLifecycleOperation());
    }

    private Mono<Void> beforeUpdate(DTO previousDto, DTO dto, USER user) {
        return getReactiveLifecycleOperationComponent().beforeUpdate(previousDto, dto, user, getDomainLifecycleOperation());
    }

    private Mono<Void> afterUpdate(DTO previousDto, DTO updatedDto, USER user) {
        return getReactiveLifecycleOperationComponent().afterUpdate(previousDto, updatedDto, user, getDomainLifecycleOperation());
    }

    private Mono<Void> beforeDelete(C criteria, USER user) {
        return getReactiveLifecycleOperationComponent().beforeDelete(criteria, user, getDomainLifecycleOperation());
    }

    private Mono<Void> afterDelete(DTO dto, C criteria, USER user) {
        return getReactiveLifecycleOperationComponent().afterDelete(dto, criteria, user, getDomainLifecycleOperation());
    }

    // ---------------------------
    // JSON patch helpers (unchanged behavior but callable from async wrappers)
    // ---------------------------

    private Collection<DTO> applyJsonPatch(Collection<DTO> dtos, JsonPatch jsonPatch, ObjectMapper objectMapper) {
        return dtos.stream()
                .map(dto -> applyJsonPatch(jsonPatch, dto, objectMapper))
                .toList();
    }

    @SneakyThrows
    private DTO applyJsonPatch(JsonPatch jsonPatch, DTO dto, ObjectMapper objectMapper) {
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(dto, JsonNode.class));
        return (DTO) objectMapper.treeToValue(patched, dto.getClass());
    }

    // ---------------------------
    // Abstract / required methods (same as before)
    // ---------------------------

    ReactiveLifecycleOperationComponent getReactiveLifecycleOperationComponent();

    List<BaseReactiveDomainLifecycleOperation<ID, USER, DTO, C>> getDomainLifecycleOperation();

    ObjectMapper getObjectMapper();

    private Collection<ID> prepareIds(Collection<DTO> domains) {
        return domains.stream().map(BaseElementDto::getId).toList();
    }
}