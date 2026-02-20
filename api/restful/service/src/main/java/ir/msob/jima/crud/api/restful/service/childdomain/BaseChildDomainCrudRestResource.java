package ir.msob.jima.crud.api.restful.service.childdomain;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.read.*;
import ir.msob.jima.crud.api.restful.service.childdomain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;

/**
 * This interface provides a RESTful API for CRUD operations on a domain.
 * It extends multiple interfaces each providing a specific CRUD operation.
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
public interface BaseChildDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>
        > extends
        BaseCountAllChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseCountChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseGetByIdChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetOneChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetPageChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetManyChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetStreamChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseSaveChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseSaveManyChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseUpdateByIdChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateManyChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseEditByIdChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseEditChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseEditManyChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseDeleteByIdChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteManyChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {
}