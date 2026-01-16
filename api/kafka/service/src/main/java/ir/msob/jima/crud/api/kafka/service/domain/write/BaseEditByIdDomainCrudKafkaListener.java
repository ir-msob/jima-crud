package ir.msob.jima.crud.api.kafka.service.domain.write;

import ir.msob.jima.core.api.kafka.commons.KafkaListenerUtil;
import ir.msob.jima.core.commons.callback.CallbackError;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.IdJsonPatchMessage;
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
 * Interface for a listener that handles CRUD operations for editing an entity by its ID.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <R>    The type of the Repository, which must extend BaseDomainCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseDomainCrudService.
 */
public interface BaseEditByIdDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseEditByIdDomainCrudKafkaListener.class);

    /**
     * Initializes the listener for the EDIT_BY_ID operation.
     */
    @Scope(operation = Operations.EDIT_BY_ID)
    @PostConstruct
    default void startEditById() {
        KafkaListenerUtil.startListener(getKafkaConsumerFactory(),
                ChannelUtil.getChannel(getDtoClass(), Operations.EDIT_BY_ID),
                getGroupId(),
                this::editById);
    }

    /**
     * Handles the EDIT_BY_ID operation by reading the DTO from the message, editing the entity by its ID, and sending a callback with the result.
     *
     * @param dto The DTO as a JSON string.
     */
    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    @Scope(operation = Operations.EDIT_BY_ID)
    @Transactional
    default void editById(String dto) {
        logger.debug("Received message for edit by id: dto {}", dto);
        ChannelMessage<USER, IdJsonPatchMessage<ID>> message = getObjectMapper().readValue(dto, getChannelMessageIdJsonPatchReferenceType());
        getService().edit(message.getData().getId(), message.getData().getJsonPatch(), message.getUser())
                .subscribe(editedDto -> sendCallbackDto(message, editedDto, OperationsStatus.EDIT_BY_ID, message.getUser()));
    }
}