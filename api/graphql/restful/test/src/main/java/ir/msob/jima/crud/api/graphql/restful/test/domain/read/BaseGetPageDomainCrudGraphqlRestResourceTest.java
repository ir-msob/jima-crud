package ir.msob.jima.crud.api.graphql.restful.test.domain.read;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CriteriaPageableInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.PageType;
import ir.msob.jima.crud.api.graphql.restful.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.read.BaseGetPageDomainCrudResourceTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;

/**
 * The {@code BaseGetPageDomainCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods
 * for retrieving a page of entities based on criteria in the context of CRUD operations. It extends both the
 * {@code BaseGetPageDomainCrudResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific
 * testing capabilities.
 * <p>
 * The interface defines a GraphQL query document and a corresponding path for retrieving a page of entities based on criteria.
 * It also includes an implementation for making a request to get a page of entities with GraphQL, including the conversion of criteria
 * and pageable information to the required format.
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
 * @see BaseGetPageDomainCrudResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseGetPageDomainCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetPageDomainCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL query document for retrieving a page of entities based on criteria and pageable information.
     * It includes information about the data transfer object (DTO) and the class type of the entity.
     * The query includes parameters for criteria input and pageable input.
     */
    String DOCUMENT = "query getPage($input: CriteriaPageableInput){getPage(input: $input){page,classType}}";

    /**
     * The path used in the GraphQL query to obtain a page of entities based on criteria.
     */
    String PATH = "getPage";

    /**
     * Executes a GraphQL request to retrieve a page of entities based on criteria and pageable information,
     * and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is malformed or contains invalid parameters.
     */
    @Override
    default void getPageRequest(DTO savedDto, Assertable<Page<DTO>> assertable) throws DomainNotFoundException, BadRequestException {
        CriteriaPageableInput input = CriteriaPageableInput.builder()
                .criteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId())))
                .pageable(convertToString(PageRequest.of(0, 10)))
                .build();
        PageType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(PageType.class)
                .get();
        assertable.assertThan(convertToPage(res.getPage()));
    }
}