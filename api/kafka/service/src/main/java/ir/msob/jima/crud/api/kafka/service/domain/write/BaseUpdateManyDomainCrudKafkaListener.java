package ir.msob.jima.crud.api.kafka.service.domain.write;

import ir.msob.jima.core.api.kafka.commons.KafkaListenerUtil;
import ir.msob.jima.core.commons.callback.CallbackError;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.DtosMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Interface for a listener that handles CRUD operations for updating multiple entities.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <R>    The type of the Repository, which must extend BaseDomainCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseDomainCrudService.
 */
public interface BaseUpdateManyDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseUpdateManyDomainCrudKafkaListener.class);

    /**
     * Initializes the listener for the UPDATE_MANY operation.
     */
    @Scope(operation = Operations.UPDATE_MANY)
    @PostConstruct
    default void startUpdateMany() {
        KafkaListenerUtil.startListener(getKafkaConsumerFactory(),
                ChannelUtil.getChannel(getDtoClass(), Operations.UPDATE_MANY),
                getGroupId(),
                this::updateMany);
    }

    /**
     * Handles the UPDATE_MANY operation by reading the DTOs from the message, updating the entities, and sending a callback with the result.
     *
     * @param dto The DTOs as a JSON string.
     */
    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    @Scope(operation = Operations.UPDATE_MANY)
    @Transactional
    default void updateMany(String dto) {
        logger.debug("Received message for update many: dto {}", dto);
        // Parse the message from the JSON string
        ChannelMessage<USER, DtosMessage<ID, DTO>> message = getObjectMapper().readValue(dto, getChannelMessageDtosReferenceType());
        // Call the service to update the entities and send a callback with the result
        getService().updateMany(message.getData().getDtos(), message.getUser())
                .subscribe(updatedDtos -> sendCallbackDtos(message, updatedDtos, OperationsStatus.UPDATE_MANY, message.getUser()));
    }
}