package ir.msob.jima.crud.api.graphql.restful.service.resource;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.graphql.restful.service.resource.read.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;

import java.io.Serializable;

/**
 * The {@code BaseReadCrudGraphqlRestResource} interface extends several GraphQL resource interfaces,
 * providing read-specific GraphQL operations for a CRUD service.
 * It is generic and allows customization for different types such as ID, USER, D, DTO, C, Q, R, and S.
 * This interface combines methods from various read-related GraphQL resource interfaces.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseCrudRepository<ID, USER, D, C, Q>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseCrudService<ID, USER, D, DTO, C, Q, R>}.
 * @see BaseCountAllCrudGraphqlRestResource
 * @see BaseCountCrudGraphqlRestResource
 * @see BaseGetManyCrudGraphqlRestResource
 * @see BaseGetOneCrudGraphqlRestResource
 * @see BaseGetPageCrudGraphqlRestResource
 */
public interface BaseReadCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends BaseCountAllCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetByIdCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S> {
}
