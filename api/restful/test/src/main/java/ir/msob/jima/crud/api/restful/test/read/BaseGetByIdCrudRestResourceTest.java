package ir.msob.jima.crud.api.restful.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.restful.test.ParentCrudRestResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseGetByIdCrudResourceTest;
import org.springframework.http.MediaType;

import java.io.Serializable;

/**
 * The {@code BaseGetByIdCrudRestResourceTest} interface represents a set of RESTful-specific test methods for retrieving an entity by its ID.
 * It extends both the {@code BaseGetByIdCrudResourceTest} and {@code ParentCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve an entity by its ID using RESTful API. The result of the get operation is the DTO of the retrieved entity.
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
 * @see BaseGetByIdCrudResourceTest
 * @see ParentCrudRestResourceTest
 */
public interface BaseGetByIdCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetByIdCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to retrieve an entity by its ID and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be retrieved.
     */
    @Override
    default void getByIdRequest(DTO savedDto, Assertable<DTO> assertable) {
        // Send a GET request to the base URI with the ID of the entity to be retrieved
        // Prepare the request header
        // Expect the status to be equal to the GET_BY_ID operation status
        // Expect the content type to be JSON
        // Expect the body to be of the DTO class type
        this.getWebTestClient()
                .get()
                .uri(String.format("%s/%s", getBaseUri(), savedDto.getDomainId()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.GET_BY_ID)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(this.getDataProvider().getService().getDtoClass())
                .value(assertable::assertThan);
    }
}