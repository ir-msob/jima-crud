package ir.msob.jima.crud.api.kafka.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtosMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.kafka.test.ParentCrudKafkaListenerTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseUpdateManyCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseUpdateManyCrudKafkaListenerTest} interface represents a set of RESTful-specific test methods for updating multiple entities.
 * It extends both the {@code BaseUpdateManyCrudResourceTest} and {@code ParentCrudKafkaListenerTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to update multiple entities using RESTful API. The result of the update operation is a collection of DTOs of the updated entities.
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
 * @see BaseUpdateManyCrudResourceTest
 * @see ParentCrudKafkaListenerTest
 */
public interface BaseUpdateManyCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseUpdateManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudKafkaListenerTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to update multiple entities and extracts the result from the response.
     *
     * @param dtos The collection of data transfer objects (DTOs) representing the entities to be updated.
     */
    @SneakyThrows
    @Override
    default void updateManyRequest(Collection<DTO> dtos, Assertable<Collection<DTO>> assertable) {
        // Send a PUT request to the UPDATE_MANY operation URI
        // Prepare the request header
        // Set the body of the request to the collection of DTOs
        // Expect the status to be equal to the UPDATE_MANY operation status
        // Expect the content type to be JSON
        // Expect the body to be of type Collection
        // Return the response body
        String topic = prepareTopic(Operations.UPDATE_MANY);
        DtosMessage<ID, DTO> data = new DtosMessage<>();
        data.setDtos(dtos);

        ChannelMessage<USER, DtosMessage<ID, DTO>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        channelMessage.setCallback(prepareCallbackTopic());
        channelMessage.setUser(getSampleUser().orElse(null));

        String message = getObjectMapper().writeValueAsString(channelMessage);
        getKafkaTemplate().send(topic, message);

        startListener(channelMessage.getCallback(), s -> assertable.assertThan(cast(s, getDtosReferenceType()).getData().getDtos()));
    }
}