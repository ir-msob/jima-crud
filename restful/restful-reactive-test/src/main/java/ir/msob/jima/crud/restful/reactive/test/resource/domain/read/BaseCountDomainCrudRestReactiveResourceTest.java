package ir.msob.jima.crud.restful.reactive.test.resource.domain.read;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseCountDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;

import java.io.Serializable;

/**
 * The {@code BaseCountChildDomainCrudRestReactiveResourceTest} interface represents a set of RESTful-specific test methods for counting entities.
 * It extends both the {@code BaseCountChildDomainCrudReactiveResourceTest} and {@code BaseEmbeddedDomainCrudRestReactiveResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to count entities using RESTful API. The result of the count operation is the total number of entities that match the given criteria.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountDomainCrudReactiveResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseCountDomainCrudRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseCountDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to count entities that match the given criteria and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be counted.
     */
    @Override
    default void countRequest(DTO savedDto, Assertable<Long> assertable) {
        // Send a GET request to the COUNT operation URI with the ID of the entity to be counted
        // Prepare the request header
        // Expect the status to be equal to the COUNT operation status
        // Expect the body to be of type Long
        this.getWebTestClient()
                .get()
                .uri(String.format("%s/%s?%s.eq=%s", getBaseUri(), Operations.COUNT, savedDto.getIdName(), savedDto.getId()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.COUNT)
                .expectBody(Long.class)
                .value(assertable::assertThan);
    }
}