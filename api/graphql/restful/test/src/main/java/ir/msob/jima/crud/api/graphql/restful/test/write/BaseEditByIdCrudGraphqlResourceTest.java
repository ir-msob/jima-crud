package ir.msob.jima.crud.api.graphql.restful.test.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdJsonPatchInput;
import ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseEditByIdCrudResourceTest;
import ir.msob.jima.crud.test.write.BaseEditCrudResourceTest;

import java.io.Serializable;

/**
 * The {@code BaseEditCrudGraphqlResourceTest} interface represents a set of GraphQL-specific test methods
 * for editing (updating) an entity based on criteria using JSON Patch operations. It extends both the
 * {@code BaseEditCrudResourceTest} and {@code ParentCrudGraphqlResourceTest} interfaces, providing GraphQL-specific
 * testing capabilities.
 * <p>
 * The interface defines a GraphQL mutation document and a corresponding path for editing an entity based on criteria
 * using JSON Patch. It includes an implementation for making a request to edit an entity with GraphQL, including the conversion
 * of criteria and JSON Patch to the required format. The result of the edit operation is a {@code DtoType} representing the DTO
 * of the edited entity and its class type.
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
 * @see BaseEditCrudResourceTest
 * @see ParentCrudGraphqlResourceTest
 * @see ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlResourceTest
 */
public interface BaseEditByIdCrudGraphqlResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditByIdCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGraphqlResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL mutation document for editing (updating) an entity based on criteria using JSON Patch operations.
     * It includes information about the data transfer object (DTO) and the class type of the entity.
     * The mutation includes parameters for criteria input and the JSON Patch.
     */
    String DOCUMENT = "mutation editById($input: IdJsonPatchInput){editById(input: $input){dto,classType}}";

    /**
     * The path used in the GraphQL mutation to edit (update) an entity based on criteria using JSON Patch operations.
     */
    String PATH = "editById";

    /**
     * Executes a GraphQL request to edit (update) an entity based on criteria using JSON Patch operations and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the saved entity.
     * @param jsonPatch The JSON Patch operation to be applied for editing the entity.
     * @return The data transfer object (DTO) representing the edited entity.
     */
    @Override
    default DTO editByIdRequest(DTO savedDto, JsonPatch jsonPatch) {
        // Create an instance of IdJsonPatchInput with the ID of the saved entity and the JSON Patch operation
        IdJsonPatchInput input = IdJsonPatchInput.builder()
                .id(convertToString(savedDto.getDomainId()))
                .jsonPatch(convertToString(jsonPatch))
                .build();

        // Execute the GraphQL mutation with the created input and extract the result from the response
        DtoType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(DtoType.class)
                .get();

        // Convert the result to the DTO type and return it
        return convertToDto(res.getDto());
    }
}