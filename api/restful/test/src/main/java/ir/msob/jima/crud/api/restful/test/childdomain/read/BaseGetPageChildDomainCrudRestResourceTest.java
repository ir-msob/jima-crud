package ir.msob.jima.crud.api.restful.test.childdomain.read;

import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.PageDto;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.restful.test.childdomain.ParentChildDomainCrudRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.childdomain.BaseChildDomainCrudDataProvider;
import ir.msob.jima.crud.test.childdomain.read.BaseGetPageChildDomainCrudResourceTest;
import ir.msob.jima.crud.test.domain.read.BaseGetPageDomainCrudResourceTest;
import org.springframework.http.MediaType;

import java.io.Serializable;

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
 * @see ParentChildDomainCrudRestResourceTest
 */
public interface BaseGetPageChildDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetPageChildDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentChildDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to retrieve a page of entities and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entities to be retrieved.
     * @throws DomainNotFoundException If the entity is not found.
     * @throws BadRequestException     If the request is not valid.
     */
    @Override
    default void getPageRequest(ID parentId,DTO savedDto, Assertable<PageDto<DTO>> assertable) throws DomainNotFoundException, BadRequestException {
        // Send a GET request to the GET_PAGE operation URI with the ID of the entities to be retrieved
        // Prepare the request header
        // Expect the status to be equal to the GET_PAGE operation status
        // Expect the content type to be JSON
        // Expect the body to be of type Page
        this.getWebTestClient()
                .get()
                .uri(String.format("%s/%s/%s?page=0&size=10&%s.eq=%s", getDomainUri(), parentId, getChildDomainUri(), savedDto.getIdName(), savedDto.getId()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.GET_PAGE)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(cast(getPageDtoReferenceType()))
                .value(assertable::assertThan);
    }
}