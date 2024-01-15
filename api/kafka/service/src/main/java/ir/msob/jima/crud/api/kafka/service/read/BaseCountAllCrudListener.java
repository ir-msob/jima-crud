package ir.msob.jima.crud.api.kafka.service.read;

import ir.msob.jima.core.commons.annotation.async.CallbackError;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.model.operation.ConditionalOnOperationUtil;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.operation.OperationsStatus;
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

public interface BaseCountAllCrudListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudListener<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseCountAllCrudListener.class);

    @PostConstruct
    default void countAll() {
        String operation = Operations.COUNT_ALL;

        if (!ConditionalOnOperationUtil.hasOperation(operation, getClass()))
            return;

        ContainerProperties containerProperties = createContainerProperties(operation);
        containerProperties.setMessageListener((MessageListener<String, String>) dto -> serviceCountAll(dto.value()));
        startContainer(containerProperties, operation);
    }

    @MethodStats
    @SneakyThrows
    @CallbackError("dto")
    private void serviceCountAll(String dto) {
        ChannelMessage<ID, USER, ModelType> message = getObjectMapper().readValue(dto, getModelTypeReferenceType());
        Optional<USER> user = Optional.ofNullable(message.getUser());
        getService().countAll(user)
                .subscribe(count -> sendCallbackCountAll(message, count, OperationsStatus.COUNT_ALL, user));
    }

}
