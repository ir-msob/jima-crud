package ir.msob.jima.crud.kafka.reactive.resource.domain.write;

import ir.msob.jima.crud.kafka.reactive.resource.domain.ParentDomainCrudKafkaListener;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.ChannelUtil;
import ir.msob.jima.platform.api.channel.message.DtosMessage;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.event.publish.PublishEvent;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.scope.Scope;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.kafka.api.util.KafkaListenerUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
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
 * @param <DTO>  The type of the DTO, which must extend BaseDomainDto.
 * @param <C>    The type of the Criteria, which must extend BaseDomainCriteria.
 * @param <R>    The type of the Repository, which must extend BaseReactiveRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface BaseUpdateManyDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
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
    @PublishEvent("dto")
    @Scope(operation = Operations.UPDATE_MANY)
    @Transactional
    default void updateMany(String dto) {
        logger.debug("Received message for update many: dto {}", dto);
        // Parse the message from the JSON string
        ChannelMessage<USER, DtosMessage<ID, DTO>> message = getObjectMapper().readValue(dto, getChannelMessageDtosReferenceType());
        // Call the service to update the entities and send a callback with the result
        getService().updateMany(message.getData().getDtos(), message.getUser())
                .subscribe(updatedDtos -> getChannelMessagePublisher().sendCallbackDtos(message, updatedDtos, OperationsStatus.UPDATE_MANY, message.getUser()));
    }
}