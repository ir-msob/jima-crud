package ir.msob.jima.crud.api.rsocket.test.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.IdJsonPatchMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseEditByIdCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseEditByIdCrudRsocketResourceTest} interface represents a set of RSocket-specific test methods for editing an entity by its ID.
 * It extends both the {@code BaseEditByIdCrudResourceTest} and {@code ParentCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit an entity by its ID using RSocket API. The entity to be edited and the changes to be made are determined by the DTO and the JsonPatch respectively.
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
 * @see BaseEditByIdCrudResourceTest
 * @see ParentCrudRsocketResourceTest
 */
public interface BaseEditByIdCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditByIdCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RSocket request to edit an entity by its ID and extracts the result from the response.
     * The entity to be edited and the changes to be made are determined by the DTO and the JsonPatch respectively.
     *
     * @param savedDto  The data transfer object (DTO) representing the entity to be edited.
     * @param jsonPatch The JsonPatch representing the changes to be made to the entity.
     * @return The data transfer object (DTO) representing the edited entity.
     */
    @SneakyThrows
    @Override
    default DTO editByIdRequest(DTO savedDto, JsonPatch jsonPatch) {
        // Create a new IdJsonPatchMessage
        // Set the ID of the message to the domain ID of the DTO
        // Set the JsonPatch of the message to the provided JsonPatch
        // Send a RSocket request to the EDIT_BY_ID operation URI with the IdJsonPatchMessage as data
        // Retrieve the result as a Mono of the DTO class
        // Convert the Mono to a Future
        // Get the result from the Future
        IdJsonPatchMessage<ID> data = new IdJsonPatchMessage<>();
        data.setId(savedDto.getDomainId());
        data.setJsonPatch(jsonPatch);

        ChannelMessage<ID, USER, IdJsonPatchMessage<ID>> message = new ChannelMessage<>();
        message.setData(data);

        return getRSocketRequester()
                .route(getUri(Operations.EDIT_BY_ID))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(getDtoClass())
                .toFuture()
                .get();
    }
}