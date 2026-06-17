package ir.msob.jima.crud.restful.reactive.resource.childdomain;

import ir.msob.jima.crud.api.resource.BaseCrudResource;
import ir.msob.jima.crud.reactive.service.childdomain.ParentChildDomainCrudService;
import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.restful.reactive.resource.BaseRestReactiveResource;

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
public interface ParentChildDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends ParentChildDomainCrudService<ID, USER, D, DTO, C, R>>
        extends BaseRestReactiveResource<ID, USER>,
        BaseCrudResource<ID, USER> {

    /**
     * This method returns the service that provides the CRUD operations.
     *
     * @return the service that provides the CRUD operations
     */
    S getService();

}