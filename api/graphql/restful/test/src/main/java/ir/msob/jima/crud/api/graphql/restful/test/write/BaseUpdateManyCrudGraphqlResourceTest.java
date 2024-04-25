package ir.msob.jima.crud.api.graphql.restful.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtosInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtosType;
import ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseUpdateManyCrudResourceTest;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseUpdateManyCrudGraphqlResourceTest} interface represents a set of GraphQL-specific test methods for updating multiple entities using GraphQL mutations.
 * It extends both the {@code BaseUpdateManyCrudResourceTest} and {@code ParentCrudGraphqlResourceTest} interfaces, providing GraphQL-specific testing capabilities.
 * <p>
 * The interface defines a GraphQL mutation document and a corresponding path for updating multiple entities. It includes an implementation for making a request
 * to update multiple entities with GraphQL, including the conversion of the DTOs to the required format. The result of the update operation is a {@code DtosType}
 * representing the updated entities and their class type.
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
 * @see BaseUpdateManyCrudResourceTest
 * @see ParentCrudGraphqlResourceTest
 * @see ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlResourceTest
 */
public interface BaseUpdateManyCrudGraphqlResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseUpdateManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGraphqlResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL mutation document for updating multiple entities. It includes information about the data transfer objects (DTOs) and the class type of the entities.
     * The mutation includes a parameter for the DTOs input.
     */
    String DOCUMENT = "mutation updateMany($input: DtosInput){updateMany(input: $input){dtos,classType}}";

    /**
     * The path used in the GraphQL mutation to update multiple entities.
     */
    String PATH = "updateMany";

    /**
     * Executes a GraphQL request to update multiple entities and extracts the result from the response.
     *
     * @param dtos The collection of data transfer objects (DTOs) representing the entities to be updated.
     * @return A collection of data transfer objects (DTOs) representing the updated entities.
     */
    @Override
    default Collection<DTO> updateManyRequest(Collection<DTO> dtos) {
        DtosInput input = DtosInput.builder()
                .dtos(convertToStrings(dtos))
                .build();
        DtosType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(DtosType.class)
                .get();
        return convertToDtos(res.getDtos());
    }
}

