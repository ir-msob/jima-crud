package ir.msob.jima.crud.api.graphql.restful.test.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CriteriaJsonPatchInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtosType;
import ir.msob.jima.crud.api.graphql.restful.test.domain.ParentDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseEditManyDomainCrudResourceTest;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseEditManyDomainCrudGraphqlRestResourceTest} interface represents a set of GraphQL-specific test methods
 * for editing (updating) multiple entities based on criteria using JSON Patch operations. It extends both the
 * {@code BaseEditManyDomainCrudResourceTest} and {@code ParentDomainCrudGraphqlRestResourceTest} interfaces, providing GraphQL-specific
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
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseEditManyDomainCrudResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 * @see ParentDomainCrudGraphqlRestResourceTest
 */
public interface BaseEditManyDomainCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseEditManyDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGraphqlRestResourceTest<ID, USER, D, DTO, C> {

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
                .criteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId())))
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
