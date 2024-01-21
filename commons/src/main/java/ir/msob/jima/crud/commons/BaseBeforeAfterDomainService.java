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
 *
 * @param <ID>   the type of the ID of the DTO, which extends Comparable and Serializable
 * @param <USER> the type of the user, which extends BaseUser
 * @param <DTO>  the type of the DTO, which extends BaseDto
 * @param <C>    the type of the criteria, which extends BaseCriteria
 */
public interface BaseBeforeAfterDomainService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>> {

    /**
     * This method is called before the count operation.
     *
     * @param criteria the criteria used for counting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default void beforeCount(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the count operation.
     *
     * @param criteria the criteria used for counting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default void afterCount(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called before the get operation.
     *
     * @param criteria the criteria used for getting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default void beforeGet(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the get operation.
     *
     * @param ids      the IDs of the DTOs that were gotten
     * @param dtos     the DTOs that were gotten
     * @param criteria the criteria used for getting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default void afterGet(Collection<ID> ids, Collection<DTO> dtos, C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called before the save operation.
     *
     * @param dtos the DTOs to be saved
     * @param user the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default void beforeSave(Collection<DTO> dtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the save operation.
     *
     * @param ids       the IDs of the DTOs that were saved
     * @param dtos      the DTOs that were saved
     * @param savedDtos the DTOs that were actually saved
     * @param user      the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default void afterSave(Collection<ID> ids, Collection<DTO> dtos, Collection<DTO> savedDtos, Optional<USER> user)
            throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called before the update operation.
     *
     * @param ids          the IDs of the DTOs to be updated
     * @param previousDtos the previous state of the DTOs to be updated
     * @param dtos         the new state of the DTOs to be updated
     * @param user         the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default void beforeUpdate(Collection<ID> ids, Collection<DTO> previousDtos, Collection<DTO> dtos, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the update operation.
     *
     * @param ids          the IDs of the DTOs that were updated
     * @param previousDtos the previous state of the DTOs that were updated
     * @param updatedDtos  the updated DTOs
     * @param user         the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default void afterUpdate(Collection<ID> ids, Collection<DTO> previousDtos, Collection<DTO> updatedDtos, Optional<USER> user)
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
    default void beforeDelete(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
    }

    /**
     * This method is called after the delete operation.
     *
     * @param ids      the IDs of the DTOs that were deleted
     * @param criteria the criteria used for deleting
     * @param user     the current user
     * @throws DomainNotFoundException if the domain is not found
     * @throws BadRequestException     if the request is bad
     */
    default void afterDelete(Collection<ID> ids, C criteria, Optional<USER> user)
            throws DomainNotFoundException, BadRequestException {
    }
}