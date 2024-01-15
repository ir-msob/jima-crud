package ir.msob.jima.crud.api.graphql.restful.test;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.test.read.*;
import ir.msob.jima.crud.api.graphql.restful.test.write.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;

import java.io.Serializable;

/**
 * The {@code BaseCrudGraphqlResourceTest} interface represents a comprehensive set of GraphQL-specific test methods for performing CRUD operations on entities.
 * It extends multiple GraphQL-specific testing interfaces, each focusing on a specific CRUD operation, including reading, writing, updating, and deleting entities.
 * The interface provides a unified entry point for testing various GraphQL operations related to CRUD functionality.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <Q>    The type of query used for retrieving entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountAllCrudGraphqlResourceTest
 * @see BaseCountCrudGraphqlResourceTest
 * @see BaseGetManyCrudGraphqlResourceTest
 * @see BaseGetOneCrudGraphqlResourceTest
 * @see BaseGetPageCrudGraphqlResourceTest
 * @see BaseDeleteCrudGraphqlResourceTest
 * @see BaseDeleteManyCrudGraphqlResourceTest
 * @see BaseEditManyCrudGraphqlResourceTest
 * @see BaseEditCrudGraphqlResourceTest
 * @see BaseSaveManyCrudGraphqlResourceTest
 * @see BaseSaveCrudGraphqlResourceTest
 * @see BaseUpdateManyCrudGraphqlResourceTest
 * @see BaseUpdateCrudGraphqlResourceTest
 */
public interface BaseCrudGraphqlResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends
        BaseCountAllCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseCountCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetManyCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetOneCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetPageCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteManyCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditManyCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveManyCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateManyCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateCrudGraphqlResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}

