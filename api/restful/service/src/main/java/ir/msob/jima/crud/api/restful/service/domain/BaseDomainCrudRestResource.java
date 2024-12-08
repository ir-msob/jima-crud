package ir.msob.jima.crud.api.restful.service.domain;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.domain.read.*;
import ir.msob.jima.crud.api.restful.service.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;

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
 * @param <Q>    the type of the query
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 * @author Yaqub Abdi
 */
public interface BaseDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>
        > extends
        BaseCountAllDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseGetByIdDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetStreamDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseSaveDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseUpdateByIdDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseEditByIdDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseDeleteByIdDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {
}