package ir.msob.jima.crud.api.graphql.restful.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdType;
import ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseDeleteByIdCrudResourceTest;
import ir.msob.jima.crud.test.write.BaseDeleteCrudResourceTest;

import java.io.Serializable;

/**
 * The {@code BaseDeleteCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods
 * for deleting an entity based on criteria in the context of CRUD operations. It extends both the
 * {@code BaseDeleteCrudResourceTest} and {@code ParentCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific
 * testing capabilities.
 * <p>
 * The interface defines a GraphQL mutation document and a corresponding path for deleting an entity based on criteria.
 * It also includes an implementation for making a request to delete an entity with GraphQL, including the conversion of criteria
 * to the required format. The result of the delete operation is an {@code IdType} representing the deleted entity's ID and class type.
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
 * @see BaseDeleteCrudResourceTest
 * @see ParentCrudGraphqlRestResourceTest
 * @see ParentCrudGraphqlRestResourceTest
 */
public interface BaseDeleteByIdCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseDeleteByIdCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL mutation document for deleting an entity based on criteria.
     * It includes information about the data transfer object (DTO) and the class type of the entity.
     * The mutation includes a parameter for criteria input.
     */
    String DOCUMENT = "mutation deleteById($input: IdInput){deleteById(input: $input){id,classType}}";

    /**
     * The path used in the GraphQL mutation to delete an entity based on criteria.
     */
    String PATH = "deleteById";

    /**
     * Executes a GraphQL request to delete an entity based on criteria and extracts the result from the response.
     * This method is responsible for creating the GraphQL request, executing it, and processing the response.
     * It uses the GraphQL tester to execute the request and then extracts the entity from the response using the specified path.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity. This DTO is used to create the criteria for the GraphQL request.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is malformed or contains invalid parameters.
     */
    @Override
    default void deleteByIdRequest(DTO savedDto, Assertable<ID> assertable) throws DomainNotFoundException, BadRequestException {
        IdInput input = IdInput.builder()
                .id(convertToString(savedDto.getDomainId()))
                .build();
        IdType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(IdType.class)
                .get();
        assertable.assertThan(getIdService().of(res.getId()));
    }
}