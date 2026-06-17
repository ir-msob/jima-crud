package ir.msob.jima.crud.restful.reactive.test.resource.domain.write;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseSaveManyDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseSaveManyChildDomainCrudRestReactiveResourceTest} interface represents a set of RESTful-specific test methods for saving multiple entities.
 * It extends both the {@code BaseSaveManyChildDomainCrudReactiveResourceTest} and {@code BaseEmbeddedDomainCrudRestReactiveResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to save multiple entities using RESTful API. The result of the save operation is a collection of DTOs of the saved entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseSaveManyDomainCrudReactiveResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseSaveManyDomainCrudRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseSaveManyDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to save multiple entities and extracts the result from the response.
     *
     * @param dtos The collection of data transfer objects (DTOs) representing the entities to be saved.
     */
    @Override
    default void saveManyRequest(Collection<DTO> dtos, Assertable<Collection<DTO>> assertable) {
        // Send a POST request to the SAVE_MANY operation URI
        // Prepare the request header
        // Set the body of the request to the collection of DTOs
        // Expect the status to be equal to the SAVE_MANY operation status
        // Expect the content type to be JSON
        // Expect the body to be of type Collection
        this.getWebTestClient()
                .post()
                .uri(String.format("%s/%s", getBaseUri(), Operations.SAVE_MANY))
                .headers(this::prepareHeader)
                .bodyValue(dtos)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.SAVE_MANY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList(getDtoClass())
                .value(assertable::assertThan);
    }
}