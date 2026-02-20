package ir.msob.jima.crud.api.restful.test.childdomain.write;

import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.restful.test.childdomain.ParentChildDomainCrudRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.childdomain.BaseChildDomainCrudDataProvider;
import ir.msob.jima.crud.test.childdomain.write.BaseUpdateChildDomainCrudResourceTest;
import ir.msob.jima.crud.test.domain.write.BaseUpdateDomainCrudResourceTest;
import org.springframework.http.MediaType;

import java.io.Serializable;

/**
 * The {@code BaseUpdateChildDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for updating an entity.
 * It extends both the {@code BaseUpdateChildDomainCrudResourceTest} and {@code BaseEmbeddedDomainCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to update an entity using RESTful API. The result of the update operation is the DTO of the updated entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseUpdateDomainCrudResourceTest
 * @see ParentChildDomainCrudRestResourceTest
 */
public interface BaseUpdateChildDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseUpdateChildDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentChildDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to update an entity and extracts the result from the response.
     *
     * @param dto The data transfer object (DTO) representing the entity to be updated.
     */
    @Override
    default void updateRequest(ID parentId,DTO dto, Assertable<DTO> assertable) {
        // Send a PUT request to the UPDATE operation URI
        // Prepare the request header
        // Set the body of the request to the DTO
        // Expect the status to be equal to the UPDATE operation status
        // Expect the content type to be JSON
        // Expect the body to be of the DTO class type
        this.getWebTestClient()
                .put()
                .uri(String.format("%s/%s/%s/%s", getDomainUri(), parentId, getChildDomainUri(), Operations.UPDATE))
                .headers(this::prepareHeader)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.UPDATE)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(this.getDataProvider().getService().getDtoClass())
                .value(assertable::assertThan);
    }
}