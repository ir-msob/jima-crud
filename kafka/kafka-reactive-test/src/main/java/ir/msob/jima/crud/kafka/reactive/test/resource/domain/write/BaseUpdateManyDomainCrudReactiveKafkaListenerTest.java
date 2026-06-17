package ir.msob.jima.crud.kafka.reactive.test.resource.domain.write;

import ir.msob.jima.crud.kafka.reactive.test.resource.domain.ParentDomainCrudKafkaListenerTest;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseUpdateManyDomainCrudReactiveResourceTest;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.message.DtosMessage;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.shared.ModelType;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseUpdateManyDomainCrudReactiveKafkaListenerTest} interface represents a set of RESTful-specific test methods for updating multiple entities.
 * It extends both the {@code BaseUpdateManyChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudKafkaListenerTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to update multiple entities using RESTful API. The result of the update operation is a collection of DTOs of the updated entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseUpdateManyDomainCrudReactiveResourceTest
 * @see ParentDomainCrudKafkaListenerTest
 */
public interface BaseUpdateManyDomainCrudReactiveKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseUpdateManyDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudKafkaListenerTest<ID, USER, D, DTO, C> {

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


        ChannelMessage<USER, DtosMessage<ID, DTO>> channelMessage = ChannelMessage.<USER, DtosMessage<ID, DTO>>builder()
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder()
                        .channel(prepareCallbackTopic())
                        .build())
                .user(getSampleUser())
                .build();

        String message = getObjectMapper().writeValueAsString(channelMessage);
        getKafkaTemplate().executeInTransaction(operations -> operations.send(topic, message));

        startListener(channelMessage.getCallbacks().getFirst().getChannel(), s -> assertable.assertThan(cast(s, getChannelMessageDtosReferenceType()).getData().getDtos()));
    }
}