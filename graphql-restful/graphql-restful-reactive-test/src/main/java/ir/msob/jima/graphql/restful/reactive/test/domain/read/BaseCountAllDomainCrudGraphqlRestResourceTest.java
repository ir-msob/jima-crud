package ir.msob.jima.graphql.restful.reactive.test.domain.read;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
//import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseCountAllDomainCrudResourceTest;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseCountAllDomainCrudResourceTest;
import ir.msob.jima.graphql.restful.api.model.CountType;
import ir.msob.jima.graphql.restful.reactive.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;

import java.io.Serializable;

/**
 * The {@code BaseCountAllDomainCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods
 * for counting all entities in the context of CRUD operations. It extends both the {@code BaseCountAllChildDomainCrudResourceTest}
 * and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific testing capabilities.
 * <p>
 * The interface defines a GraphQL query document and a corresponding path for counting all entities.
 * It also includes an implementation for making a countAll request using GraphQL and extracting the count result.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountAllDomainCrudResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseCountAllDomainCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseCountAllDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL query document for counting all entities. It includes information about the count
     * and the class type of the entities.
     */
    String DOCUMENT = "query countAll{countAll{count,classType}}";

    /**
     * The path used in the GraphQL query to obtain the count of all entities.
     */
    String PATH = "countAll";

    /**
     * Executes a GraphQL request to count all entities and extracts the count result from the response.
     */
    @Override
    default void countAllRequest(Assertable<Long> assertable) {
        CountType res = getGraphQlTester()
                .document(DOCUMENT)
                .execute()
                .path(PATH)
                .entity(CountType.class)
                .get();
        assertable.assertThan(res.getCount());
    }
}
