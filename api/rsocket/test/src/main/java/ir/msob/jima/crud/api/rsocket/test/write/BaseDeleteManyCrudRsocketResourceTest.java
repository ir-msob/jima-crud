package ir.msob.jima.crud.api.rsocket.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseDeleteManyCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Set;

/**
 * The {@code BaseDeleteManyCrudRsocketResourceTest} interface represents a set of RSocket-specific test methods for deleting multiple entities.
 * It extends both the {@code BaseDeleteManyCrudResourceTest} and {@code ParentCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to delete multiple entities using RSocket API. The entities to be deleted are determined by the criteria provided in the DTO.
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
 * @see BaseDeleteManyCrudResourceTest
 * @see ParentCrudRsocketResourceTest
 */
public interface BaseDeleteManyCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseDeleteManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RSocket request to delete multiple entities and extracts the result from the response.
     * The entities to be deleted are determined by the criteria provided in the DTO.
     *
     * @param savedDto The data transfer object (DTO) representing the entities to be deleted.
     * @return The IDs of the deleted entities.
     * @throws DomainNotFoundException if the domain is not found.
     * @throws BadRequestException     if the request is bad.
     */
    @SneakyThrows
    @Override
    default Set<ID> deleteManyRequest(DTO savedDto) throws DomainNotFoundException, BadRequestException {
        // Create a new CriteriaMessage
        // Set the criteria of the message to the ID criteria of the DTO
        // Send a RSocket request to the DELETE_MANY operation URI with the CriteriaMessage as data
        // Retrieve the result as a Mono of type Set
        // Convert the Mono to a Future
        // Get the result from the Future
        CriteriaMessage<ID, C> data = new CriteriaMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId()));

        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> message = new ChannelMessage<>();
        message.setData(data);

        return getRSocketRequester()
                .route(getUri(Operations.DELETE_MANY))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(Set.class)
                .toFuture()
                .get();
    }
}