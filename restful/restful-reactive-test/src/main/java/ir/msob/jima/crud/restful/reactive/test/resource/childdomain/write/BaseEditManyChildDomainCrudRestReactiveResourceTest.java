package ir.msob.jima.crud.restful.reactive.test.resource.childdomain.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.crud.reactive.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.reactive.test.dataprovider.childdomain.BaseChildDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.childdomain.write.BaseEditManyChildDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseEditManyDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.ParentChildDomainCrudRestResourceTest;
import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseEditManyChildDomainCrudRestReactiveResourceTest} interface represents a set of RESTful-specific test methods for editing multiple entities.
 * It extends both the {@code BaseEditManyChildDomainCrudReactiveResourceTest} and {@code BaseEmbeddedDomainCrudRestReactiveResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit multiple entities using RESTful API. The result of the edit operation is a collection of DTOs of the edited entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseEditManyDomainCrudReactiveResourceTest
 * @see ParentChildDomainCrudRestResourceTest
 */
public interface BaseEditManyChildDomainCrudRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseEditManyChildDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentChildDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to edit multiple entities and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the entities to be edited.
     * @param jsonPatch The JSON Patch representing the changes to be applied to the entities.
     */
    @SneakyThrows
    @Override
    default void editManyRequest(ID parentId, DTO savedDto, JsonPatch jsonPatch, Assertable<Collection<DTO>> assertable) {
        // Send a PATCH request to the EDIT_MANY operation URI with the ID of the entities to be edited
        // Prepare the request header
        // Set the body of the request to the JSON Patch
        // Expect the status to be equal to the EDIT_MANY operation status
        // Expect the content type to be JSON
        // Expect the body to be of type Collection
        this.getWebTestClient()
                .patch()
                .uri(String.format("%s/%s/%s/%s?%s.eq=%s", getDomainUri(), parentId, getChildDomainUri(), Operations.EDIT_MANY, savedDto.getIdName(), savedDto.getId()))
                .headers(this::prepareHeader)
                .contentType(MediaType.valueOf("application/json-patch+json"))
                .bodyValue(getObjectMapper().writer().writeValueAsString(jsonPatch))
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.EDIT_MANY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList(getDtoClass())
                .value(assertable::assertThan);
    }
}