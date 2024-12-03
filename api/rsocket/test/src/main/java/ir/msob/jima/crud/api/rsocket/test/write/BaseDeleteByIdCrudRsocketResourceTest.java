package ir.msob.jima.crud.api.rsocket.test.write;

import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.IdMessage;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseDeleteByIdCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseDeleteByIdCrudRsocketResourceTest} interface represents a set of RSocket-specific test methods for deleting an entity by its ID.
 * It extends both the {@code BaseDeleteByIdCrudResourceTest} and {@code ParentCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to delete an entity by its ID using RSocket API.
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
 * @see BaseDeleteByIdCrudResourceTest
 * @see ParentCrudRsocketResourceTest
 */
public interface BaseDeleteByIdCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseDeleteByIdCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RSocket request to delete an entity by its ID and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be deleted.
     * @throws DomainNotFoundException if the domain is not found.
     * @throws BadRequestException     if the request is bad.
     */
    @SneakyThrows
    @Override
    default void deleteByIdRequest(DTO savedDto, Assertable<ID> assertable) throws DomainNotFoundException, BadRequestException {
        // Create a new IdMessage
        // Set the ID of the message to the domain ID of the DTO
        // Send a RSocket request to the DELETE_BY_ID operation URI with the IdMessage as data
        // Retrieve the result as a Mono of the ID class
        // Convert the Mono to a Future
        // Get the result from the Future
        IdMessage<ID> data = new IdMessage<>();
        data.setId(savedDto.getId());

        ChannelMessage<USER, IdMessage<ID>> message = ChannelMessage.<USER, IdMessage<ID>>builder()
                .data(data)
                .build();

        getRSocketRequester()
                .route(getUri(Operations.DELETE_BY_ID))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(getIdClass())
                .doOnSuccess(assertable::assertThan)
                .toFuture()
                .get();
    }
}