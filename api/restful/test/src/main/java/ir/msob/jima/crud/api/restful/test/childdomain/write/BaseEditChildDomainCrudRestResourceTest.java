package ir.msob.jima.crud.api.restful.test.childdomain.write;

import com.github.fge.jsonpatch.JsonPatch;
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
import ir.msob.jima.crud.test.childdomain.write.BaseEditChildDomainCrudResourceTest;
import ir.msob.jima.crud.test.domain.write.BaseEditDomainCrudResourceTest;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;

import java.io.Serializable;

/**
 * The {@code BaseEditChildDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for editing an entity.
 * It extends both the {@code BaseEditChildDomainCrudResourceTest} and {@code BaseEmbeddedDomainCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit an entity using RESTful API. The result of the edit operation is the DTO of the edited entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseEditDomainCrudResourceTest
 * @see ParentChildDomainCrudRestResourceTest
 */
public interface BaseEditChildDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseEditChildDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentChildDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to edit an entity and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the entity to be edited.
     * @param jsonPatch The JSON Patch representing the changes to be applied to the entity.
     */
    @SneakyThrows
    @Override
    default void editRequest(ID parentId,DTO savedDto, JsonPatch jsonPatch, Assertable<DTO> assertable) {
        // Send a PATCH request to the EDIT operation URI with the ID of the entity to be edited
        // Prepare the request header
        // Set the body of the request to the JSON Patch
        // Expect the status to be equal to the EDIT operation status
        // Expect the content type to be JSON
        // Expect the body to be of the DTO class type
        this.getWebTestClient()
                .patch()
                .uri(String.format("%s/%s/%s/%s?%s.eq=%s", getDomainUri(), parentId, getChildDomainUri(), Operations.EDIT, savedDto.getIdName(), savedDto.getId()))
                .headers(this::prepareHeader)
                .contentType(MediaType.valueOf("application/json-patch+json"))
                .bodyValue(getObjectMapper().writer().writeValueAsString(jsonPatch))
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.EDIT)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(this.getDataProvider().getService().getDtoClass())
                .value(assertable::assertThan);
    }
}