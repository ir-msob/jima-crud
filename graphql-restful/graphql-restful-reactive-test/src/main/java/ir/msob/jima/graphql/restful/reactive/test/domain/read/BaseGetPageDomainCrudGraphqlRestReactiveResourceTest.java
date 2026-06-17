package ir.msob.jima.graphql.restful.reactive.test.domain.read;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseGetPageDomainCrudReactiveResourceTest;
import ir.msob.jima.graphql.restful.api.model.CriteriaPageableInput;
import ir.msob.jima.graphql.restful.api.model.PageType;
import ir.msob.jima.graphql.restful.reactive.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.shared.PageDto;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.util.Map;

/**
 * The {@code BaseGetPageDomainCrudGraphqlRestReactiveResourceTest} interface represents a set of GraphQL-specific test methods
 * for retrieving a page of entities based on criteria in the context of CRUD operations. It extends both the
 * {@code BaseGetPageChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific
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
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetPageDomainCrudReactiveResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseGetPageDomainCrudGraphqlRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetPageDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
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
    default void getPageRequest(DTO savedDto, Assertable<PageDto<DTO>> assertable) throws DomainNotFoundException, BadRequestException {
        CriteriaPageableInput input = CriteriaPageableInput.builder()
                .criteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId())))
                .pageable(convertToString(PageRequest.of(0, 10)))
                .build();
        PageType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", getObjectMapper().convertValue(input, Map.class))
                .execute()
                .path(PATH)
                .entity(PageType.class)
                .get();
        assertable.assertThan(convertToPage(res.getPage()));
    }
}