package ir.msob.jima.crud.api.kafka.service.domain.write;

import ir.msob.jima.core.api.kafka.commons.KafkaListenerUtil;
import ir.msob.jima.core.commons.callback.CallbackError;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.IdMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.kafka.client.ChannelUtil;
import ir.msob.jima.crud.api.kafka.service.domain.ParentDomainCrudKafkaListener;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Interface for a listener that handles CRUD operations for deleting an entity by its ID.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <R>    The type of the Repository, which must extend BaseDomainCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseDomainCrudService.
 */
public interface BaseDeleteByIdDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByIdDomainCrudKafkaListener.class);

    /**
     * Initializes the listener for the DELETE_BY_ID operation.
     */
    @Scope(operation = Operations.DELETE_BY_ID)
    @PostConstruct
    default void startDeleteById() {
        KafkaListenerUtil.startListener(getKafkaConsumerFactory(),
                ChannelUtil.getChannel(getDtoClass(), Operations.DELETE_BY_ID),
                getGroupId(),
                this::deleteById);
    }

    /**
     * Handles the DELETE_BY_ID operation by reading the DTO from the message, deleting the entity by its ID, and sending a callback with the result.
     *
     * @param dto The DTO as a JSON string.
     */
    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    @Scope(operation = Operations.DELETE_BY_ID)
    @Transactional
    default void deleteById(String dto) {
        log.debug("Received message for delete by id: dto {}", dto);
        ChannelMessage<USER, IdMessage<ID>> message = getObjectMapper().readValue(dto, getChannelMessageIdReferenceType());
        getService().delete(message.getData().getId(), message.getUser())
                .subscribe(deletedId -> sendCallbackId(message, deletedId, OperationsStatus.DELETE_BY_ID, message.getUser()));
    }
}