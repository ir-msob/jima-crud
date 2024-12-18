package ir.msob.jima.crud.api.restful.test.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.restful.test.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseEditDomainCrudResourceTest;
import org.springframework.http.MediaType;

import java.io.Serializable;

/**
 * The {@code BaseEditDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for editing an entity.
 * It extends both the {@code BaseEditDomainCrudResourceTest} and {@code ParentChildCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit an entity using RESTful API. The result of the edit operation is the DTO of the edited entity.
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
 * @see BaseEditDomainCrudResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseEditDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditDomainCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to edit an entity and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the entity to be edited.
     * @param jsonPatch The JSON Patch representing the changes to be applied to the entity.
     */
    @Override
    default void editRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<DTO> assertable) {
        // Send a PATCH request to the EDIT operation URI with the ID of the entity to be edited
        // Prepare the request header
        // Set the body of the request to the JSON Patch
        // Expect the status to be equal to the EDIT operation status
        // Expect the content type to be JSON
        // Expect the body to be of the DTO class type
        this.getWebTestClient()
                .patch()
                .uri(String.format("%s/%s?%s.eq=%s", getBaseUri(), Operations.EDIT, savedDto.getIdName(), savedDto.getId()))
                .headers(this::prepareHeader)
                .bodyValue(jsonPatch)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.EDIT)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(this.getDataProvider().getService().getDtoClass())
                .value(assertable::assertThan);
    }
}