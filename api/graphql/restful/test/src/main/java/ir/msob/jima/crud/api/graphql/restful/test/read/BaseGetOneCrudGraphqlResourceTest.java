package ir.msob.jima.crud.api.graphql.restful.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CriteriaInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseGetOneCrudResourceTest;

import java.io.Serializable;

/**
 * The {@code BaseGetOneCrudGraphqlResourceTest} interface represents a set of GraphQL-specific test methods
 * for retrieving a single entity based on criteria in the context of CRUD operations. It extends both the
 * {@code BaseGetOneCrudResourceTest} and {@code ParentCrudGraphqlResourceTest} interfaces, providing GraphQL-specific
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
 * @see ParentCrudGraphqlResourceTest
 * @see ir.msob.jima.crud.test.ParentCrudGraphqlResourceTest
 */
public interface BaseGetOneCrudGraphqlResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetOneCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGraphqlResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL query document for retrieving a single entity based on criteria. It includes information about the
     * data transfer object (DTO) and the class type of the entity. The query includes a parameter for criteria input.
     */
    String DOCUMENT = "query getOne($input: CriteriaInput){getOne(input: $input){dto,classType}}";

    /**
     * The path used in the GraphQL query to obtain a single entity based on criteria.
     */
    String PATH = "getOne";

    /**
     * Executes a GraphQL request to retrieve a single entity based on criteria and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity.
     * @return A data transfer object (DTO) representing the retrieved entity.
     */
    @Override
    default DTO getOneRequest(DTO savedDto) {
        CriteriaInput input = CriteriaInput.builder()
                .criteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId())))
                .build();
        DtoType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(DtoType.class)
                .get();
        return convertToDto(res.getDto());
    }
}
