package ir.msob.jima.crud.api.graphql.restful.test.domain;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.graphql.restful.test.domain.read.*;
import ir.msob.jima.crud.api.graphql.restful.test.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;

import java.io.Serializable;

/**
 * The {@code BaseCrudGraphqlRestResourceTest} interface represents a comprehensive set of GraphQL-specific test methods for performing CRUD operations on entities.
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
 * @see BaseCountAllCrudGraphqlRestResourceTest
 * @see BaseCountCrudGraphqlRestResourceTest
 * @see BaseGetManyCrudGraphqlRestResourceTest
 * @see BaseGetOneCrudGraphqlRestResourceTest
 * @see BaseGetPageCrudGraphqlRestResourceTest
 * @see BaseDeleteCrudGraphqlRestResourceTest
 * @see BaseDeleteManyCrudGraphqlRestResourceTest
 * @see BaseEditManyCrudGraphqlRestResourceTest
 * @see BaseEditCrudGraphqlRestResourceTest
 * @see BaseSaveManyCrudGraphqlRestResourceTest
 * @see BaseSaveCrudGraphqlRestResourceTest
 * @see BaseUpdateManyCrudGraphqlRestResourceTest
 * @see BaseUpdateCrudGraphqlRestResourceTest
 */
public interface BaseCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends
        BaseCountAllCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseCountCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetManyCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetByIdCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetOneCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetPageCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteByIdCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteManyCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditManyCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditByIdCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveManyCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateManyCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateByIdCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}

