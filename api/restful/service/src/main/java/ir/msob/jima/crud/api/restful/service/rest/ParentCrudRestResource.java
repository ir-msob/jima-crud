package ir.msob.jima.crud.api.restful.service.rest;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.commons.ParentCrudService;

import java.io.Serializable;

/**
 * @param <ID>
 * @param <USER>
 * @param <S>
 * @author Yaqub Abdi
 */
public interface ParentCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends ParentCrudService<ID, USER, D, DTO, C, R>>
        extends BaseRestResource<ID, USER>,
        BaseCrudResource {

    S getService();

}
