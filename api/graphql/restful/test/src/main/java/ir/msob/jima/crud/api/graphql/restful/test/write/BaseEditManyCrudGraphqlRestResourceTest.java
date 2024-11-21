package ir.msob.jima.crud.api.graphql.restful.test.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CriteriaJsonPatchInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtosType;
import ir.msob.jima.crud.api.graphql.restful.test.ParentCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseEditManyCrudResourceTest;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseEditManyCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods
 * for editing (updating) multiple entities based on criteria using JSON Patch operations. It extends both the
 * {@code BaseEditManyCrudResourceTest} and {@code ParentCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific
 * testing capabilities.
 * <p>
 * The interface defines a GraphQL mutation document and a corresponding path for editing multiple entities based on criteria
 * using JSON Patch. It includes an implementation for making a request to edit multiple entities with GraphQL, including the
 * conversion of criteria and JSON Patch to the required format. The result of the edit operation is a {@code DtosType} representing
 * the DTOs of the edited entities and their class type.
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
 * @see BaseEditManyCrudResourceTest
 * @see ParentCrudGraphqlRestResourceTest
 * @see ParentCrudGraphqlRestResourceTest
 */
public interface BaseEditManyCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * The GraphQL mutation document for editing (updating) multiple entities based on criteria using JSON Patch operations.
     * It includes information about the data transfer objects (DTOs) and the class type of the entities.
     * The mutation includes parameters for criteria input and the JSON Patch.
     */
    String DOCUMENT = "mutation editMany($input: CriteriaJsonPatchInput){editMany(input: $input){dtos,classType}}";

    /**
     * The path used in the GraphQL mutation to edit (update) multiple entities based on criteria using JSON Patch operations.
     */
    String PATH = "editMany";

    /**
     * Executes a GraphQL request to edit (update) multiple entities based on criteria using JSON Patch operations
     * and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing one of the saved entities.
     * @param jsonPatch The JSON Patch operation to be applied for editing the entities.
     */
    @Override
    default void editManyRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<Collection<DTO>> assertable) {
        CriteriaJsonPatchInput input = CriteriaJsonPatchInput.builder()
                .criteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId())))
                .jsonPatch(convertToString(jsonPatch))
                .build();
        DtosType res = getGraphQlTester().document(DOCUMENT)
                .variable("input", input)
                .execute()
                .path(PATH)
                .entity(DtosType.class)
                .get();
        assertable.assertThan(convertToDtos(res.getDtos()));
    }
}
