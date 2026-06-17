package ir.msob.jima.crud.restful.reactive.test.resource.childdomain.read;

import ir.msob.jima.crud.reactive.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.reactive.test.dataprovider.childdomain.BaseChildDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.childdomain.read.BaseCountAllChildDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseCountAllDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.ParentChildDomainCrudRestResourceTest;
import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;

import java.io.Serializable;

/**
 * The {@code BaseCountAllChildDomainCrudRestReactiveResourceTest} interface represents a set of RESTful-specific test methods for counting all entities.
 * It extends both the {@code BaseCountAllChildDomainCrudReactiveResourceTest} and {@code BaseEmbeddedDomainCrudRestReactiveResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to count all entities using RESTful API. The result of the count operation is the total number of entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountAllDomainCrudReactiveResourceTest
 * @see ParentChildDomainCrudRestResourceTest
 */
public interface BaseCountAllChildDomainCrudRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseCountAllChildDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentChildDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to count all entities and extracts the result from the response.
     */
    @Override
    default void countAllRequest(ID parentId, Assertable<Long> assertable) {
        // Send a GET request to the COUNT_ALL operation URI
        // Prepare the request header
        // Expect the status to be equal to the COUNT_ALL operation status
        // Expect the body to be of type Long
        this.getWebTestClient()
                .get()
                .uri(String.format("%s/%s/%s/%s", getDomainUri(), parentId, getChildDomainUri(), Operations.COUNT_ALL))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.COUNT_ALL)
                .expectBody(Long.class)
                .value(assertable::assertThan);
    }
}