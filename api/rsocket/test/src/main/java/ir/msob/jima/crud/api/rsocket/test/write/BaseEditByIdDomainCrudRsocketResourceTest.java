package ir.msob.jima.crud.api.rsocket.test.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.IdJsonPatchMessage;
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
import ir.msob.jima.crud.test.domain.write.BaseEditByIdDomainCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseEditByIdDomainCrudRsocketResourceTest} interface represents a set of RSocket-specific test methods for editing an entity by its ID.
 * It extends both the {@code BaseEditByIdDomainCrudResourceTest} and {@code ParentDomainCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit an entity by its ID using RSocket API. The entity to be edited and the changes to be made are determined by the DTO and the JsonPatch respectively.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseEditByIdDomainCrudResourceTest
 * @see ParentDomainCrudRsocketResourceTest
 */
public interface BaseEditByIdDomainCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseEditByIdDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RSocket request to edit an entity by its ID and extracts the result from the response.
     * The entity to be edited and the changes to be made are determined by the DTO and the JsonPatch respectively.
     *
     * @param savedDto  The data transfer object (DTO) representing the entity to be edited.
     * @param jsonPatch The JsonPatch representing the changes to be made to the entity.
     */
    @SneakyThrows
    @Override
    default void editByIdRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<DTO> assertable) {
        // Create a new IdJsonPatchMessage
        // Set the ID of the message to the domain ID of the DTO
        // Set the JsonPatch of the message to the provided JsonPatch
        // Send a RSocket request to the EDIT_BY_ID operation URI with the IdJsonPatchMessage as data
        // Retrieve the result as a Mono of the DTO class
        // Convert the Mono to a Future
        // Get the result from the Future
        IdJsonPatchMessage<ID> data = new IdJsonPatchMessage<>();
        data.setId(savedDto.getId());
        data.setJsonPatch(jsonPatch);

        ChannelMessage<USER, IdJsonPatchMessage<ID>> message = ChannelMessage.<USER, IdJsonPatchMessage<ID>>builder()
                .data(data)
                .build();

        getRSocketRequester()
                .route(getUri(Operations.EDIT_BY_ID))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(getDtoClass())
                .doOnSuccess(assertable::assertThan)
                .toFuture()
                .get();
    }
}