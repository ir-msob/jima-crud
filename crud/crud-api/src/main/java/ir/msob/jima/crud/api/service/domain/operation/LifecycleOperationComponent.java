package ir.msob.jima.crud.api.service.domain.operation;

import ir.msob.jima.platform.api.element.criteria.BaseElementCriteria;
import ir.msob.jima.platform.api.element.dto.BaseElementDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.operation.BaseDomainLifecycleOperation;
import ir.msob.jima.platform.api.operation.BaseLifecycleOperation;
import ir.msob.jima.platform.api.security.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LifecycleOperationComponent {

    private final List<BaseLifecycleOperation> lifecycleOperations;

    private static <T> List<T> safeList(List<T> list) {
        return list == null ? List.of() : list;
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void beforeCount(C criteria, USER user, List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations)
            throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.beforeCount(criteria, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.beforeCount(criteria, user);
        }
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void afterCount(
            C criteria,
            USER user,
            List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations
    ) throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.afterCount(criteria, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.afterCount(criteria, user);
        }
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void beforeGet(
            C criteria,
            USER user,
            List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations
    ) throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.beforeGet(criteria, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.beforeGet(criteria, user);
        }
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void afterGet(
            Collection<ID> ids,
            Collection<DTO> dtos,
            C criteria,
            USER user,
            List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations
    ) throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.afterGet(ids, dtos, criteria, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.afterGet(ids, dtos, criteria, user);
        }
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void beforeSave(
            DTO dto,
            USER user,
            List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations
    ) throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.beforeSave(dto, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.beforeSave(dto, user);
        }
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void afterSave(
            DTO dto,
            DTO savedDto,
            USER user,
            List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations
    ) throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.afterSave(dto, savedDto, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.afterSave(dto, savedDto, user);
        }
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void beforeUpdate(
            DTO previousDto,
            DTO dto,
            USER user,
            List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations
    ) throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.beforeUpdate(previousDto, dto, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.beforeUpdate(previousDto, dto, user);
        }
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void afterUpdate(
            DTO previousDto,
            DTO updatedDto,
            USER user,
            List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations
    ) throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.afterUpdate(previousDto, updatedDto, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.afterUpdate(previousDto, updatedDto, user);
        }
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void beforeDelete(
            C criteria,
            USER user,
            List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations
    ) throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.beforeDelete(criteria, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.beforeDelete(criteria, user);
        }
    }

    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseElementDto<ID>,
            C extends BaseElementCriteria<ID>>
    void afterDelete(
            DTO dto,
            C criteria,
            USER user,
            List<BaseDomainLifecycleOperation<ID, USER, DTO, C>> domainOperations
    ) throws DomainNotFoundException, BadRequestException {

        for (BaseLifecycleOperation operation : safeList(lifecycleOperations)) {
            operation.afterDelete(dto, criteria, user);
        }

        for (BaseDomainLifecycleOperation<ID, USER, DTO, C> operation : safeList(domainOperations)) {
            operation.afterDelete(dto, criteria, user);
        }
    }
}