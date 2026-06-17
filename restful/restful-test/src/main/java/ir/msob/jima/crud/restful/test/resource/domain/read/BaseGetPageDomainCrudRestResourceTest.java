package ir.msob.jima.crud.restful.test.resource.domain.read;

import ir.msob.jima.crud.core.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.restful.test.resource.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.crud.test.dataprovider.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.resource.domain.read.BaseGetPageDomainCrudResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.shared.PageDto;
import ir.msob.jima.platform.test.Assertable;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code BaseGetPageChildDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for retrieving a page of entities.
 * It extends both the {@code BaseGetPageChildDomainCrudResourceTest} and {@code BaseEmbeddedDomainCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve a page of entities using RESTful API. The result of the get operation is a page of DTOs of the retrieved entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetPageDomainCrudResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseGetPageDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetPageDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to retrieve a page of entities and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entities to be retrieved.
     * @throws DomainNotFoundException If the entity is not found.
     * @throws BadRequestException     If the request is not valid.
     */
    @Override
    default void getPageRequest(DTO savedDto, Assertable<PageDto<DTO>> assertable) throws DomainNotFoundException, BadRequestException {
        HttpHeaders headers = new HttpHeaders();
        prepareHeader(headers);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder
                .fromPath(getBaseUri())
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam(savedDto.getIdName() + ".eq", savedDto.getId())
                .toUriString();

        ResponseEntity<PageDto<DTO>> response = this.getTestRestTemplate()
                .exchange(
                        url,
                        HttpMethod.GET,
                        requestEntity,
                        cast(getPageDtoReferenceType())
                );

        assertThat(response.getStatusCode().value())
                .isEqualTo(OperationsStatus.GET_PAGE);

        assertThat(response.getHeaders().getContentType())
                .isEqualTo(MediaType.APPLICATION_JSON);

        assertable.assertThan(response.getBody());
    }
}