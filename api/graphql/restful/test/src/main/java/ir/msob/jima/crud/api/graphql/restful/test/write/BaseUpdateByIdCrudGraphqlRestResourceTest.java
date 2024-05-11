package ir.msob.jima.crud.api.graphql.restful.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseUpdateByIdCrudResourceTest;
import ir.msob.jima.crud.test.write.BaseUpdateCrudResourceTest;

import java.io.Serializable;

/**
 * The {@code BaseUpdateCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods for updating entities using GraphQL mutations.
 * It extends both the {@code BaseUpdateCrudResourceTest} and {@code ParentCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific testing capabilities.
 * <p>
 * The interface defines a GraphQL mutation document and a corresponding path for updating entities. It includes an implementation for making a request
 * to update an entity with GraphQL, including the conversion of the DTO to the required format. The result of the update operation is a {@code DtoType}
 * representing the updated entity and its class type.
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
 * @see BaseUpdateCrudResourceTest
 * @see ParentCrudGraphqlRestResourceTest
 * @see ParentCrudGraphqlRestResourceTest
 */
public interface BaseUpdateByIdCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseUpdateByIdCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL mutation document for updating an entity. It includes information about the data transfer object (DTO) and the class type of the entity.
     * The mutation includes a parameter for the DTO input.
     */
    String DOCUMENT = "mutation updateById($input: DtoInput){updateById(input: $input){dto,classType}}";

    /**
     * The path used in the GraphQL mutation to update an entity.
     */
    String PATH = "updateById";

    /**
     * Executes a GraphQL request to update an entity and extracts the result from the response.
     *
     * @param dto The data transfer object (DTO) representing the entity to be updated.
     */
    @Override
    default void updateByIdRequest(DTO dto, Assertable<DTO> assertable) {
        // Create an instance of DtoInput with the ID and DTO of the entity to be updated
        DtoInput input = DtoInput.builder()
                .id(convertToString(dto.getDomainId()))
                .dto(convertToString(dto))
                .build();
        // Execute the GraphQL mutation with the created input and extract the result from the response
        DtoType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(DtoType.class)
                .get();
        // Convert the result to the DTO type and return it
        assertable.assertThan(convertToDto(res.getDto()));
    }
}