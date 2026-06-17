package ir.msob.jima.crud.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.crud.api.service.domain.operation.LifecycleOperationComponent;
import ir.msob.jima.platform.api.element.criteria.BaseElementCriteria;
import ir.msob.jima.platform.api.element.dto.BaseElementDto;
import ir.msob.jima.platform.api.element.element.BaseElement;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.exception.validation.ValidationException;
import ir.msob.jima.platform.api.operation.BaseDomainLifecycleOperation;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.safemodify.IdempotencyKeyUtil;
import ir.msob.jima.platform.api.safemodify.SafeSave;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.service.BaseService;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


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
public interface BaseCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseElement<ID>,
        DTO extends BaseElementDto<ID>,
        C extends BaseElementCriteria<ID>,
        R extends BaseRepository<ID, D, C>>
        extends BaseCrudServiceParent<ID, USER, D, DTO, C, R>,
        BaseService<ID, USER, D, DTO, C, R> {

    // ---------------------------
    // CRUD entry points
    // ---------------------------


    default @NonNull Long doCount(C criteria, USER user) {
        beforeCount(criteria, user);
        Long count = this.getRepository().count(criteria);
        afterCount(criteria, user);
        return count;
    }

    default DTO doGetOne(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        beforeGet(criteria, user);
        this.preGet(criteria, user);
        var domain = this.getRepository().getOne(criteria);
        var dto = toDto(domain, user);
        Collection<ID> ids = Collections.singleton(domain.getId());
        Collection<DTO> dtos = Collections.singleton(dto);
        postGet(ids, dtos, criteria, user);
        afterGet(ids, dtos, criteria, user);
        return dto;
    }

    default @NonNull Collection<DTO> doGetMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        beforeGet(criteria, user);
        this.preGet(criteria, user);

        var dtos = this.getRepository().getMany(criteria)
                .stream()
                .map(domain -> toDto(domain, user))
                .toList();

        Collection<ID> ids = prepareIds(dtos);
        postGet(ids, dtos, criteria, user);
        afterGet(ids, dtos, criteria, user);
        return dtos;

    }

    default @NonNull Page<@NonNull DTO> doGetPage(C criteria, Pageable pageable, USER user) throws DomainNotFoundException, BadRequestException {
        beforeGet(criteria, user);

        this.preGet(criteria, user);
        var domainPage = this.getRepository().getPage(criteria, pageable);
        var dtos = domainPage.stream().map(domain -> toDto(domain, user)).toList();

        Page<DTO> dtoPage = new PageImpl<>(dtos, domainPage.getPageable(), domainPage.getTotalElements());
        Collection<ID> ids = prepareIds(dtos);
        postGet(ids, dtos, criteria, user);
        afterGet(ids, dtos, criteria, user);
        return dtoPage;

    }


    default ID doDelete(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        beforeDelete(criteria, user);
        var dto = doGetOne(criteria, user);
        this.preDelete(criteria, user);
        C criteriaId = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId());
        var deletedDto = this.getRepository().removeOne(criteriaId);
        this.postDelete(dto, criteria, user);
        afterDelete(dto, criteria, user);
        return deletedDto.getId();
    }

    default @NonNull Collection<ID> doDeleteMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        beforeDelete(criteria, user);
        var dtos = doGetMany(criteria, user);
        return dtos.stream().peek(dto -> {
                    this.preDelete(criteria, user);
                    C criteriaId = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId());
                    this.getRepository().removeOne(criteriaId);
                    this.postDelete(dto, criteria, user);
                    afterDelete(dto, criteria, user);
                })
                .map(BaseElementDto::getId)
                .toList();
    }

    default DTO doEdit(C criteria, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        var dto = doGetOne(criteria, user);
        var cloned = SerializationUtils.clone(dto);
        var patched = applyJsonPatch(jsonPatch, dto, getObjectMapper());
        return doUpdate(cloned, patched, user);

    }

    default @NonNull Collection<DTO> doEditMany(C criteria, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        var dtos = doGetMany(criteria, user);
        var cloneds = dtos.stream().map(SerializationUtils::clone).toList();
        var patcheds = applyJsonPatch(dtos, jsonPatch, getObjectMapper());
        return doUpdateMany(cloneds, patcheds, user);

    }

    default @NonNull DTO doSave(DTO dto, USER user) {
        return safeSave(dto, user)
                .orElse(save(dto, user));
    }

    private @NonNull DTO save(DTO dto, USER user) {
        beforeSave(dto, user);
        this.preSave(dto, user);
        var domain = toDomain(dto, user);
        var savedDomain = this.getRepository().insertOne(domain);
        this.postSave(dto, savedDomain, user);
        var savedDto = doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), savedDomain.getId()), user);
        afterSave(dto, savedDto, user);
        return savedDto;
    }

    private Optional<DTO> safeSave(DTO dto, USER user) {
        if (SafeSave.info.hasAnnotation(getDtoClass())) {
            String idempotencyKey = IdempotencyKeyUtil.idempotencyKey(getDtoClass(), dto);
            C criteria = CriteriaUtil.uniqueCriteria(getCriteriaClass(), idempotencyKey);
            return Optional.ofNullable(this.doGetOne(criteria, user));
        }
        return Optional.empty();
    }

    default @NonNull DTO doUpdate(DTO previousDto, @Valid DTO dto, USER user) throws BadRequestException, ValidationException, DomainNotFoundException {
        beforeUpdate(previousDto, dto, user);
        this.preUpdate(dto, user);
        var domain = toDomain(dto, user);
        var updatedDomain = this.getRepository().updateOne(domain);
        this.postUpdate(dto, updatedDomain, user);
        var updatedDto = doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), updatedDomain.getId()), user);
        afterUpdate(previousDto, updatedDto, user);
        return updatedDto;
    }

    // ---------------------------
    // Helper wrappers: keep blocking off the event-loop & simplify main flows
    // ---------------------------

    default @NonNull DTO doUpdate(@Valid DTO dto, USER user) {
        var previousDto = doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId()), user);
        return this.doUpdate(previousDto, dto, user);
    }

    default @NonNull Collection<DTO> doUpdateMany(Collection<DTO> previousDtos, Collection<@Valid DTO> dtos, USER user) {

        return dtos.stream()
                .map(dto -> {
                            var prev = findPreviousDto(previousDtos, dto.getId());
                            return doUpdate(prev, dto, user);
                        }
                ).toList();
    }


    /**
     * Find previous DTO by id from collection as a reactive Mono.
     * If not found, returns a DomainNotFoundException wrapped in Mono.error.
     */
    private DTO findPreviousDto(Collection<DTO> previousDtos, ID id) {
        return previousDtos.stream().filter(pd -> pd.getId().equals(id)).findFirst()
                .orElseThrow(() -> new DomainNotFoundException("previous dto not found for id: " + id));
    }

    // ---------------------------
    // Small helpers to call LifecycleOperationComponent for readability
    // ---------------------------

    private void beforeCount(C criteria, USER user) {
        getLifecycleOperationComponent().beforeCount(criteria, user, getDomainLifecycleOperation());
    }

    private void afterCount(C criteria, USER user) {
        getLifecycleOperationComponent().afterCount(criteria, user, getDomainLifecycleOperation());
    }

    private void beforeGet(C criteria, USER user) {
        getLifecycleOperationComponent().beforeGet(criteria, user, getDomainLifecycleOperation());
    }

    private void afterGet(Collection<ID> ids, Collection<DTO> dtos, C criteria, USER user) {
        getLifecycleOperationComponent().afterGet(ids, dtos, criteria, user, getDomainLifecycleOperation());
    }

    private void beforeSave(DTO dto, USER user) {
        getLifecycleOperationComponent().beforeSave(dto, user, getDomainLifecycleOperation());
    }

    private void afterSave(DTO dto, DTO savedDto, USER user) {
        getLifecycleOperationComponent().afterSave(dto, savedDto, user, getDomainLifecycleOperation());
    }

    private void beforeUpdate(DTO previousDto, DTO dto, USER user) {
        getLifecycleOperationComponent().beforeUpdate(previousDto, dto, user, getDomainLifecycleOperation());
    }

    private void afterUpdate(DTO previousDto, DTO updatedDto, USER user) {
        getLifecycleOperationComponent().afterUpdate(previousDto, updatedDto, user, getDomainLifecycleOperation());
    }

    private void beforeDelete(C criteria, USER user) {
        getLifecycleOperationComponent().beforeDelete(criteria, user, getDomainLifecycleOperation());
    }

    private void afterDelete(DTO dto, C criteria, USER user) {
        getLifecycleOperationComponent().afterDelete(dto, criteria, user, getDomainLifecycleOperation());
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

    LifecycleOperationComponent getLifecycleOperationComponent();

    List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> getDomainLifecycleOperation();

    ObjectMapper getObjectMapper();

    private Collection<ID> prepareIds(Collection<DTO> domains) {
        return domains.stream().map(BaseElement::getId).toList();
    }
}