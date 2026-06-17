package ir.msob.jima.graphql.restful.reactive.test.domain.read;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseGetByIdDomainCrudReactiveResourceTest;
import ir.msob.jima.graphql.restful.api.model.DtoType;
import ir.msob.jima.graphql.restful.api.model.IdInput;
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
 * The {@code BaseGetOneDomainCrudGraphqlRestReactiveResourceTest} interface represents a set of GraphQL-specific test methods
 * for retrieving a single entity based on criteria in the context of CRUD operations. It extends both the
 * {@code BaseGetOneChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific
 * testing capabilities.
 * <p>
 * The interface defines a GraphQL query document and a corresponding path for retrieving a single entity based on criteria.
 * It also includes an implementation for making a request to get a single entity with GraphQL, including the conversion of criteria
 * to the required format.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetByIdDomainCrudReactiveResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseGetByIdDomainCrudGraphqlRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetByIdDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL query document for retrieving a single entity based on criteria. It includes information about the
     * data transfer object (DTO) and the class type of the entity. The query includes a parameter for criteria input.
     */
    String DOCUMENT = "query getById($input: IdInput){getById(input: $input){dto,classType}}";

    /**
     * The path used in the GraphQL query to obtain a single entity based on criteria.
     */
    String PATH = "getById";

    /**
     * Executes a GraphQL request to retrieve a single entity based on criteria and extracts the result from the response.
     * This method is responsible for creating the GraphQL request, executing it, and processing the response.
     * It uses the GraphQL tester to execute the request and then extracts the entity from the response using the specified path.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity. This DTO is used to create the criteria for the GraphQL request.
     */
    @Override
    default void getByIdRequest(DTO savedDto, Assertable<DTO> assertable) {
        IdInput input = IdInput.builder()
                .id(convertToString(savedDto.getId()))
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