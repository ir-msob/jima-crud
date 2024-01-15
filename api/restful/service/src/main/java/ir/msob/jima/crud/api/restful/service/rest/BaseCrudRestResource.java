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
 * @param <ID>
 * @param <D>
 * @param <DTO>
 * @param <USER>
 * @param <C>
 * @param <R>
 * @param <S>
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
        BaseGetOneCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetStreamCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseSaveCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteCrudRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {
}
