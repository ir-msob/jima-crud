package ir.msob.jima.crud.commons;

import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * This interface provides a set of methods that are called before and after each CRUD operation.
 * It is designed to be implemented by domain services that need to perform additional processing or validation.
 */
public interface BaseBeforeAfterService {

    /**
     * This method is called before the count operation.
     *
     * @param criteria the criteria used for counting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void beforeCount(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the count operation.
     *
     * @param criteria the criteria used for counting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void afterCount(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called before the get operation.
     *
     * @param criteria the criteria used for getting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void beforeGet(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the get operation.
     *
     * @param ids      the IDs of retrieved entities
     * @param dtos     the retrieved DTOs
     * @param criteria the criteria used for getting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterGet(Collection<ID> ids, Collection<DTO> dtos, C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called before the save operation.
     *
     * @param dtos the DTOs to be saved
     * @param user the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void beforeSave(Collection<DTO> dtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the save operation.
     *
     * @param ids       the IDs of saved entities
     * @param dtos      the DTOs that were saved
     * @param savedDtos the saved DTOs
     * @param user      the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void afterSave(Collection<ID> ids, Collection<DTO> dtos, Collection<DTO> savedDtos, Optional<USER> user)
            throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called before the update operation.
     *
     * @param ids          the IDs of entities to be updated
     * @param previousDtos the DTOs before the update
     * @param dtos         the DTOs to be updated
     * @param user         the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void beforeUpdate(Collection<ID> ids, Collection<DTO> previousDtos, Collection<DTO> dtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the update operation.
     *
     * @param ids          the IDs of updated entities
     * @param previousDtos the DTOs before the update
     * @param updatedDtos  the DTOs after the update
     * @param user         the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>> void afterUpdate(Collection<ID> ids, Collection<DTO> previousDtos, Collection<DTO> updatedDtos, Optional<USER> user)
            throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called before the delete operation.
     *
     * @param criteria the criteria used for deleting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            C extends BaseCriteria<ID>> void beforeDelete(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the delete operation.
     *
     * @param ids      the IDs of deleted entities
     * @param criteria the criteria used for deleting
     * @param dtoClass the class of the DTO
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default <ID extends Comparable<ID> & Serializable,
            USER extends BaseUser<ID>,
            DTO extends BaseDto<ID>,
            C extends BaseCriteria<ID>> void afterDelete(Collection<ID> ids, C criteria, Class<DTO> dtoClass, Optional<USER> user)
            throws DomainNotFoundException, BadRequestException {
    }
}