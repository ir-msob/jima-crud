package ir.msob.jima.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseBeforeAfterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

/**
 * This class provides before and after operations for various CRUD operations.
 * It allows you to apply additional logic before and after counting, getting, saving,
 * updating, and deleting records.
 */
@Service
@RequiredArgsConstructor
public class BeforeAfterComponent implements BaseBeforeAfterService {

    private final Collection<BaseBeforeAfterService> beforeAfterServices;

    /**
     * Executes before counting records based on the provided criteria.
     *
     * @param criteria The criteria used for counting records.
     * @param user     An optional user associated with the operation.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void beforeCount(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeCount(criteria, user);
            }
        }
    }

    /**
     * Executes after counting records based on the provided criteria.
     *
     * @param criteria The criteria used for counting records.
     * @param user     An optional user associated with the operation.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void afterCount(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterCount(criteria, user);
            }
        }
    }

    /**
     * Executes before getting records based on the provided criteria.
     *
     * @param criteria The criteria used for getting records.
     * @param user     An optional user associated with the operation.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void beforeGet(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeGet(criteria, user);
            }
        }
    }

    /**
     * Executes after getting records based on the provided criteria.
     *
     * @param ids      The IDs of the retrieved records.
     * @param domains  The retrieved domain objects.
     * @param dtos     The retrieved DTO objects.
     * @param criteria The criteria used for getting records.
     * @param user     An optional user associated with the operation.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            D extends BaseDomain<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterGet(Collection<ID> ids, Collection<D> domains, Collection<DTO> dtos, C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterGet(ids, domains, dtos, criteria, user);
            }
        }
    }

    /**
     * Executes before saving records based on the provided DTOs.
     *
     * @param dtos The DTOs to be saved.
     * @param user An optional user associated with the operation.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void beforeSave(Collection<DTO> dtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
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
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws BadRequestException       If a bad request is encountered.
     * @throws JsonProcessingException   If there's an issue with JSON processing.
     * @throws InvocationTargetException If an invocation target exception occurs.
     * @throws NoSuchMethodException     If the specified method is not found.
     * @throws InstantiationException    If an instantiation exception occurs.
     * @throws IllegalAccessException    If there's an issue with access.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void afterSave(Collection<ID> ids, Collection<DTO> dtos, Collection<DTO> savedDtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException, JsonProcessingException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
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
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void beforeUpdate(Collection<ID> ids, Collection<DTO> previousDtos, Collection<DTO> dtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
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
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws BadRequestException       If a bad request is encountered.
     * @throws JsonProcessingException   If there's an issue with JSON processing.
     * @throws InvocationTargetException If an invocation target exception occurs.
     * @throws NoSuchMethodException     If the specified method is not found.
     * @throws InstantiationException    If an instantiation exception occurs.
     * @throws IllegalAccessException    If there's an issue with access.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void afterUpdate(Collection<ID> ids, Collection<DTO> dtos, Collection<DTO> updatedDtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException, JsonProcessingException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterUpdate(ids, dtos, updatedDtos, user);
            }
        }
    }

    /**
     * Executes before deleting records based on the provided criteria.
     *
     * @param criteria The criteria used for deleting records.
     * @param user     An optional user associated with the operation.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void beforeDelete(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.beforeDelete(criteria, user);
            }
        }
    }

    /**
     * Executes after deleting records based on the provided criteria.
     *
     * @param ids            The IDs of the deleted records.
     * @param deletedDomains The deleted domain objects.
     * @param criteria       The criteria used for deleting records.
     * @param dtoClass       The class of DTO used.
     * @param user           An optional user associated with the operation.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If a bad request is encountered.
     */
    @Override
    public <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            D extends BaseDomain<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterDelete(Collection<ID> ids, Collection<D> deletedDomains, C criteria, Class<DTO> dtoClass, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        if (beforeAfterServices != null && !beforeAfterServices.isEmpty()) {
            for (BaseBeforeAfterService beforeAfterService : beforeAfterServices) {
                beforeAfterService.afterDelete(ids, deletedDomains, criteria, dtoClass, user);
            }
        }
    }
}