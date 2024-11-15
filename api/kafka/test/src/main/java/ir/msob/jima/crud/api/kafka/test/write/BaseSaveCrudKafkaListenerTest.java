package ir.msob.jima.crud.api.kafka.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtoMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.kafka.test.ParentCrudKafkaListenerTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.write.BaseSaveCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseSaveCrudKafkaListenerTest} interface represents a set of RESTful-specific test methods for saving an entity.
 * It extends both the {@code BaseSaveCrudResourceTest} and {@code ParentCrudKafkaListenerTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to save an entity using RESTful API. The result of the save operation is the DTO of the saved entity.
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
 * @see BaseSaveCrudResourceTest
 * @see ParentCrudKafkaListenerTest
 */
public interface BaseSaveCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseSaveCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudKafkaListenerTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to save an entity and extracts the result from the response.
     *
     * @param dto The data transfer object (DTO) representing the entity to be saved.
     */
    @SneakyThrows
    @Override
    default void saveRequest(DTO dto, Assertable<DTO> assertable) {
        // Send a POST request to the SAVE operation URI
        // Prepare the request header
        // Set the body of the request to the DTO
        // Expect the status to be equal to the SAVE operation status
        // Expect the content type to be JSON
        // Expect the body to be of the DTO class type
        // Return the response body
        String topic = prepareTopic(Operations.SAVE);
        DtoMessage<ID, DTO> data = new DtoMessage<>();
        data.setDto(dto);

        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        channelMessage.setCallback(prepareCallbackTopic());
        channelMessage.setUser(getSampleUser());

        String message = getObjectMapper().writeValueAsString(channelMessage);
        getKafkaTemplate().send(topic, message);

        startListener(channelMessage.getCallback(), s -> assertable.assertThan(cast(s, getDtoReferenceType()).getData().getDto()));
    }
}