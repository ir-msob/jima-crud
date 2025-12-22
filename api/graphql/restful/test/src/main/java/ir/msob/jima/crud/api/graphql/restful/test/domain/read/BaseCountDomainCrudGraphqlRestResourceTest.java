package ir.msob.jima.crud.api.graphql.restful.test.domain.read;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CountType;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CriteriaInput;
import ir.msob.jima.crud.api.graphql.restful.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.read.BaseCountDomainCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Map;

/**
 * The {@code BaseCountDomainCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods
 * for counting entities in the context of CRUD operations. It extends both the {@code BaseCountDomainCrudResourceTest}
 * and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific testing capabilities.
 * <p>
 * The interface defines a GraphQL query document and a corresponding path for counting entities based on criteria.
 * It also includes an implementation for making a count request with GraphQL, including the conversion of criteria
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
 * @see BaseCountDomainCrudResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseCountDomainCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseCountDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL query document for counting entities based on criteria. It includes information about the count
     * and the class type of the entities. The query includes a parameter for criteria input.
     */
    String DOCUMENT = "query count($input: CriteriaInput){count(input: $input){count,classType}}";

    /**
     * The path used in the GraphQL query to obtain the count of entities based on criteria.
     */
    String PATH = "count";

    /**
     * Executes a GraphQL request to count entities based on criteria and extracts the count result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity.
     */
    @SneakyThrows
    @Override
    default void countRequest(DTO savedDto, Assertable<Long> assertable) {
        CriteriaInput input = CriteriaInput.builder()
                .criteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId())))
                .build();
        CountType res = getGraphQlTester()
                .document(DOCUMENT)
                .variable("input", getObjectMapper().convertValue(input, Map.class))
                .execute()
                .path(PATH)
                .entity(CountType.class)
                .get();
        assertable.assertThan(res.getCount());
    }
}
