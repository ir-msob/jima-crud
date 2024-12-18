package ir.msob.jima.crud.api.rsocket.test.read;

import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.IdMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.rsocket.test.ParentDomainCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.read.BaseGetByIdDomainCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseGetByIdDomainCrudRsocketResourceTest} interface represents a set of RSocket-specific test methods for retrieving an entity by its ID.
 * It extends both the {@code BaseGetByIdDomainCrudResourceTest} and {@code ParentDomainCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve an entity by its ID using RSocket API. The result of the get operation is the DTO of the retrieved entity.
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
 * @see BaseGetByIdDomainCrudResourceTest
 * @see ParentDomainCrudRsocketResourceTest
 */
public interface BaseGetByIdDomainCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetByIdDomainCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentDomainCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RSocket request to retrieve an entity by its ID and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be retrieved.
     */
    @SneakyThrows
    @Override
    default void getByIdRequest(DTO savedDto, Assertable<DTO> assertable) {
        // Create a new IdMessage
        // Set the ID of the message to the domain ID of the DTO
        // Send a RSocket request to the GET_BY_ID operation URI with the IdMessage as data
        // Retrieve the result as a Mono of the DTO class
        // Convert the Mono to a Future
        // Get the result from the Future
        IdMessage<ID> data = new IdMessage<>();
        data.setId(savedDto.getId());

        ChannelMessage<USER, IdMessage<ID>> message = ChannelMessage.<USER, IdMessage<ID>>builder()
                .data(data)
                .build();

        getRSocketRequester()
                .route(getUri(Operations.GET_BY_ID))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(getDtoClass())
                .doOnSuccess(assertable::assertThan)
                .toFuture()
                .get();
    }
}