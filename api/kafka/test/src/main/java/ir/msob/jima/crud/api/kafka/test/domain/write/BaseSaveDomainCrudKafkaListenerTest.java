package ir.msob.jima.crud.api.kafka.test.domain.write;

import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.DtoMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.ModelType;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.kafka.test.domain.ParentDomainCrudKafkaListenerTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseSaveDomainCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseSaveDomainCrudKafkaListenerTest} interface represents a set of RESTful-specific test methods for saving an entity.
 * It extends both the {@code BaseSaveDomainCrudResourceTest} and {@code ParentDomainCrudKafkaListenerTest} interfaces, providing RESTful-specific testing capabilities.
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
 * @see BaseSaveDomainCrudResourceTest
 * @see ParentDomainCrudKafkaListenerTest
 */
public interface BaseSaveDomainCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseSaveDomainCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentDomainCrudKafkaListenerTest<ID, USER, D, DTO, C> {

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

        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = ChannelMessage.<USER, DtoMessage<ID, DTO>>builder()
                .user(getSampleUser())
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder()
                        .channel(prepareCallbackTopic())
                        .build())
                .build();

        String message = getObjectMapper().writeValueAsString(channelMessage);
        getKafkaTemplate().send(topic, message);

        startListener(channelMessage.getCallbacks().getFirst().getChannel(), s -> assertable.assertThan(cast(s, getChannelMessageDtoReferenceType()).getData().getDto()));
    }
}