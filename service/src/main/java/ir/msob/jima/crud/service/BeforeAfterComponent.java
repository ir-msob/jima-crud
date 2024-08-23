package ir.msob.jima.crud.service;

import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterOperation;
import ir.msob.jima.core.commons.security.BaseUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * This class provides before and after operations for various CRUD operations.
 * It allows you to apply additional logic before and after counting, getting, saving,
 * updating, and deleting records.
 * <p>
 * It uses the strategy pattern to delegate the before and after operations to a collection of
 * BaseBeforeAfterOperation and BaseBeforeAfterDomainOperation instances.
 */
@Service
@RequiredArgsConstructor
public class BeforeAfterComponent {
    /**
     * A collection of BaseBeforeAfterOperation instances.
     */
    private final Collection<BaseBeforeAfterOperation> beforeAfterServices;

    /**
     * Executes before counting records based on the provided criteria.
     *
     * @param criteria                  The criteria used for counting records.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeCount(C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeCount(criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeCount(criteria, user);
            }
        }
    }

    /**
     * Executes after counting records based on the provided criteria.
     *
     * @param criteria                  The criteria used for counting records.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterCount(C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterCount(criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterCount(criteria, user);
            }
        }
    }

    /**
     * Executes before getting records based on the provided criteria.
     *
     * @param criteria                  The criteria used for getting records.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeGet(C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeGet(criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeGet(criteria, user);
            }
        }
    }

    /**
     * Executes after getting records based on the provided criteria.
     *
     * @param ids                       The IDs of the retrieved records.
     * @param dtos                      The retrieved DTO objects.
     * @param criteria                  The criteria used for getting records.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterGet(Collection<ID> ids, Collection<DTO> dtos, C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterGet(ids, dtos, criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterGet(ids, dtos, criteria, user);
            }
        }
    }

    /**
     * Executes before saving records based on the provided DTO.
     *
     * @param dto                       The DTO to be saved.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeSave(DTO dto, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeSave(dto, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeSave(dto, user);
            }
        }
    }

    /**
     * Executes after saving records based on the provided DTO.
     *
     * @param dto                       The DTO that were saved.
     * @param savedDto                  The saved DTO.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterSave(DTO dto, DTO savedDto, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterSave(dto, savedDto, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterSave(dto, savedDto, user);
            }
        }
    }

    /**
     * Executes before updating records based on the provided DTO.
     *
     * @param previousDto               The previous DTO.
     * @param dto                       The updated DTO.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeUpdate(DTO previousDto, DTO dto, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeUpdate(previousDto, dto, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeUpdate(previousDto, dto, user);
            }
        }
    }

    /**
     * Executes after updating records based on the provided DTO.
     *
     * @param dto                       The updated DTO.
     * @param updatedDto                The updated DTO after the update.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterUpdate(DTO dto, DTO updatedDto, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterUpdate(dto, updatedDto, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterUpdate(dto, updatedDto, user);
            }
        }
    }

    /**
     * Executes before deleting records based on the provided criteria.
     *
     * @param criteria                  The criteria used for deleting records.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeDelete(C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeDelete(criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeDelete(criteria, user);
            }
        }
    }

    /**
     * Executes after deleting records based on the provided criteria.
     *
     * @param dto                       The DTO of the deleted records.
     * @param criteria                  The criteria used for deleting records.
     * @param dtoClass                  The class of DTO used.
     * @param user                      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainOperation instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterDelete(DTO dto, C criteria, Class<DTO> dtoClass, Optional<USER> user, Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterOperation beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterDelete(dto, criteria, dtoClass, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainOperation<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterDelete(dto, criteria, user);
            }
        }
    }
}