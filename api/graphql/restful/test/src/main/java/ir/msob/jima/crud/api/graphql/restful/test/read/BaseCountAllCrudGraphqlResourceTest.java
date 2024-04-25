package ir.msob.jima.crud.api.graphql.restful.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CountType;
import ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseCountAllCrudResourceTest;

import java.io.Serializable;

/**
 * The {@code BaseCountAllCrudGraphqlResourceTest} interface represents a set of GraphQL-specific test methods
 * for counting all entities in the context of CRUD operations. It extends both the {@code BaseCountAllCrudResourceTest}
 * and {@code ParentCrudGraphqlResourceTest} interfaces, providing GraphQL-specific testing capabilities.
 * <p>
 * The interface defines a GraphQL query document and a corresponding path for counting all entities.
 * It also includes an implementation for making a countAll request using GraphQL and extracting the count result.
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
 * @see BaseCountAllCrudResourceTest
 * @see ParentCrudGraphqlResourceTest
 * @see ParentCrudGraphqlResourceTest
 */
public interface BaseCountAllCrudGraphqlResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseCountAllCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGraphqlResourceTest<ID, USER, D, DTO, C> {

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
     *
     * @return The count of all entities.
     */
    @Override
    default Long countAllRequest() {
        CountType res = getGraphQlTester().document(DOCUMENT)
                .execute()
                .path(PATH)
                .entity(CountType.class)
                .get();
        return res.getCount();
    }
}
