package ir.msob.jima.crud.api.kafka.service.domain.read;

import ir.msob.jima.core.api.kafka.commons.KafkaListenerUtil;
import ir.msob.jima.core.commons.callback.CallbackError;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.PageableMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Interface for a listener that handles CRUD operations for retrieving a page of entities based on a given criteria.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <R>    The type of the Repository, which must extend BaseDomainCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseDomainCrudService.
 */
public interface BaseGetPageDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseGetPageDomainCrudKafkaListener.class);

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
    @CallbackError("dto")
    @Scope(operation = Operations.GET_PAGE)
    @Transactional
    default void getPage(String dto) {
        log.debug("Received message for get page: dto {}", dto);
        ChannelMessage<USER, PageableMessage<ID, C>> message;
        message = getObjectMapper().readValue(dto, getChannelMessagePageableReferenceType());
        getService().getPage(message.getData().getCriteria(), message.getData().getPageable(), message.getUser())
                .subscribe(page -> sendCallbackPage(message, page, OperationsStatus.GET_PAGE, message.getUser()));
    }
}