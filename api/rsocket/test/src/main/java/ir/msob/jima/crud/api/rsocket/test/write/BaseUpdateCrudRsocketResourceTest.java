package ir.msob.jima.crud.api.rsocket.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtoMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseUpdateCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * This interface extends the BaseUpdateCrudResourceTest and ParentCrudRsocketResourceTest interfaces.
 * It provides a method for updating a resource via RSocket.
 *
 * @param <ID>   the type of the ID of the domain object, which must be comparable and serializable
 * @param <USER> the type of the user, which extends BaseUser
 * @param <D>    the type of the domain object, which extends BaseDomain
 * @param <DTO>  the type of the DTO object, which extends BaseDto
 * @param <C>    the type of the criteria object, which extends BaseCriteria
 * @param <Q>    the type of the query object, which extends BaseQuery
 * @param <R>    the type of the repository object, which extends BaseCrudRepository
 * @param <S>    the type of the service object, which extends BaseCrudService
 * @param <DP>   the type of the data provider object, which extends BaseCrudDataProvider
 */
public interface BaseUpdateCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseUpdateCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {

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

        ChannelMessage<USER, DtoMessage<ID, DTO>> message = new ChannelMessage<>();
        message.setData(data);

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