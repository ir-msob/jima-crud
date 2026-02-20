package ir.msob.jima.crud.api.rsocket.test.write;

import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.DtoMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.rsocket.test.ParentDomainCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseUpdateDomainCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * This interface extends the BaseUpdateChildDomainCrudResourceTest and ParentDomainCrudRsocketResourceTest interfaces.
 * It provides a method for updating a resource via RSocket.
 *
 * @param <ID>   the type of the ID of the domain object, which must be comparable and serializable
 * @param <USER> the type of the user, which extends BaseUser
 * @param <D>    the type of the domain object, which extends BaseDomain
 * @param <DTO>  the type of the DTO object, which extends BaseDto
 * @param <C>    the type of the criteria object, which extends BaseCriteria
 * @param <R>    the type of the repository object, which extends BaseDomainCrudRepository
 * @param <S>    the type of the service object, which extends BaseChildDomainCrudService
 * @param <DP>   the type of the data provider object, which extends BaseChildDomainCrudDataProvider
 */
public interface BaseUpdateDomainCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseUpdateDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * This method sends an update request for a resource via RSocket.
     * It creates a DtoMessage with the provided DTO and sends it to the specified route.
     *
     * @param dto the DTO object that represents the resource to be updated
     */
    @SneakyThrows
    @Override
    default void updateRequest(DTO dto, Assertable<DTO> assertable) {
        DtoMessage<ID, DTO> data = new DtoMessage<>();
        data.setDto(dto);

        ChannelMessage<USER, DtoMessage<ID, DTO>> message = ChannelMessage.<USER, DtoMessage<ID, DTO>>builder()
                .data(data)
                .build();

        getRSocketRequester()
                .route(getUri(Operations.UPDATE))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(getDtoClass())
                .doOnSuccess(assertable::assertThan)
                .toFuture()
                .get();
    }
}