package ir.msob.jima.crud.api.rsocket.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseGetOneCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseGetOneCrudRsocketResourceTest} interface represents a set of RSocket-specific test methods for retrieving a single entity.
 * It extends both the {@code BaseGetOneCrudResourceTest} and {@code ParentCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve a single entity using RSocket API. The entity to be retrieved is determined by the criteria provided in the DTO.
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
 * @see BaseGetOneCrudResourceTest
 * @see ParentCrudRsocketResourceTest
 */
public interface BaseGetOneCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetOneCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RSocket request to retrieve a single entity and extracts the result from the response.
     * The entity to be retrieved is determined by the criteria provided in the DTO.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be retrieved.
     * @return The data transfer object (DTO) representing the retrieved entity.
     */
    @SneakyThrows
    @Override
    default DTO getOneRequest(DTO savedDto) {
        // Create a new CriteriaMessage
        // Set the criteria of the message to the ID criteria of the DTO
        // Send a RSocket request to the GET_ONE operation URI with the CriteriaMessage as data
        // Retrieve the result as a Mono of the DTO class
        // Convert the Mono to a Future
        // Get the result from the Future
        CriteriaMessage<ID, C> data = new CriteriaMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId()));

        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> message = new ChannelMessage<>();
        message.setData(data);

        return getRSocketRequester()
                .route(getUri(Operations.GET_ONE))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(getDtoClass())
                .toFuture()
                .get();
    }
}