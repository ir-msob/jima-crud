package ir.msob.jima.crud.api.restful.test.childdomain.read;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.restful.test.childdomain.ParentChildDomainCrudRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.childdomain.BaseChildDomainCrudDataProvider;
import ir.msob.jima.crud.test.childdomain.read.BaseCountChildDomainCrudResourceTest;
import ir.msob.jima.crud.test.domain.read.BaseCountDomainCrudResourceTest;

import java.io.Serializable;

/**
 * The {@code BaseCountChildDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for counting entities.
 * It extends both the {@code BaseCountChildDomainCrudResourceTest} and {@code BaseEmbeddedDomainCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
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
 * @see BaseCountDomainCrudResourceTest
 * @see ParentChildDomainCrudRestResourceTest
 */
public interface BaseCountChildDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseCountChildDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentChildDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to count entities that match the given criteria and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be counted.
     */
    @Override
    default void countRequest(ID parentId, DTO savedDto, Assertable<Long> assertable) {
        // Send a GET request to the COUNT operation URI with the ID of the entity to be counted
        // Prepare the request header
        // Expect the status to be equal to the COUNT operation status
        // Expect the body to be of type Long
        this.getWebTestClient()
                .get()
                .uri(String.format("%s/%s/%s/%s?%s.eq=%s", getDomainUri(), parentId, getChildDomainUri(), Operations.COUNT, savedDto.getIdName(), savedDto.getId()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.COUNT)
                .expectBody(Long.class)
                .value(assertable::assertThan);
    }
}