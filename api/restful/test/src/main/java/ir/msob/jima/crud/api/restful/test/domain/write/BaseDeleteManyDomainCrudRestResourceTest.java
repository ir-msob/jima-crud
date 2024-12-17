package ir.msob.jima.crud.api.restful.test.domain.write;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.restful.test.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseDeleteManyDomainCrudResourceTest;

import java.io.Serializable;
import java.util.Set;

/**
 * The {@code BaseDeleteManyDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for deleting multiple entities.
 * It extends both the {@code BaseDeleteManyDomainCrudResourceTest} and {@code ParentChildCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to delete multiple entities using RESTful API. The result of the delete operation is a set of IDs of the deleted entities.
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
 * @see BaseDeleteManyDomainCrudResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseDeleteManyDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseDeleteManyDomainCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to delete multiple entities and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entities to be deleted.
     * @throws DomainNotFoundException If the entity is not found.
     * @throws BadRequestException     If the request is not valid.
     */
    @Override
    default void deleteManyRequest(DTO savedDto, Assertable<Set<ID>> assertable) throws DomainNotFoundException, BadRequestException {
        // Send a DELETE request to the DELETE_MANY operation URI with the ID of the entities to be deleted
        // Prepare the request header
        // Expect the status to be equal to the DELETE_MANY operation status
        // Expect the body to be of type Set
        this.getWebTestClient()
                .delete()
                .uri(String.format("%s/%s?%s.eq=%s", getBaseUri(), Operations.DELETE_MANY, savedDto.getIdName(), savedDto.getId()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus()
                .isEqualTo(OperationsStatus.DELETE_MANY)
                .expectBody(Set.class)
                .value(assertable::assertThan);

    }
}