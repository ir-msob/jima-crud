package ir.msob.jima.crud.api.restful.service.rest;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.rest.read.*;
import ir.msob.jima.crud.api.restful.service.rest.write.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;

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
public interface BaseCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends
        BaseCountAllCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseGetByIdCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetStreamCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseSaveCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseUpdateByIdCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseEditByIdCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseDeleteByIdCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {
}