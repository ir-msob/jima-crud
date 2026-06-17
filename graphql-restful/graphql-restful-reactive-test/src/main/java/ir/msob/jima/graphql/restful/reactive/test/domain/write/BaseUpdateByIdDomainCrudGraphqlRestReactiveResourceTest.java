package ir.msob.jima.graphql.restful.reactive.test.domain.write;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseUpdateByIdDomainCrudReactiveResourceTest;
import ir.msob.jima.graphql.restful.api.model.DtoInput;
import ir.msob.jima.graphql.restful.api.model.DtoType;
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
 * The {@code BaseUpdateDomainCrudGraphqlRestReactiveResourceTest} interface represents a set of GraphQL-specific test methods for updating entities using GraphQL mutations.
 * It extends both the {@code BaseUpdateChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific testing capabilities.
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
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseUpdateByIdDomainCrudReactiveResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseUpdateByIdDomainCrudGraphqlRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseUpdateByIdDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

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
                .id(convertToString(dto.getId()))
                .dto(convertToString(dto))
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