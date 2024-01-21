package ir.msob.jima.crud.api.kafka.service.write;

import ir.msob.jima.core.commons.annotation.async.CallbackError;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.IdJsonPatchMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.operation.OperationsStatus;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.model.scope.ScopeInitializer;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.kafka.service.ParentCrudListener;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Optional;

/**
 * Interface for a listener that handles CRUD operations for editing an entity by its ID.
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
public interface BaseEditByIdCrudListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudListener<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseEditByIdCrudListener.class);

    /**
     * Initializes the listener for the EDIT_BY_ID operation.
     */
    @ScopeInitializer(Operations.EDIT_BY_ID)
    @PostConstruct
    default void editById() {
        String operation = Operations.EDIT_BY_ID;

        ContainerProperties containerProperties = createContainerProperties(operation);
        containerProperties.setMessageListener((MessageListener<String, String>) dto -> serviceEditById(dto.value()));
        startContainer(containerProperties, operation);
    }

    /**
     * Handles the EDIT_BY_ID operation by reading the DTO from the message, editing the entity by its ID, and sending a callback with the result.
     *
     * @param dto The DTO as a JSON string.
     */
    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    @Scope(Operations.EDIT_BY_ID)
    private void serviceEditById(String dto) {
        log.debug("Received message for edit by id: dto {}", dto);
        ChannelMessage<ID, USER, IdJsonPatchMessage<ID>> message = getObjectMapper().readValue(dto, getIdJsonPatchReferenceType());
        Optional<USER> user = Optional.ofNullable(message.getUser());
        getService().edit(message.getData().getId(), message.getData().getJsonPatch(), user)
                .subscribe(editedDto -> sendCallbackDto(message, editedDto, OperationsStatus.EDIT_BY_ID, user));
    }
}