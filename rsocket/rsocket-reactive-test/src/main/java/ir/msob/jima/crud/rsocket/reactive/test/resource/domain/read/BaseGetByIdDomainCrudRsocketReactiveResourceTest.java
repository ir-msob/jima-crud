package ir.msob.jima.crud.rsocket.reactive.test.resource.domain.read;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseGetByIdDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.rsocket.reactive.test.resource.domain.ParentDomainCrudRsocketResourceTest;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.message.IdMessage;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseGetByIdDomainCrudRsocketReactiveResourceTest} interface represents a set of RSocket-specific test methods for retrieving an entity by its ID.
 * It extends both the {@code BaseGetByIdChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve an entity by its ID using RSocket API. The result of the get operation is the DTO of the retrieved entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetByIdDomainCrudReactiveResourceTest
 * @see ParentDomainCrudRsocketResourceTest
 */
public interface BaseGetByIdDomainCrudRsocketReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetByIdDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
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