package ir.msob.jima.crud.restful.test.resource.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.crud.core.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.restful.test.resource.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.crud.test.dataprovider.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.resource.domain.write.BaseEditManyDomainCrudResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code BaseEditManyChildDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for editing multiple entities.
 * It extends both the {@code BaseEditManyChildDomainCrudResourceTest} and {@code BaseEmbeddedDomainCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
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
 * @see BaseEditManyDomainCrudResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseEditManyDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseEditManyDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to edit multiple entities and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the entities to be edited.
     * @param jsonPatch The JSON Patch representing the changes to be applied to the entities.
     */
    @SneakyThrows
    @Override
    default void editManyRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<Collection<DTO>> assertable) {
        HttpHeaders headers = new HttpHeaders();
        prepareHeader(headers);
        headers.setContentType(MediaType.valueOf("application/json-patch+json"));

        HttpEntity<String> requestEntity = new HttpEntity<>(
                getObjectMapper().writer().writeValueAsString(jsonPatch),
                headers
        );

        String url = UriComponentsBuilder
                .fromPath(String.format("%s/%s", getBaseUri(), Operations.EDIT_MANY))
                .queryParam(savedDto.getIdName() + ".eq", savedDto.getId())
                .toUriString();

        ResponseEntity<Collection<DTO>> response = this.getTestRestTemplate()
                .exchange(
                        url,
                        HttpMethod.PATCH,
                        requestEntity,
                        cast(getDtosReferenceType())
                );

        assertThat(response.getStatusCode().value())
                .isEqualTo(OperationsStatus.EDIT_MANY);

        assertable.assertThan(response.getBody());
    }
}