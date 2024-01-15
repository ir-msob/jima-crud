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

public interface ParentCrudListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>>
        extends BaseKafkaListener<ID, USER>,
        BaseChannelTypeReference<ID, USER, DTO, C> {
    default ContainerProperties createContainerProperties(String operation) {
        return createKafkaContainerProperties(Constants.getChannel(getDtoClass(), operation));
    }

    default void startContainer(ContainerProperties containerProperties, String operation) {
        startKafkaContainer(containerProperties, Constants.getChannel(getDtoClass(), operation));
    }

    S getService();

    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackDtos(ChannelMessage<ID, USER, DATA> message, Collection<DTO> dtos, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            DtosMessage<ID, DTO> data = new DtosMessage<>();
            data.setDtos(dtos);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackDto(ChannelMessage<ID, USER, DATA> message, DTO dto, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            DtoMessage<ID, DTO> data = new DtoMessage<>();
            data.setDto(dto);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackIds(ChannelMessage<ID, USER, DATA> message, Collection<ID> ids, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            IdsMessage<ID> data = new IdsMessage<>();
            data.setIds(ids);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackId(ChannelMessage<ID, USER, DATA> message, ID id, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            IdMessage<ID> data = new IdMessage<>();
            data.setId(id);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default void sendCallbackCountAll(ChannelMessage<ID, USER, ModelType> message, Long count, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            LongMessage data = new LongMessage();
            data.setResult(count);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default void sendCallbackCount(ChannelMessage<ID, USER, CriteriaMessage<ID, C>> message, Long count, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            LongMessage data = new LongMessage();
            data.setResult(count);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    @SneakyThrows
    default <DATA extends ModelType> void sendCallbackPage(ChannelMessage<ID, USER, DATA> message, Page<DTO> page, Integer status, Optional<USER> user) {
        if (Strings.isNotBlank(message.getCallback())) {
            PageMessage<ID, DTO> data = new PageMessage<>();
            data.setPage(page);
            getAsyncClient().send(prepareChannelMessage(message, data, status, user), message.getCallback(), user);
        }
    }

    default Class<D> getDomainClass() {
        return (Class<D>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentCrudListener.class, 2);
    }

    default Class<DTO> getDtoClass() {
        return (Class<DTO>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentCrudListener.class, 3);
    }

    default Class<C> getCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentCrudListener.class, 4);
    }

    default Class<R> getRepositoryClass() {
        return (Class<R>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentCrudListener.class, 5);
    }

}
