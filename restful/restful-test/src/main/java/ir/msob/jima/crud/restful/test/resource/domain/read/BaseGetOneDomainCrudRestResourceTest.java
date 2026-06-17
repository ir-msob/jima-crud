package ir.msob.jima.crud.restful.test.resource.domain.read;

import ir.msob.jima.crud.core.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.restful.test.resource.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.crud.test.dataprovider.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.resource.domain.read.BaseGetOneDomainCrudResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.test.Assertable;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code BaseGetOneChildDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for retrieving a single entity.
 * It extends both the {@code BaseGetOneChildDomainCrudResourceTest} and {@code BaseEmbeddedDomainCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve a single entity using RESTful API. The result of the get operation is the DTO of the retrieved entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetOneDomainCrudResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseGetOneDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetOneDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to retrieve a single entity and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be retrieved.
     */
    @Override
    default void getOneRequest(DTO savedDto, Assertable<DTO> assertable) {
        HttpHeaders headers = new HttpHeaders();
        prepareHeader(headers);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder
                .fromPath(String.format("%s/%s", getBaseUri(), Operations.GET_ONE))
                .queryParam(savedDto.getIdName() + ".eq", savedDto.getId())
                .toUriString();

        ResponseEntity<DTO> response = this.getTestRestTemplate()
                .exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        this.getDataProvider().getService().getDtoClass()
                );

        assertThat(response.getStatusCode().value())
                .isEqualTo(OperationsStatus.GET_ONE);

        assertThat(response.getHeaders().getContentType())
                .isEqualTo(MediaType.APPLICATION_JSON);

        assertable.assertThan(response.getBody());

    }
}