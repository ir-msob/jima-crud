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
 * The {@code BaseDomainCrudGraphqlRestResourceTest} interface represents a comprehensive set of GraphQL-specific test methods for performing CRUD operations on entities.
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
 * @see BaseCountAllDomainCrudGraphqlRestResourceTest
 * @see BaseCountDomainCrudGraphqlRestResourceTest
 * @see BaseGetManyDomainCrudGraphqlRestResourceTest
 * @see BaseGetOneDomainCrudGraphqlRestResourceTest
 * @see BaseGetPageDomainCrudGraphqlRestResourceTest
 * @see BaseDeleteDomainCrudGraphqlRestResourceTest
 * @see BaseDeleteManyDomainCrudGraphqlRestResourceTest
 * @see BaseEditManyDomainCrudGraphqlRestResourceTest
 * @see BaseEditDomainCrudGraphqlRestResourceTest
 * @see BaseSaveManyDomainCrudGraphqlRestResourceTest
 * @see BaseSaveDomainCrudGraphqlRestResourceTest
 * @see BaseUpdateManyDomainCrudGraphqlRestResourceTest
 * @see BaseUpdateDomainCrudGraphqlRestResourceTest
 */
public interface BaseDomainCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C, R, S, DP> {
}

