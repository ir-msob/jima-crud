package ir.msob.jima.crud.api.graphql.restful.service.resource;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.service.resource.write.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;

import java.io.Serializable;

/**
 * The {@code BaseWriteCrudGraphQLResource} interface extends several GraphQL resource interfaces,
 * providing write-specific GraphQL operations for a CRUD service.
 * It is generic and allows customization for different types such as ID, USER, D, DTO, C, Q, R, and S.
 * This interface combines methods from various write-related GraphQL resource interfaces.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser<ID>}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseCrudRepository<ID, USER, D, C, Q>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseCrudService<ID, USER, D, DTO, C, Q, R>}.
 * @see BaseDeleteCrudGraphQLResource
 * @see BaseDeleteManyCrudGraphQLResource
 * @see BaseEditCrudGraphQLResource
 * @see BaseEditManyCrudGraphQLResource
 * @see BaseUpdateCrudGraphQLResource
 * @see BaseUpdateManyCrudGraphQLResource
 * @see BaseSaveCrudGraphQLResource
 * @see BaseSaveManyCrudGraphQLResource
 */
public interface BaseWriteCrudGraphQLResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends BaseDeleteCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteByIdCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateByIdCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyCrudGraphQLResource<ID, USER, D, DTO, C, Q, R, S> {
}
