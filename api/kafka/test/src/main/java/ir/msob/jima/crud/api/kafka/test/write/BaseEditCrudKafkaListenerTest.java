package ir.msob.jima.crud.api.kafka.test.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.JsonPatchMessage;
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
import ir.msob.jima.crud.test.write.BaseEditCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseEditCrudKafkaListenerTest} interface represents a set of RESTful-specific test methods for editing an entity.
 * It extends both the {@code BaseEditCrudResourceTest} and {@code ParentCrudKafkaListenerTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit an entity using RESTful API. The result of the edit operation is the DTO of the edited entity.
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
 * @see BaseEditCrudResourceTest
 * @see ParentCrudKafkaListenerTest
 */
public interface BaseEditCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudKafkaListenerTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to edit an entity and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the entity to be edited.
     * @param jsonPatch The JSON Patch representing the changes to be applied to the entity.
     */
    @SneakyThrows
    @Override
    default void editRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<DTO> assertable) {
        // Send a PATCH request to the EDIT operation URI with the ID of the entity to be edited
        // Prepare the request header
        // Set the body of the request to the JSON Patch
        // Expect the status to be equal to the EDIT operation status
        // Expect the content type to be JSON
        // Expect the body to be of the DTO class type
        // Return the response body
        String topic = prepareTopic(Operations.EDIT);
        JsonPatchMessage<ID, C> data = new JsonPatchMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId()));
        data.setJsonPatch(jsonPatch);


        ChannelMessage<USER, JsonPatchMessage<ID, C>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        channelMessage.setCallback(prepareCallbackTopic());
        channelMessage.setUser(getSampleUser());

        String message = getObjectMapper().writeValueAsString(channelMessage);
        getKafkaTemplate().send(topic, message);

        startListener(channelMessage.getCallback(), s -> assertable.assertThan(cast(s, getDtoReferenceType()).getData().getDto()));
    }
}