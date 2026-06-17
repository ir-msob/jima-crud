package ir.msob.jima.graphql.restful.reactive.test.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseEditByIdDomainCrudReactiveResourceTest;
import ir.msob.jima.graphql.restful.api.model.DtoType;
import ir.msob.jima.graphql.restful.api.model.IdJsonPatchInput;
import ir.msob.jima.graphql.restful.reactive.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;

import java.io.Serializable;
import java.util.Map;

/**
 * The {@code BaseEditDomainCrudGraphqlRestReactiveResourceTest} interface represents a set of GraphQL-specific test methods
 * for editing (updating) an entity based on criteria using JSON Patch operations. It extends both the
 * {@code BaseEditChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific
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
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseEditByIdDomainCrudReactiveResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseEditByIdDomainCrudGraphqlRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseEditByIdDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

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
     */
    @Override
    default void editByIdRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<DTO> assertable) {
        // Create an instance of IdJsonPatchInput with the ID of the saved entity and the JSON Patch operation
        IdJsonPatchInput input = IdJsonPatchInput.builder()
                .id(convertToString(savedDto.getId()))
                .jsonPatch(convertToString(jsonPatch))
                .build();

        // Execute the GraphQL mutation with the created input and extract the result from the response
        DtoType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", getObjectMapper().convertValue(input, Map.class))
                .execute()
                .path(PATH)
                .entity(DtoType.class)
                .get();

        // Convert the result to the DTO type and return it
        assertable.assertThan(convertToDto(res.getDto()));
    }
}