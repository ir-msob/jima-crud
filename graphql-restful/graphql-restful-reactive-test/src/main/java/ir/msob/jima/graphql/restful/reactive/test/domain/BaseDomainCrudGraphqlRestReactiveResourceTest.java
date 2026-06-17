package ir.msob.jima.graphql.restful.reactive.test.domain;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.graphql.restful.reactive.test.domain.read.*;
import ir.msob.jima.graphql.restful.reactive.test.domain.write.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

/**
 * The {@code BaseDomainCrudGraphqlRestReactiveResourceTest} interface represents a comprehensive set of GraphQL-specific test methods for performing CRUD operations on entities.
 * It extends multiple GraphQL-specific testing interfaces, each focusing on a specific CRUD operation, including reading, writing, updating, and deleting entities.
 * The interface provides a unified entry point for testing various GraphQL operations childdomain to CRUD functionality.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountAllDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseCountDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseGetManyDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseGetOneDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseGetPageDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseDeleteDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseDeleteManyDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseEditManyDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseEditDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseSaveManyDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseSaveDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseUpdateManyDomainCrudGraphqlRestReactiveResourceTest
 * @see BaseUpdateDomainCrudGraphqlRestReactiveResourceTest
 */
public interface BaseDomainCrudGraphqlRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateDomainCrudGraphqlRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {
}

