package ir.msob.jima.crud.api.kafka.test.domain.write;

import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.dto.ModelType;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.kafka.test.domain.ParentDomainCrudKafkaListenerTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseDeleteDomainCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseDeleteDomainCrudKafkaListenerTest} interface represents a set of RESTful-specific test methods for deleting an entity.
 * It extends both the {@code BaseDeleteDomainCrudResourceTest} and {@code ParentDomainCrudKafkaListenerTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to delete an entity using RESTful API. The result of the delete operation is the ID of the deleted entity.
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
 * @see BaseDeleteDomainCrudResourceTest
 * @see ParentDomainCrudKafkaListenerTest
 */
public interface BaseDeleteDomainCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseDeleteDomainCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentDomainCrudKafkaListenerTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to delete an entity and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be deleted.
     * @throws DomainNotFoundException If the entity is not found.
     * @throws BadRequestException     If the request is not valid.
     */
    @SneakyThrows
    @Override
    default void deleteRequest(DTO savedDto, Assertable<ID> assertable) throws DomainNotFoundException, BadRequestException {
        // Send a DELETE request to the DELETE operation URI with the ID of the entity to be deleted
        // Prepare the request header
        // Expect the status to be equal to the DELETE operation status
        // Expect the body to be of the ID class type
        // Return the response body
        String topic = prepareTopic(Operations.DELETE);
        CriteriaMessage<ID, C> data = new CriteriaMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId()));

        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = ChannelMessage.<USER, CriteriaMessage<ID, C>>builder()
                .user(getSampleUser())
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder()
                        .channel(prepareCallbackTopic())
                        .build())
                .build();

        String message = getObjectMapper().writeValueAsString(channelMessage);
        getKafkaTemplate().send(topic, message);

        startListener(channelMessage.getCallbacks().getFirst().getChannel(), s -> assertable.assertThan(cast(s, getIdReferenceType()).getData().getId()));
    }
}