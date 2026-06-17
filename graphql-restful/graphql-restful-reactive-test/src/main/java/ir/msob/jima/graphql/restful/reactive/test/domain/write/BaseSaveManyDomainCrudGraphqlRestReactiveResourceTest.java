package ir.msob.jima.graphql.restful.reactive.test.domain.write;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseSaveManyDomainCrudReactiveResourceTest;
import ir.msob.jima.graphql.restful.api.model.DtosInput;
import ir.msob.jima.graphql.restful.api.model.DtosType;
import ir.msob.jima.graphql.restful.reactive.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;


/**
 * The {@code BaseSaveManyDomainCrudGraphqlRestReactiveResourceTest} interface represents a set of GraphQL-specific test methods for saving (creating or updating)
 * multiple entities using GraphQL mutations. It extends both the {@code BaseSaveManyChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces,
 * providing GraphQL-specific testing capabilities.
 * <p>
 * The interface defines a GraphQL mutation document and a corresponding path for saving (creating or updating) multiple entities. It includes
 * an implementation for making a request to save multiple entities with GraphQL, including the conversion of the DTOs to the required format.
 * The result of the save operation is a {@code DtosType} representing the DTOs of the saved entities and their class type.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseSaveManyDomainCrudReactiveResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseSaveManyDomainCrudGraphqlRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseSaveManyDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL mutation document for saving (creating or updating) multiple entities. It includes information about
     * the data transfer objects (DTOs) and the class type of the entities. The mutation includes a parameter for the DTOs input.
     */
    String DOCUMENT = "mutation saveMany($input: DtosInput){saveMany(input: $input){dtos,classType}}";

    /**
     * The path used in the GraphQL mutation to save (create or update) multiple entities.
     */
    String PATH = "saveMany";

    /**
     * Executes a GraphQL request to save (create or update) multiple entities and extracts the result from the response.
     *
     * @param dtos A collection of data transfer objects (DTOs) representing the entities to be saved.
     */
    @Override
    default void saveManyRequest(Collection<DTO> dtos, Assertable<Collection<DTO>> assertable) {
        DtosInput input = DtosInput.builder()
                .dtos(convertToStrings(dtos))
                .build();
        DtosType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", getObjectMapper().convertValue(input, Map.class))
                .execute()
                .path(PATH)
                .entity(DtosType.class)
                .get();
        assertable.assertThan(convertToDtos(res.getDtos()));
    }
}
