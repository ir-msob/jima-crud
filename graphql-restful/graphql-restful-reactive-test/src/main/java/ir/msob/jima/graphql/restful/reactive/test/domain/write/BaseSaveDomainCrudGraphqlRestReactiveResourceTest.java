package ir.msob.jima.graphql.restful.reactive.test.domain.write;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseSaveDomainCrudReactiveResourceTest;
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
 * The {@code BaseSaveDomainCrudGraphqlRestReactiveResourceTest} interface represents a set of GraphQL-specific test methods for saving (creating or updating)
 * entities using GraphQL mutations. It extends both the {@code BaseSaveChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces,
 * providing GraphQL-specific testing capabilities.
 * <p>
 * The interface defines a GraphQL mutation document and a corresponding path for saving (creating or updating) an entity. It includes
 * an implementation for making a request to save an entity with GraphQL, including the conversion of the DTO to the required format.
 * The result of the save operation is a {@code DtoType} representing the DTO of the saved entity and its class type.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseSaveDomainCrudReactiveResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseSaveDomainCrudGraphqlRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseSaveDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL mutation document for saving (creating or updating) an entity. It includes information about
     * the data transfer object (DTO) and the class type of the entity. The mutation includes a parameter for the DTO input.
     */
    String DOCUMENT = "mutation save($input: DtoInput){save(input: $input){dto,classType}}";

    /**
     * The path used in the GraphQL mutation to save (create or update) an entity.
     */
    String PATH = "save";

    /**
     * Executes a GraphQL request to save (create or update) an entity and extracts the result from the response.
     *
     * @param dto The data transfer object (DTO) representing the entity to be saved.
     */
    @Override
    default void saveRequest(DTO dto, Assertable<DTO> assertable) {
        DtoInput input = DtoInput.builder()
                .dto(convertToString(dto))
                .build();
        DtoType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", getObjectMapper().convertValue(input, Map.class))
                .execute()
                .path(PATH)
                .entity(DtoType.class)
                .get();
        assertable.assertThan(convertToDto(res.getDto()));
    }
}
