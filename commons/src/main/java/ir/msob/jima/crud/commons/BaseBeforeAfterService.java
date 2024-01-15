package ir.msob.jima.crud.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

/**
 * This interface defines before and after service methods for CRUD operations on domain entities.
 */
public interface BaseBeforeAfterService {

    /**
     * Called before counting entities based on provided criteria.
     *
     * @param criteria The criteria for counting entities.
     * @param user     An optional user context.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void beforeCount(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * Called after counting entities based on provided criteria.
     *
     * @param criteria The criteria for counting entities.
     * @param user     An optional user context.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void afterCount(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * Called before retrieving entities based on provided criteria.
     *
     * @param criteria The criteria for retrieving entities.
     * @param user     An optional user context.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void beforeGet(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * Called after retrieving entities based on provided criteria.
     *
     * @param ids      The IDs of retrieved entities.
     * @param domains  The retrieved domain entities.
     * @param dtos     The retrieved DTOs.
     * @param criteria The criteria for retrieving entities.
     * @param user     An optional user context.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            D extends BaseDomain<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterGet(Collection<ID> ids, Collection<D> domains, Collection<DTO> dtos, C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * Called before saving a collection of DTOs.
     *
     * @param dtos The DTOs to be saved.
     * @param user An optional user context.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void beforeSave(Collection<DTO> dtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * Called after saving a collection of DTOs.
     *
     * @param ids       The IDs of saved entities.
     * @param dtos      The DTOs that were saved.
     * @param savedDtos The saved DTOs.
     * @param user      An optional user context.
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws BadRequestException       If the request is invalid.
     * @throws JsonProcessingException   If there is an issue with JSON processing.
     * @throws InvocationTargetException If there is an issue with method invocation.
     * @throws NoSuchMethodException     If a required method is not found.
     * @throws InstantiationException    If an instance of a class cannot be created.
     * @throws IllegalAccessException    If there is illegal access to a class.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void afterSave(Collection<ID> ids, Collection<DTO> dtos, Collection<DTO> savedDtos, Optional<USER> user)
            throws DomainNotFoundException, BadRequestException, JsonProcessingException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    /**
     * Called before updating a collection of DTOs.
     *
     * @param ids          The IDs of entities to be updated.
     * @param previousDtos The DTOs before the update.
     * @param dtos         The DTOs to be updated.
     * @param user         An optional user context.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void beforeUpdate(Collection<ID> ids, Collection<DTO> previousDtos, Collection<DTO> dtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * Called after updating a collection of DTOs.
     *
     * @param ids          The IDs of updated entities.
     * @param previousDtos The DTOs before the update.
     * @param updatedDtos  The DTOs after the update.
     * @param user         An optional user context.
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws BadRequestException       If the request is invalid.
     * @throws JsonProcessingException   If there is an issue with JSON processing.
     * @throws InvocationTargetException If there is an issue with method invocation.
     * @throws NoSuchMethodException     If a required method is not found.
     * @throws InstantiationException    If an instance of a class cannot be created.
     * @throws IllegalAccessException    If there is illegal access to a class.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void afterUpdate(Collection<ID> ids, Collection<DTO> previousDtos, Collection<DTO> updatedDtos, Optional<USER> user)
            throws DomainNotFoundException, BadRequestException, JsonProcessingException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    /**
     * Called before deleting entities based on provided criteria.
     *
     * @param criteria The criteria for deleting entities.
     * @param user     An optional user context.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void beforeDelete(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * Called after deleting entities based on provided criteria.
     *
     * @param ids            The IDs of deleted entities.
     * @param deletedDomains The deleted domain entities.
     * @param criteria       The criteria for deleting entities.
     * @param dtoClass       The class of the DTO.
     * @param user           An optional user context.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            D extends BaseDomain<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterDelete(Collection<ID> ids, Collection<D> deletedDomains, C criteria, Class<DTO> dtoClass, Optional<USER> user)
            throws DomainNotFoundException, BadRequestException {
    }
}
