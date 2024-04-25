package ir.msob.jima.crud.api.kafka.service;

import ir.msob.jima.core.api.kafka.commons.BaseKafkaListener;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.BaseChannelTypeReference;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.*;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.crud.api.kafka.client.Constants;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.kafka.listener.ContainerProperties;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * Interface for a listener that handles CRUD operations.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <Q>    The type of the Query, which must extend BaseQuery.
 * @param <R>    The type of the Repository, which must extend BaseCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseCrudService.
 */
public interface ParentCrudListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>>
        extends BaseKafkaListener<ID, USER>,
        BaseChannelTypeReference<ID, USER, DTO, C> {

    /**
     * Creates container properties for the Kafka listener.
     *
     * @param operation The operation for which the container properties are being created.
     * @return The created container properties.
     */
    default ContainerProperties createContainerProperties(String operation) {
        return createKafkaContainerProperties(Constants.getChannel(getDtoClass(), operation));
    }

    /**
     * Starts the Kafka listener container.
     *
     * @param containerProperties The container properties for the Kafka listener.
     * @param operation           The operation for which the container is being started.
     */
    default void startContainer(ContainerProperties containerProperties, String operation) {
        startKafkaContainer(containerProperties, Constants.getChannel(getDtoClass(), operation));
    }

    /**
     * Returns the service that handles the CRUD operations.
     *
     * @return The service.
     */
    S getService();

    /**
     * Sends a callback with a collection of DTOs.
     *
     * @param message The original message.
     * @param dtos    The DTOs to send in the callback.
     * @param status  The status of the operation.
     * @param user    The user who initiated the operation.
     */
    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackDtos(ChannelMessage<USER, DATA> message, Collection<DTO> dtos, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            DtosMessage<ID, DTO> data = new DtosMessage<>();
            data.setDtos(dtos);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    /**
     * Sends a callback with a single DTO.
     *
     * @param message The original message.
     * @param dto     The DTO to send in the callback.
     * @param status  The status of the operation.
     * @param user    The user who initiated the operation.
     */
    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackDto(ChannelMessage<USER, DATA> message, DTO dto, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            DtoMessage<ID, DTO> data = new DtoMessage<>();
            data.setDto(dto);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    /**
     * Sends a callback with a collection of IDs.
     *
     * @param message The original message.
     * @param ids     The IDs to send in the callback.
     * @param status  The status of the operation.
     * @param user    The user who initiated the operation.
     */
    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackIds(ChannelMessage<USER, DATA> message, Collection<ID> ids, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            IdsMessage<ID> data = new IdsMessage<>();
            data.setIds(ids);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    /**
     * Sends a callback with a single ID.
     *
     * @param message The original message.
     * @param id      The ID to send in the callback.
     * @param status  The status of the operation.
     * @param user    The user who initiated the operation.
     */
    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackId(ChannelMessage<USER, DATA> message, ID id, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            IdMessage<ID> data = new IdMessage<>();
            data.setId(id);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    /**
     * Sends a callback with the count of all entities.
     *
     * @param message The original message.
     * @param count   The count of all entities.
     * @param status  The status of the operation.
     * @param user    The user who initiated the operation.
     */
    @SneakyThrows
    default void sendCallbackCountAll(ChannelMessage<USER, ModelType> message, Long count, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            LongMessage data = new LongMessage();
            data.setResult(count);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    /**
     * Sends a callback with the count of entities that match the criteria.
     *
     * @param message The original message.
     * @param count   The count of entities that match the criteria.
     * @param status  The status of the operation.
     * @param user    The user who initiated the operation.
     */
    @SneakyThrows
    default void sendCallbackCount(ChannelMessage<USER, CriteriaMessage<ID, C>> message, Long count, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            LongMessage data = new LongMessage();
            data.setResult(count);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    /**
     * Sends a callback with a page of DTOs.
     *
     * @param message The original message.
     * @param page    The page of DTOs to send in the callback.
     * @param status  The status of the operation.
     * @param user    The user who initiated the operation.
     */
    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackPage(ChannelMessage<USER, DATA> message, Page<DTO> page, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            PageMessage<ID, DTO> data = new PageMessage<>();
            data.setPage(page);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    /**
     * Returns the class of the domain.
     *
     * @return The class of the domain.
     */
    default Class<D> getDomainClass() {
        return (Class<D>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentCrudListener.class, 2);
    }

    /**
     * Returns the class of the DTO.
     *
     * @return The class of the DTO.
     */
    default Class<DTO> getDtoClass() {
        return (Class<DTO>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentCrudListener.class, 3);
    }

    /**
     * Returns the class of the criteria.
     *
     * @return The class of the criteria.
     */
    default Class<C> getCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentCrudListener.class, 4);
    }

    /**
     * Returns the class of the repository.
     *
     * @return The class of the repository.
     */
    default Class<R> getRepositoryClass() {
        return (Class<R>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentCrudListener.class, 5);
    }

}