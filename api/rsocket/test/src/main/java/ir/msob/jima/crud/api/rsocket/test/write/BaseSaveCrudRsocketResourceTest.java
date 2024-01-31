package ir.msob.jima.crud.api.rsocket.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtoMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.write.BaseSaveCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * This interface extends the BaseSaveCrudResourceTest and ParentCrudRsocketResourceTest interfaces.
 * It provides a method for saving resources via RSocket.
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
public interface BaseSaveCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseSaveCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * This method sends a save request for a resource via RSocket.
     * It creates a DtoMessage with the provided DTO and sends it to the specified route.
     *
     * @param dto the DTO object that represents the resource to be saved
     * @return the saved DTO object
     * @throws Exception if an error occurs during the request
     */
    @SneakyThrows
    @Override
    default DTO saveRequest(DTO dto) {
        DtoMessage<ID, DTO> data = new DtoMessage<>();
        data.setDto(dto);

        ChannelMessage<ID, USER, DtoMessage<ID, DTO>> message = new ChannelMessage<>();
        message.setData(data);

        return getRSocketRequester()
                .route(getUri(Operations.SAVE))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(getDtoClass())
                .toFuture()
                .get();
    }
}