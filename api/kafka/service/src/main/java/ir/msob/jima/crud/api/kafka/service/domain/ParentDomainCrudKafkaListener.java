package ir.msob.jima.crud.api.kafka.service.domain;

import ir.msob.jima.core.api.kafka.commons.BaseKafkaListener;
import ir.msob.jima.core.commons.channel.BaseChannelTypeReference;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.*;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.ModelType;
import ir.msob.jima.core.commons.shared.PageDto;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

/**
 * Interface for a listener that handles CRUD operations.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <R>    The type of the Repository, which must extend BaseDomainCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface ParentDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>>
        extends BaseKafkaListener<ID, USER>,
        BaseChannelTypeReference<ID, USER, DTO, C> {

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
    default <DATA extends ModelType> void sendCallbackDtos(ChannelMessage<USER, DATA> message, Collection<DTO> dtos, Integer status, USER user) {
        if (!message.getCallbacks().isEmpty()) {
            DtosMessage<ID, DTO> data = new DtosMessage<>();
            data.setDtos(dtos);
            for (ChannelMessage<USER, ? extends ModelType> callback : message.getCallbacks()) {
                getAsyncClient().send(callback.getChannel(), prepareChannelMessage(callback, data, status, user), user);
            }
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
    default <DATA extends ModelType> void sendCallbackDto(ChannelMessage<USER, DATA> message, DTO dto, Integer status, USER user) {
        if (!message.getCallbacks().isEmpty()) {
            DtoMessage<ID, DTO> data = new DtoMessage<>();
            data.setDto(dto);
            for (ChannelMessage<USER, ? extends ModelType> callback : message.getCallbacks()) {
                getAsyncClient().send(callback.getChannel(), prepareChannelMessage(callback, data, status, user), user);
            }
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
    default <DATA extends ModelType> void sendCallbackIds(ChannelMessage<USER, DATA> message, Collection<ID> ids, Integer status, USER user) {
        if (!message.getCallbacks().isEmpty()) {
            IdsMessage<ID> data = new IdsMessage<>();
            data.setIds(ids);
            for (ChannelMessage<USER, ? extends ModelType> callback : message.getCallbacks()) {
                getAsyncClient().send(callback.getChannel(), prepareChannelMessage(callback, data, status, user), user);
            }
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
    default <DATA extends ModelType> void sendCallbackId(ChannelMessage<USER, DATA> message, ID id, Integer status, USER user) {
        if (!message.getCallbacks().isEmpty()) {
            IdMessage<ID> data = new IdMessage<>();
            data.setId(id);
            for (ChannelMessage<USER, ? extends ModelType> callback : message.getCallbacks()) {
                getAsyncClient().send(callback.getChannel(), prepareChannelMessage(callback, data, status, user), user);
            }
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
    default void sendCallbackCountAll(ChannelMessage<USER, ModelType> message, Long count, Integer status, USER user) {
        if (!message.getCallbacks().isEmpty()) {
            LongMessage data = new LongMessage();
            data.setResult(count);
            for (ChannelMessage<USER, ? extends ModelType> callback : message.getCallbacks()) {
                getAsyncClient().send(callback.getChannel(), prepareChannelMessage(callback, data, status, user), user);
            }
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
    default void sendCallbackCount(ChannelMessage<USER, CriteriaMessage<ID, C>> message, Long count, Integer status, USER user) {
        if (!message.getCallbacks().isEmpty()) {
            LongMessage data = new LongMessage();
            data.setResult(count);
            for (ChannelMessage<USER, ? extends ModelType> callback : message.getCallbacks()) {
                getAsyncClient().send(callback.getChannel(), prepareChannelMessage(callback, data, status, user), user);
            }
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
    default <DATA extends ModelType> void sendCallbackPage(ChannelMessage<USER, DATA> message, PageDto<DTO> page, Integer status, USER user) {
        if (!message.getCallbacks().isEmpty()) {
            PageMessage<ID, DTO> data = new PageMessage<>();
            data.setPage(page);
            for (ChannelMessage<USER, ? extends ModelType> callback : message.getCallbacks()) {
                getAsyncClient().send(callback.getChannel(), prepareChannelMessage(callback, data, status, user), user);
            }
        }
    }

    /**
     * Returns the class of the domain.
     *
     * @return The class of the domain.
     */
    default Class<D> getDomainClass() {
        return (Class<D>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentDomainCrudKafkaListener.class, 2);
    }

    /**
     * Returns the class of the DTO.
     *
     * @return The class of the DTO.
     */
    default Class<DTO> getDtoClass() {
        return (Class<DTO>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentDomainCrudKafkaListener.class, 3);
    }

    /**
     * Returns the class of the criteria.
     *
     * @return The class of the criteria.
     */
    default Class<C> getCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentDomainCrudKafkaListener.class, 4);
    }

    /**
     * Returns the class of the repository.
     *
     * @return The class of the repository.
     */
    default Class<R> getRepositoryClass() {
        return (Class<R>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentDomainCrudKafkaListener.class, 5);
    }

}