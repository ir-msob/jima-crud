package ir.msob.jima.crud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.validation.ValidationException;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.commons.safemodify.SafeSave;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.service.BaseService;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.commons.util.DtoUtil;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.operation.BeforeAfterOperationComponent;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This interface defines common CRUD operations for a domain entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user context.
 * @param <D>    The type of domain entity.
 * @param <DTO>  The type of DTO (Data Transfer Object) associated with the domain entity.
 * @param <C>    The type of criteria used for querying domain entities.
 * @param <R>    The type of repository for the domain entity.
 * @author Yaqub Abdi
 */
public interface ParentCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>>
        extends BaseService<ID, USER, D, DTO, C, R> {


    default Mono<@NonNull Long> doCount(C criteria, USER user) {
        getBeforeAfterOperationComponent().beforeCount(criteria, user, getBeforeAfterDomainOperations());
        return this.getRepository().count(criteria)
                .doOnSuccess(result -> getBeforeAfterOperationComponent().afterCount(criteria, user, getBeforeAfterDomainOperations()));
    }

    default Mono<@NonNull DTO> doGetOne(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        getBeforeAfterOperationComponent().beforeGet(criteria, user, getBeforeAfterDomainOperations());

        return this.preGet(criteria, user)
                .then(this.getRepository().getOne(criteria))
                .flatMap(domain -> {
                    DTO dto = toDto(domain, user);
                    Collection<ID> ids = Collections.singletonList(domain.getId());
                    Collection<DTO> dtos = Collections.singletonList(dto);

                    return this.postGet(ids, dtos, criteria, user)
                            .doOnSuccess(x -> getBeforeAfterOperationComponent().afterGet(ids, dtos, criteria, user, getBeforeAfterDomainOperations()))
                            .thenReturn(dto);
                });
    }


    default Mono<@NonNull Collection<DTO>> doGetMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        getBeforeAfterOperationComponent().beforeGet(criteria, user, getBeforeAfterDomainOperations());

        return this.preGet(criteria, user)
                .thenMany(this.getRepository().getMany(criteria))
                .map(d -> toDto(d, user))
                .collectList()
                .flatMap(dtos -> {
                    Collection<ID> ids = prepareIds(dtos);
                    return this.postGet(ids, dtos, criteria, user)
                            .doOnSuccess(x -> getBeforeAfterOperationComponent().afterGet(ids, dtos, criteria, user, getBeforeAfterDomainOperations()))
                            .thenReturn(dtos);
                });
    }

    default Mono<@NonNull Page<@NonNull DTO>> doGetPage(C criteria, Pageable pageable, USER user) throws DomainNotFoundException, BadRequestException {
        getBeforeAfterOperationComponent().beforeGet(criteria, user, getBeforeAfterDomainOperations());

        return this.preGet(criteria, user)
                .then(this.getRepository().getPage(criteria, pageable))
                .map(domainPage -> {
                    List<DTO> dtos = domainPage
                            .stream()
                            .map(domain -> toDto(domain, user))
                            .toList();

                    return new PageImpl<>(dtos, domainPage.getPageable(), domainPage.getTotalElements());
                })
                .flatMap(dtoPage -> {
                    Collection<ID> ids = prepareIds(dtoPage.getContent());
                    return this.postGet(ids, dtoPage.getContent(), criteria, user)
                            .doOnSuccess(x -> getBeforeAfterOperationComponent().afterGet(ids, dtoPage.getContent(), criteria, user, getBeforeAfterDomainOperations()))
                            .thenReturn(dtoPage);
                });
    }

    default Flux<@NonNull DTO> doGetStream(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        getBeforeAfterOperationComponent().beforeGet(criteria, user, getBeforeAfterDomainOperations());

        return this.preGet(criteria, user)
                .thenMany(this.getRepository().getMany(criteria))
                .flatMap(domain -> {
                    DTO dto = toDto(domain, user);
                    Collection<DTO> dtos = Collections.singleton(dto);
                    Collection<ID> ids = Collections.singleton(domain.getId());
                    return this.postGet(ids, dtos, criteria, user)
                            .doOnSuccess(x -> getBeforeAfterOperationComponent().afterGet(ids, dtos, criteria, user, getBeforeAfterDomainOperations()))
                            .thenReturn(dto);
                });
    }

    default Mono<@NonNull ID> doDelete(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        getBeforeAfterOperationComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations());

        return doGetOne(criteria, user)
                .flatMap(dto -> this.preDelete(criteria, user).thenReturn(dto))
                .flatMap(dto -> {
                    C criteriaId = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId());

                    return this.getRepository().removeOne(criteriaId).thenReturn(dto);
                })
                .flatMap(dto -> this.postDelete(dto, criteria, user).thenReturn(dto))
                .doOnNext(dto -> this.getBeforeAfterOperationComponent().afterDelete(dto, criteria, getDtoClass(), user, getBeforeAfterDomainOperations()))
                .map(BaseDto::getId);
    }

    default Mono<@NonNull Collection<ID>> doDeleteMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        getBeforeAfterOperationComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations());

        return doGetStream(criteria, user)
                .flatMap(dto -> this.preDelete(criteria, user).thenReturn(dto))
                .flatMap(dto -> {
                    C criteriaId = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId());

                    return this.getRepository().removeOne(criteriaId).thenReturn(dto);
                })
                .flatMap(dto -> this.postDelete(dto, criteria, user).thenReturn(dto))
                .doOnNext(dto -> this.getBeforeAfterOperationComponent().afterDelete(dto, criteria, getDtoClass(), user, getBeforeAfterDomainOperations()))
                .map(BaseDto::getId)
                .collectList()
                .map(ArrayList::new);
    }


    default Mono<@NonNull DTO> doEdit(C criteria, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        return doGetOne(criteria, user).flatMap(dto -> {
            DTO previousDto = SerializationUtils.clone(dto);
            return doUpdate(previousDto, applyJsonPatch(jsonPatch, dto, getObjectMapper()), user);
        });
    }

    default Mono<@NonNull Collection<DTO>> doEditMany(C criteria, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        return doGetMany(criteria, user).flatMap(dtos -> {
            Collection<DTO> previousDtos = dtos.stream()
                    .map(SerializationUtils::clone)
                    .toList();
            try {
                return doUpdateMany(previousDtos, applyJsonPatch(dtos, jsonPatch, getObjectMapper()), user);
            } catch (BadRequestException | ValidationException | DomainNotFoundException e) {
                return Mono.error(e);
            }
        });
    }

    default Mono<@NonNull DTO> doSave(DTO dto, USER user) {
        return safeSave(dto, user)
                .switchIfEmpty(save(dto, user));
    }

    private Mono<@NonNull DTO> save(DTO dto, USER user) {
        getBeforeAfterOperationComponent().beforeSave(dto, user, getBeforeAfterDomainOperations());
        D domain = toDomain(dto, user);
        return this.preSave(dto, user)
                .then(this.getRepository().insertOne(domain))
                .doOnSuccess(savedDomain -> this.postSave(dto, savedDomain, user))
                .flatMap(savedDomain -> doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), savedDomain.getId()), user))
                .doOnSuccess(savedDto -> getBeforeAfterOperationComponent().afterSave(dto, savedDto, user, getBeforeAfterDomainOperations()));
    }

    private Mono<@NonNull DTO> safeSave(DTO dto, USER user) {
        if (SafeSave.info.hasAnnotation(getDtoClass())) {
            String uniqueFieldValue = DtoUtil.uniqueField(getDtoClass(), dto);
            C criteria = CriteriaUtil.uniqueCriteria(getCriteriaClass(), uniqueFieldValue);
            return this.doGetOne(criteria, user);
        }
        return Mono.empty();
    }

    default Mono<@NonNull DTO> doUpdate(DTO previousDto, @Valid DTO dto, USER user) throws BadRequestException, ValidationException, DomainNotFoundException {
        getBeforeAfterOperationComponent().beforeUpdate(previousDto, dto, user, getBeforeAfterDomainOperations());

        D domain = toDomain(dto, user);

        return this.preUpdate(dto, user)
                .then(this.getRepository().updateOne(domain))
                .flatMap(updatedDomain -> this.postUpdate(dto, updatedDomain, user).thenReturn(updatedDomain))
                .flatMap(updatedDomain -> doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), updatedDomain.getId()), user))
                .doOnSuccess(updatedDto ->
                        getBeforeAfterOperationComponent().afterUpdate(previousDto, updatedDto, user, getBeforeAfterDomainOperations()));
    }

    default Mono<@NonNull DTO> doUpdate(@Valid DTO dto, USER user) {
        return doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId()), user)
                .flatMap(previousDto -> this.doUpdate(previousDto, dto, user));
    }

    default Mono<@NonNull Collection<DTO>> doUpdateMany(Collection<DTO> previousDtos, Collection<@Valid DTO> dtos, USER user) {

        return Flux.fromIterable(dtos)
                .flatMap(dto -> {
                    DTO previousDto = previousDtos.stream()
                            .filter(pd -> pd.getId().equals(dto.getId()))
                            .findFirst()
                            .orElseThrow();
                    return doUpdate(previousDto, dto, user);
                })
                .collectList()
                .map(ArrayList::new);
    }

    /**
     * Applies a JSON patch to a collection of DTOs.
     *
     * @param dtos         A collection of DTOs to be patched.
     * @param jsonPatch    The JSON patch to apply.
     * @param objectMapper The ObjectMapper for JSON processing.
     * @return A collection of patched DTOs.
     */
    default Collection<DTO> applyJsonPatch(Collection<DTO> dtos, JsonPatch jsonPatch, ObjectMapper objectMapper) {
        return dtos.stream()
                .map(dto -> applyJsonPatch(jsonPatch, dto, objectMapper))
                .toList();
    }

    /**
     * Applies a JSON patch to a DTO.
     *
     * @param jsonPatch    The JSON patch to apply.
     * @param dto          The DTO to be patched.
     * @param objectMapper The ObjectMapper for JSON processing.
     * @return The patched DTO.
     */
    @SneakyThrows
    default DTO applyJsonPatch(JsonPatch jsonPatch, DTO dto, ObjectMapper objectMapper) {
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(dto, JsonNode.class));
        return (DTO) objectMapper.treeToValue(patched, dto.getClass());
    }

    /**
     * Get the before/after component for the service.
     *
     * @return The before/after component.
     */
    BeforeAfterOperationComponent getBeforeAfterOperationComponent();

    /**
     * Get the collection of before/after domain operations.
     *
     * @return The collection of before/after domain operations.
     */
    Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> getBeforeAfterDomainOperations();

    /**
     * Get the ObjectMapper for handling JSON data.
     *
     * @return The ObjectMapper instance.
     */
    ObjectMapper getObjectMapper();

    /**
     * Prepare a collection of entity IDs from a collection of domain entities.
     *
     * @param domains The collection of domain entities.
     * @return A collection of entity IDs.
     */
    default Collection<ID> prepareIds(Collection<DTO> domains) {
        return domains
                .stream()
                .map(BaseDto::getId)
                .toList();
    }
}
