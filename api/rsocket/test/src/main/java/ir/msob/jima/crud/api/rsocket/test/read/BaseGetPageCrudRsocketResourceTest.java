package ir.msob.jima.crud.api.rsocket.test.read;

import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.PageableMessage;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseGetPageCrudResourceTest;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;

/**
 * The {@code BaseGetPageCrudRsocketResourceTest} interface represents a set of RSocket-specific test methods for retrieving a page of entities.
 * It extends both the {@code BaseGetPageCrudResourceTest} and {@code ParentCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve a page of entities using RSocket API. The entities to be retrieved are determined by the criteria provided in the DTO.
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
 * @see BaseGetPageCrudResourceTest
 * @see ParentCrudRsocketResourceTest
 */
public interface BaseGetPageCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetPageCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RSocket request to retrieve a page of entities and extracts the result from the response.
     * The entities to be retrieved are determined by the criteria provided in the DTO.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be retrieved.
     * @throws DomainNotFoundException if the domain is not found.
     * @throws BadRequestException     if the request is bad.
     */
    @SneakyThrows
    @Override
    default void getPageRequest(DTO savedDto, Assertable<Page<DTO>> assertable) throws DomainNotFoundException, BadRequestException {
        // Create a new PageableMessage
        // Set the criteria of the message to the ID criteria of the DTO
        // Set the pageable of the message to a new PageRequest
        // Send a RSocket request to the GET_PAGE operation URI with the PageableMessage as data
        // Retrieve the result as a Mono of type Page
        // Convert the Mono to a Future
        // Get the result from the Future
        PageableMessage<ID, C> data = new PageableMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId()));
        data.setPageable(PageRequest.of(0, 10));

        ChannelMessage<USER, PageableMessage<ID, C>> message = ChannelMessage.<USER, PageableMessage<ID, C>>builder()
                .data(data)
                .build();

        getRSocketRequester()
                .route(getUri(Operations.GET_PAGE))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(Page.class)
                .doOnSuccess(assertable::assertThan)
                .toFuture()
                .get();
    }
}