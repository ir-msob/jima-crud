package ir.msob.jima.crud.api.rsocket.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtosMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseSaveManyCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface extends the BaseSaveManyCrudResourceTest and ParentCrudRsocketResourceTest interfaces.
 * It provides a method for saving multiple resources via RSocket.
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
public interface BaseSaveManyCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseSaveManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * This method sends a save request for multiple resources via RSocket.
     * It creates a DtosMessage with the provided DTOs and sends it to the specified route.
     *
     * @param dtos the collection of DTO objects that represent the resources to be saved
     * @return a collection of saved DTO objects
     * @throws Exception if an error occurs during the request
     */
    @SneakyThrows
    @Override
    default Collection<DTO> saveManyRequest(Collection<DTO> dtos) {
        DtosMessage<ID, DTO> data = new DtosMessage<>();
        data.setDtos(dtos);

        ChannelMessage<ID, USER, DtosMessage<ID, DTO>> message = new ChannelMessage<>();
        message.setData(data);

        return getRSocketRequester()
                .route(getUri(Operations.SAVE_MANY))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(Collection.class)
                .toFuture()
                .get();
    }
}