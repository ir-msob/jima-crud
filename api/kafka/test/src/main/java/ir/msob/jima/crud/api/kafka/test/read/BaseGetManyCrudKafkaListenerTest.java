package ir.msob.jima.crud.api.kafka.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.kafka.test.ParentCrudKafkaListenerTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.read.BaseGetManyCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseGetManyCrudKafkaListenerTest} interface represents a set of RESTful-specific test methods for retrieving multiple entities.
 * It extends both the {@code BaseGetManyCrudResourceTest} and {@code ParentCrudKafkaListenerTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve multiple entities using RESTful API. The result of the get operation is a collection of DTOs of the retrieved entities.
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
 * @see BaseGetManyCrudResourceTest
 * @see ParentCrudKafkaListenerTest
 */
public interface BaseGetManyCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudKafkaListenerTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to retrieve multiple entities and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entities to be retrieved.
     */
    @SneakyThrows
    @Override
    default void getManyRequest(DTO savedDto, Assertable<Collection<DTO>> assertable) {
        // Send a GET request to the GET_MANY operation URI with the ID of the entities to be retrieved
        // Prepare the request header
        // Expect the status to be equal to the GET_MANY operation status
        // Expect the content type to be JSON
        // Expect the body to be of type Collection
        // Return the response body
        String topic = prepareTopic(Operations.GET_MANY);
        CriteriaMessage<ID, C> data = new CriteriaMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId()));

        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        channelMessage.setCallback(prepareCallbackTopic());
        channelMessage.setUser(getSampleUser().orElse(null));

        String message = getObjectMapper().writeValueAsString(channelMessage);
        getKafkaTemplate().send(topic, message);

        startListener(channelMessage.getCallback(), s -> assertable.assertThan(cast(s, getDtosReferenceType()).getData().getDtos()));

    }
}