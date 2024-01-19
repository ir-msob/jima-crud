package ir.msob.jima.crud.service;

import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseBeforeAfterDomainService;
import ir.msob.jima.crud.commons.BaseBeforeAfterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * This class provides before and after operations for various CRUD operations.
 * It allows you to apply additional logic before and after counting, getting, saving,
 * updating, and deleting records.
 *
 * It uses the strategy pattern to delegate the before and after operations to a collection of
 * BaseBeforeAfterService and BaseBeforeAfterDomainService instances.
 */
@Service
@RequiredArgsConstructor
public class BeforeAfterComponent {
    /**
     * A collection of BaseBeforeAfterService instances.
     */
    private final Collection<BaseBeforeAfterService> beforeAfterServices;

    /**
     * Executes before counting records based on the provided criteria.
     *
     * @param criteria The criteria used for counting records.
     * @param user     An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeCount(C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeCount(criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeCount(criteria, user);
            }
        }
    }

    /**
     * Executes after counting records based on the provided criteria.
     *
     * @param criteria The criteria used for counting records.
     * @param user     An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterCount(C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterCount(criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterCount(criteria, user);
            }
        }
    }

    /**
     * Executes before getting records based on the provided criteria.
     *
     * @param criteria The criteria used for getting records.
     * @param user     An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeGet(C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeGet(criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeGet(criteria, user);
            }
        }
    }

    /**
     * Executes after getting records based on the provided criteria.
     *
     * @param ids      The IDs of the retrieved records.
     * @param dtos     The retrieved DTO objects.
     * @param criteria The criteria used for getting records.
     * @param user     An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterGet(Collection<ID> ids, Collection<DTO> dtos, C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterGet(ids, dtos, criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterGet(ids, dtos, criteria, user);
            }
        }
    }

    /**
     * Executes before saving records based on the provided DTOs.
     *
     * @param dtos The DTOs to be saved.
     * @param user An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeSave(Collection<DTO> dtos, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeSave(dtos, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeSave(dtos, user);
            }
        }
    }

    /**
     * Executes after saving records based on the provided DTOs.
     *
     * @param ids       The IDs of the saved records.
     * @param dtos      The DTOs that were saved.
     * @param savedDtos The saved DTOs.
     * @param user      An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterSave(Collection<ID> ids, Collection<DTO> dtos, Collection<DTO> savedDtos, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterSave(ids, dtos, savedDtos, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterSave(ids, dtos, savedDtos, user);
            }
        }
    }

    /**
     * Executes before updating records based on the provided DTOs.
     *
     * @param ids          The IDs of the records to be updated.
     * @param previousDtos The previous DTOs.
     * @param dtos         The updated DTOs.
     * @param user         An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeUpdate(Collection<ID> ids, Collection<DTO> previousDtos, Collection<DTO> dtos, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeUpdate(ids, previousDtos, dtos, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeUpdate(ids, previousDtos, dtos, user);
            }
        }
    }

    /**
     * Executes after updating records based on the provided DTOs.
     *
     * @param ids         The IDs of the updated records.
     * @param dtos        The updated DTOs.
     * @param updatedDtos The updated DTOs after the update.
     * @param user        An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterUpdate(Collection<ID> ids, Collection<DTO> dtos, Collection<DTO> updatedDtos, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterUpdate(ids, dtos, updatedDtos, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterUpdate(ids, dtos, updatedDtos, user);
            }
        }
    }

    /**
     * Executes before deleting records based on the provided criteria.
     *
     * @param criteria The criteria used for deleting records.
     * @param user     An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void beforeDelete(C criteria, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeDelete(criteria, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.beforeDelete(criteria, user);
            }
        }
    }

    /**
     * Executes after deleting records based on the provided criteria.
     *
     * @param ids      The IDs of the deleted records.
     * @param criteria The criteria used for deleting records.
     * @param dtoClass The class of DTO used.
     * @param user     An optional user associated with the operation.
     * @param beforeAfterDomainServices A collection of BaseBeforeAfterDomainService instances.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterDelete(Collection<ID> ids, C criteria, Class<DTO> dtoClass, Optional<USER> user, Collection<BaseBeforeAfterDomainService<ID, USER, DTO, C>> beforeAfterDomainServices) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterDelete(ids, criteria, dtoClass, user);
            }
        }
        if (beforeAfterDomainServices != null && !beforeAfterDomainServices.isEmpty()) {
            for (BaseBeforeAfterDomainService<ID, USER, DTO, C> beforeAfterService : beforeAfterDomainServices) {
                beforeAfterService.afterDelete(ids, criteria, user);
            }
        }
    }
}