package ir.msob.jima.crud.api.kafka.service.domain.write;

import ir.msob.jima.core.api.kafka.commons.KafkaListenerUtil;
import ir.msob.jima.core.commons.callback.CallbackError;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.DtoMessage;
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
 * Interface for a listener that handles CRUD operations for updating entities.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <R>    The type of the Repository, which must extend BaseDomainCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface BaseUpdateDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseUpdateDomainCrudKafkaListener.class);

    /**
     * Initializes the listener for the UPDATE operation.
     */
    @Scope(operation = Operations.UPDATE)
    @PostConstruct
    default void startUpdate() {
        KafkaListenerUtil.startListener(getKafkaConsumerFactory(),
                ChannelUtil.getChannel(getDtoClass(), Operations.UPDATE),
                getGroupId(),
                this::update);
    }

    /**
     * Handles the UPDATE operation by reading the DTO from the message, updating the entity, and sending a callback with the result.
     *
     * @param dto The DTO as a JSON string.
     */
    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    @Scope(operation = Operations.UPDATE)
    @Transactional
    default void update(String dto) {
        logger.debug("Received message for update: dto {}", dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> message = getObjectMapper().readValue(dto, getChannelMessageDtoReferenceType());
        getService().update(message.getData().getDto(), message.getUser())
                .subscribe(updatedDto -> sendCallbackDto(message, updatedDto, OperationsStatus.UPDATE, message.getUser()));
    }
}