package ir.msob.jima.crud.kafka.reactive.test.resource.domain.write;

import ir.msob.jima.crud.kafka.reactive.test.resource.domain.ParentDomainCrudKafkaListenerTest;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseDeleteManyDomainCrudReactiveResourceTest;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.message.CriteriaMessage;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.shared.ModelType;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code BaseDeleteManyDomainCrudReactiveKafkaListenerTest} interface represents a set of RESTful-specific test methods for deleting multiple entities.
 * It extends both the {@code BaseDeleteManyChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudKafkaListenerTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to delete multiple entities using RESTful API. The result of the delete operation is a set of IDs of the deleted entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseDeleteManyDomainCrudReactiveResourceTest
 * @see ParentDomainCrudKafkaListenerTest
 */
public interface BaseDeleteManyDomainCrudReactiveKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseDeleteManyDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudKafkaListenerTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to delete multiple entities and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entities to be deleted.
     * @throws DomainNotFoundException If the entity is not found.
     * @throws BadRequestException     If the request is not valid.
     */
    @SneakyThrows
    @Override
    default void deleteManyRequest(DTO savedDto, Assertable<Set<ID>> assertable) throws DomainNotFoundException, BadRequestException {
        // Send a DELETE request to the DELETE_MANY operation URI with the ID of the entities to be deleted
        // Prepare the request header
        // Expect the status to be equal to the DELETE_MANY operation status
        // Expect the body to be of type Set
        // Return the response body
        String topic = prepareTopic(Operations.DELETE_MANY);
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
        getKafkaTemplate().executeInTransaction(operations -> operations.send(topic, message));

        startListener(channelMessage.getCallbacks().getFirst().getChannel(), s -> assertable.assertThan(new HashSet<>(cast(s, getChannelMessageIdsReferenceType()).getData().getIds())));

    }
}