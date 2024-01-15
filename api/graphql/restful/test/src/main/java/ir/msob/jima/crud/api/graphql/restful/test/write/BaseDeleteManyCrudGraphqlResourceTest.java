package ir.msob.jima.crud.api.graphql.restful.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CriteriaInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdsType;
import ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseDeleteManyCrudResourceTest;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code BaseDeleteManyCrudGraphqlResourceTest} interface represents a set of GraphQL-specific test methods
 * for deleting multiple entities based on criteria in the context of CRUD operations. It extends both the
 * {@code BaseDeleteManyCrudResourceTest} and {@code ParentCrudGraphqlResourceTest} interfaces, providing GraphQL-specific
 * testing capabilities.
 * <p>
 * The interface defines a GraphQL mutation document and a corresponding path for deleting multiple entities based on criteria.
 * It also includes an implementation for making a request to delete multiple entities with GraphQL, including the conversion of criteria
 * to the required format. The result of the delete operation is an {@code IdsType} representing the IDs of the deleted entities and their class type.
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
 * @see BaseDeleteManyCrudResourceTest
 * @see ParentCrudGraphqlResourceTest
 * @see ir.msob.jima.crud.test.ParentCrudGraphqlResourceTest
 */
public interface BaseDeleteManyCrudGraphqlResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseDeleteManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGraphqlResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL mutation document for deleting multiple entities based on criteria.
     * It includes information about the data transfer object (DTO) and the class type of the entity.
     * The mutation includes a parameter for criteria input.
     */
    String DOCUMENT = "mutation deleteMany($input: CriteriaInput){deleteMany(input: $input){ids,classType}}";

    /**
     * The path used in the GraphQL mutation to delete multiple entities based on criteria.
     */
    String PATH = "deleteMany";

    /**
     * Executes a GraphQL request to delete multiple entities based on criteria and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity.
     * @return A set of IDs representing the deleted entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is malformed or contains invalid parameters.
     */
    @Override
    default Set<ID> deleteManyRequest(DTO savedDto) throws DomainNotFoundException, BadRequestException {
        CriteriaInput input = CriteriaInput.builder()
                .criteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId())))
                .build();
        IdsType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(IdsType.class)
                .get();
        return res.getIds().stream().map(this.getIdService()::<ID>of).collect(Collectors.toSet());
    }
}
