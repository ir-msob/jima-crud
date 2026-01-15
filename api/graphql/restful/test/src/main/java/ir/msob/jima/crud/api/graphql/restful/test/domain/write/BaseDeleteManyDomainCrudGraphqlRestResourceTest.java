package ir.msob.jima.crud.api.graphql.restful.test.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CriteriaInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdsType;
import ir.msob.jima.crud.api.graphql.restful.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseDeleteManyDomainCrudResourceTest;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code BaseDeleteManyDomainCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods
 * for deleting multiple entities based on criteria in the context of CRUD operations. It extends both the
 * {@code BaseDeleteManyDomainCrudResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific
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
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseDeleteManyDomainCrudResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseDeleteManyDomainCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseDeleteManyDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

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
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is malformed or contains invalid parameters.
     */
    @Override
    default void deleteManyRequest(DTO savedDto, Assertable<Set<ID>> assertable) throws DomainNotFoundException, BadRequestException {
        CriteriaInput input = CriteriaInput.builder()
                .criteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId())))
                .build();
        IdsType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", getObjectMapper().convertValue(input, Map.class))
                .execute()
                .path(PATH)
                .entity(IdsType.class)
                .get();
        assertable.assertThan(res.getIds().stream().map(this.getIdService()::<ID>of).collect(Collectors.toSet()));
    }
}
