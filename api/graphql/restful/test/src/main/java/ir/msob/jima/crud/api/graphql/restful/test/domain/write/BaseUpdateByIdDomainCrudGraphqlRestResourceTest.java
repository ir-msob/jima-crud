package ir.msob.jima.crud.api.graphql.restful.test.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseUpdateByIdDomainCrudResourceTest;
import ir.msob.jima.crud.test.domain.write.BaseUpdateDomainCrudResourceTest;

import java.io.Serializable;

/**
 * The {@code BaseUpdateDomainCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods for updating entities using GraphQL mutations.
 * It extends both the {@code BaseUpdateDomainCrudResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific testing capabilities.
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
 * @see BaseUpdateDomainCrudResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseUpdateByIdDomainCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseUpdateByIdDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
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
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(DtoType.class)
                .get();
        // Convert the result to the DTO type and return it
        assertable.assertThan(convertToDto(res.getDto()));
    }
}