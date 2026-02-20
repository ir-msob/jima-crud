package ir.msob.jima.crud.api.graphql.restful.test.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtosInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtosType;
import ir.msob.jima.crud.api.graphql.restful.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseUpdateManyDomainCrudResourceTest;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * The {@code BaseUpdateManyDomainCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods for updating multiple entities using GraphQL mutations.
 * It extends both the {@code BaseUpdateManyChildDomainCrudResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific testing capabilities.
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
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseUpdateManyDomainCrudResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseUpdateManyDomainCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseUpdateManyDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

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
     */
    @Override
    default void updateManyRequest(Collection<DTO> dtos, Assertable<Collection<DTO>> assertable) {
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

