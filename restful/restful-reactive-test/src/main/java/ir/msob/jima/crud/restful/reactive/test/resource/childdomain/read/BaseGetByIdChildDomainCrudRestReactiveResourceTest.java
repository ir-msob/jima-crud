package ir.msob.jima.crud.restful.reactive.test.resource.childdomain.read;

import ir.msob.jima.crud.reactive.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.reactive.test.dataprovider.childdomain.BaseChildDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.childdomain.read.BaseGetByIdChildDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseGetByIdDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.ParentChildDomainCrudRestResourceTest;
import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import org.springframework.http.MediaType;

import java.io.Serializable;

/**
 * The {@code BaseGetByIdChildDomainCrudRestReactiveResourceTest} interface represents a set of RESTful-specific test methods for retrieving an entity by its ID.
 * It extends both the {@code BaseGetByIdChildDomainCrudReactiveResourceTest} and {@code BaseEmbeddedDomainCrudRestReactiveResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve an entity by its ID using RESTful API. The result of the get operation is the DTO of the retrieved entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetByIdDomainCrudReactiveResourceTest
 * @see ParentChildDomainCrudRestResourceTest
 */
public interface BaseGetByIdChildDomainCrudRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetByIdChildDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentChildDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to retrieve an entity by its ID and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be retrieved.
     */
    @Override
    default void getByIdRequest(ID parentId, DTO savedDto, Assertable<DTO> assertable) {
        // Send a GET request to the base URI with the ID of the entity to be retrieved
        // Prepare the request header
        // Expect the status to be equal to the GET_BY_ID operation status
        // Expect the content type to be JSON
        // Expect the body to be of the DTO class type
        this.getWebTestClient()
                .get()
                .uri(String.format("%s/%s/%s/%s", getDomainUri(), parentId, getChildDomainUri(), savedDto.getId()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.GET_BY_ID)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(this.getDataProvider().getService().getDtoClass())
                .value(assertable::assertThan);
    }
}