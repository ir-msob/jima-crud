package ir.msob.jima.crud.api.kafka.service.read;

import ir.msob.jima.core.commons.annotation.async.CallbackError;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.IdMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.model.scope.ScopeInitializer;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.kafka.service.ParentCrudKafkaListener;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import java.io.Serializable;
import java.util.Optional;

/**
 * Interface for a listener that handles CRUD operations for getting an entity by its ID.
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
public interface BaseGetByIdCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseGetByIdCrudKafkaListener.class);

    /**
     * Initializes the listener for the GET_BY_ID operation.
     */
    @ScopeInitializer(Operations.GET_BY_ID)
    @PostConstruct
    default void getById() {
        String operation = Operations.GET_BY_ID;

        ContainerProperties containerProperties = createContainerProperties(operation);
        containerProperties.setMessageListener((MessageListener<String, String>) dto -> serviceGetById(dto.value()));
        startContainer(containerProperties, operation);
    }

    /**
     * Handles the GET_BY_ID operation by reading the DTO from the message, getting the entity by its ID, and sending a callback with the result.
     *
     * @param dto The DTO as a JSON string.
     */
    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    @Scope(Operations.GET_BY_ID)
    private void serviceGetById(String dto) {
        log.debug("Received message for get by id: dto {}", dto);
        ChannelMessage<USER, IdMessage<ID>> message = getObjectMapper().readValue(dto, getIdReferenceType());
        Optional<USER> user = Optional.ofNullable(message.getUser());
        getService().getOne(message.getData().getId(), user)
                .subscribe(getOneDto -> sendCallbackDto(message, getOneDto, OperationsStatus.GET_BY_ID, user));
    }
}