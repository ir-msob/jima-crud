package ir.msob.jima.crud.api.restful.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.restful.test.ParentCrudRestResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseUpdateCrudResourceTest;
import org.springframework.http.MediaType;

import java.io.Serializable;

/**
 * The {@code BaseUpdateCrudRestResourceTest} interface represents a set of RESTful-specific test methods for updating an entity.
 * It extends both the {@code BaseUpdateCrudResourceTest} and {@code ParentCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to update an entity using RESTful API. The result of the update operation is the DTO of the updated entity.
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
 * @see BaseUpdateCrudResourceTest
 * @see ParentCrudRestResourceTest
 */
public interface BaseUpdateCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseUpdateCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to update an entity and extracts the result from the response.
     *
     * @param dto The data transfer object (DTO) representing the entity to be updated.
     */
    @Override
    default void updateRequest(DTO dto, Assertable<DTO> assertable) {
        // Send a PUT request to the UPDATE operation URI
        // Prepare the request header
        // Set the body of the request to the DTO
        // Expect the status to be equal to the UPDATE operation status
        // Expect the content type to be JSON
        // Expect the body to be of the DTO class type
        this.getWebTestClient()
                .put()
                .uri(String.format("%s/%s", getBaseUri(), Operations.UPDATE))
                .headers(this::prepareHeader)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.UPDATE)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(this.getDataProvider().getService().getDtoClass())
                .value(assertable::assertThan);
    }
}