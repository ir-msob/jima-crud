package ir.msob.jima.crud.restful.reactive.test.resource.domain.write;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseDeleteDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;

import java.io.Serializable;

/**
 * The {@code BaseDeleteChildDomainCrudRestReactiveResourceTest} interface represents a set of RESTful-specific test methods for deleting an entity.
 * It extends both the {@code BaseDeleteChildDomainCrudReactiveResourceTest} and {@code BaseEmbeddedDomainCrudRestReactiveResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to delete an entity using RESTful API. The result of the delete operation is the ID of the deleted entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseDeleteDomainCrudReactiveResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseDeleteDomainCrudRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseDeleteDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to delete an entity and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be deleted.
     * @throws DomainNotFoundException If the entity is not found.
     * @throws BadRequestException     If the request is not valid.
     */
    @Override
    default void deleteRequest(DTO savedDto, Assertable<ID> assertable) throws DomainNotFoundException, BadRequestException {
        // Send a DELETE request to the DELETE operation URI with the ID of the entity to be deleted
        // Prepare the request header
        // Expect the status to be equal to the DELETE operation status
        // Expect the body to be of the ID class type
        this.getWebTestClient()
                .delete()
                .uri(String.format("%s/%s?%s.eq=%s", getBaseUri(), Operations.DELETE, savedDto.getIdName(), savedDto.getId()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus()
                .isEqualTo(OperationsStatus.DELETE)
                .expectBody(getIdClass())
                .value(assertable::assertThan);
    }
}