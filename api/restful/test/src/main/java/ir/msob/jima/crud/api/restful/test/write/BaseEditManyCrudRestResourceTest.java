package ir.msob.jima.crud.api.restful.test.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.restful.test.ParentCrudRestResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseEditManyCrudResourceTest;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseEditManyCrudRestResourceTest} interface represents a set of RESTful-specific test methods for editing multiple entities.
 * It extends both the {@code BaseEditManyCrudResourceTest} and {@code ParentCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit multiple entities using RESTful API. The result of the edit operation is a collection of DTOs of the edited entities.
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
 * @see ParentCrudRestResourceTest
 */
public interface BaseEditManyCrudRestResourceTest<
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
        ParentCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to edit multiple entities and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the entities to be edited.
     * @param jsonPatch The JSON Patch representing the changes to be applied to the entities.
     */
    @Override
    default void editManyRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<Collection<DTO>> assertable) {
        // Send a PATCH request to the EDIT_MANY operation URI with the ID of the entities to be edited
        // Prepare the request header
        // Set the body of the request to the JSON Patch
        // Expect the status to be equal to the EDIT_MANY operation status
        // Expect the content type to be JSON
        // Expect the body to be of type Collection
        this.getWebTestClient()
                .patch()
                .uri(String.format("%s/%s?%s.eq=%s", getBaseUri(), Operations.EDIT_MANY, savedDto.getDomainIdName(), savedDto.getDomainId()))
                .headers(this::prepareHeader)
                .bodyValue(jsonPatch)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.EDIT_MANY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(Collection.class)
                .value(assertable::assertThan);
    }
}