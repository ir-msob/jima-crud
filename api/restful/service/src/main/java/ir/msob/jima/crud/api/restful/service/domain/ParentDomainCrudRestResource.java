package ir.msob.jima.crud.api.restful.service.domain;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.ParentDomainCrudService;

import java.io.Serializable;

/**
 * This interface provides a RESTful API for CRUD operations on a domain.
 * It extends the BaseRestResource and BaseCrudResource interfaces and provides a method to get the service.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 * @author Yaqub Abdi
 */
public interface ParentDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends ParentDomainCrudService<ID, USER, D, DTO, C, R>>
        extends BaseRestResource<ID, USER>,
        BaseCrudResource {

    /**
     * This method returns the service that provides the CRUD operations.
     *
     * @return the service that provides the CRUD operations
     */
    S getService();

}