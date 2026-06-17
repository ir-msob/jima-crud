package ir.msob.jima.crud.kafka.reactive.resource.domain.read;

import ir.msob.jima.crud.kafka.reactive.resource.domain.ParentDomainCrudKafkaListener;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.ChannelUtil;
import ir.msob.jima.platform.api.channel.message.CriteriaMessage;
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
 * Interface for a listener that handles CRUD operations for retrieving multiple entities based on a given criteria.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDomainDto.
 * @param <C>    The type of the Criteria, which must extend BaseDomainCriteria.
 * @param <R>    The type of the Repository, which must extend BaseReactiveRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface BaseGetManyDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseGetManyDomainCrudKafkaListener.class);

    /**
     * Initializes the listener for the GET_MANY operation.
     */
    @Scope(operation = Operations.GET_MANY)
    @PostConstruct
    default void startGetMany() {
        KafkaListenerUtil.startListener(getKafkaConsumerFactory(),
                ChannelUtil.getChannel(getDtoClass(), Operations.GET_MANY),
                getGroupId(),
                this::getMany);
    }

    /**
     * Handles the GET_MANY operation by reading the Criteria from the message, retrieving multiple entities based on the criteria, and sending a callback with the result.
     *
     * @param dto The DTO as a JSON string.
     */
    @MethodStats
    @SneakyThrows
    @PublishEvent("dto")
    @Scope(operation = Operations.GET_MANY)
    @Transactional
    default void getMany(String dto) {
        logger.debug("Received message for get many: dto {}", dto);
        ChannelMessage<USER, CriteriaMessage<ID, C>> message = getObjectMapper().readValue(dto, getChannelMessageCriteriaReferenceType());
        getService().getMany(message.getData().getCriteria(), message.getUser())
                .subscribe(getListDto -> getChannelMessagePublisher().sendCallbackDtos(message, getListDto, OperationsStatus.GET_MANY, message.getUser()));
    }
}