package ir.msob.jima.crud.kafka.reactive.resource.domain.read;

import ir.msob.jima.crud.kafka.reactive.resource.domain.ParentDomainCrudKafkaListener;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.ChannelUtil;
import ir.msob.jima.platform.api.channel.message.PageableMessage;
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
import ir.msob.jima.platform.api.shared.PageDto;
import ir.msob.jima.platform.kafka.api.util.KafkaListenerUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Interface for a listener that handles CRUD operations for retrieving a page of entities based on a given criteria.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDomainDto.
 * @param <C>    The type of the Criteria, which must extend BaseDomainCriteria.
 * @param <R>    The type of the Repository, which must extend BaseReactiveRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface BaseGetPageDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseGetPageDomainCrudKafkaListener.class);

    /**
     * Initializes the listener for the GET_PAGE operation.
     */
    @Scope(operation = Operations.GET_PAGE)
    @PostConstruct
    default void startGetPage() {
        KafkaListenerUtil.startListener(getKafkaConsumerFactory(),
                ChannelUtil.getChannel(getDtoClass(), Operations.GET_PAGE),
                getGroupId(),
                this::getPage);
    }

    /**
     * Handles the GET_PAGE operation by reading the Criteria and Pageable from the message, retrieving a page of entities based on the criteria, and sending a callback with the result.
     *
     * @param dto The DTO as a JSON string.
     */
    @MethodStats
    @SneakyThrows
    @PublishEvent("dto")
    @Scope(operation = Operations.GET_PAGE)
    @Transactional
    default void getPage(String dto) {
        logger.debug("Received message for get page: dto {}", dto);
        ChannelMessage<USER, PageableMessage<ID, C>> message;
        message = getObjectMapper().readValue(dto, getChannelMessagePageableReferenceType());
        getService().getPage(message.getData().getCriteria(), message.getData().getPageable().toPageable(), message.getUser())
                .subscribe(page -> getChannelMessagePublisher().sendCallbackPage(message, PageDto.from(page), OperationsStatus.GET_PAGE, message.getUser()));
    }
}