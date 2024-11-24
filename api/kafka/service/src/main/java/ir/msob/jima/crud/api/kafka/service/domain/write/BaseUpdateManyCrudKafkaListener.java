package ir.msob.jima.crud.api.kafka.service.domain.write;

import ir.msob.jima.core.commons.callback.CallbackError;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.DtosMessage;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.kafka.service.domain.ParentCrudKafkaListener;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import java.io.Serializable;

/**
 * Interface for a listener that handles CRUD operations for updating multiple entities.
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
public interface BaseUpdateManyCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseUpdateManyCrudKafkaListener.class);

    /**
     * Initializes the listener for the UPDATE_MANY operation.
     */
    @Scope(Operations.UPDATE_MANY)
    @PostConstruct
    default void updateMany() {
        String operation = Operations.UPDATE_MANY;

        // Create container properties for the Kafka listener
        ContainerProperties containerProperties = createContainerProperties(operation);
        // Set the message listener to handle incoming messages
        containerProperties.setMessageListener((MessageListener<String, String>) dto -> serviceUpdateMany(dto.value()));
        // Start the Kafka listener container
        startContainer(containerProperties, operation);
    }

    /**
     * Handles the UPDATE_MANY operation by reading the DTOs from the message, updating the entities, and sending a callback with the result.
     *
     * @param dto The DTOs as a JSON string.
     */
    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    @Scope(Operations.UPDATE_MANY)
    private void serviceUpdateMany(String dto) {
        log.debug("Received message for update many: dto {}", dto);
        // Parse the message from the JSON string
        ChannelMessage<USER, DtosMessage<ID, DTO>> message = getObjectMapper().readValue(dto, getDtosReferenceType());
        // Call the service to update the entities and send a callback with the result
        getService().updateMany(message.getData().getDtos(), message.getUser())
                .subscribe(updatedDtos -> sendCallbackDtos(message, updatedDtos, OperationsStatus.UPDATE_MANY, message.getUser()));
    }
}