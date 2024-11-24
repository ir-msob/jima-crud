package ir.msob.jima.crud.api.graphql.restful.test.domain.read;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdInput;
import ir.msob.jima.crud.api.graphql.restful.test.domain.ParentCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseGetByIdCrudResourceTest;
import ir.msob.jima.crud.test.read.BaseGetOneCrudResourceTest;

import java.io.Serializable;

/**
 * The {@code BaseGetOneCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods
 * for retrieving a single entity based on criteria in the context of CRUD operations. It extends both the
 * {@code BaseGetOneCrudResourceTest} and {@code ParentCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific
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
 * @param <Q>    The type of query used for retrieving entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetOneCrudResourceTest
 * @see ParentCrudGraphqlRestResourceTest
 * @see ParentCrudGraphqlRestResourceTest
 */
public interface BaseGetByIdCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetByIdCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

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
                .id(convertToString(savedDto.getDomainId()))
                .build();
        DtoType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(DtoType.class)
                .get();
        assertable.assertThan(convertToDto(res.getDto()));
    }
}