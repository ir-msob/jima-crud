package ir.msob.jima.crud.api.rsocket.service.domain.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.ModelType;
import ir.msob.jima.crud.api.rsocket.service.domain.ParentDomainCrudRsocketResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.read.BaseCountAllDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * This interface provides a RSocket API for counting all domains of a specific type.
 * It extends the ParentDomainCrudRsocketResource interface and provides a default implementation for the countAll method.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 * @author Yaqub Abdi
 */
public interface BaseCountAllDomainCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseCountAllDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S> {
    Logger logger = LoggerFactory.getLogger(BaseCountAllDomainCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for counting all domains of a specific type.
     * It validates the operation, retrieves the user, and then calls the service to count the domains.
     * It returns a Mono with the count of all domains of the specified type.
     *
     * @param dto       the DTO to count the domains
     * @param principal the Principal object
     * @return a Mono with the count of all domains of the specified type
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.COUNT_ALL)
    @MethodStats
    @Scope(operation = Operations.COUNT_ALL)
    default Mono<@NonNull Long> countAll(@Payload String dto, @AuthenticationPrincipal Jwt principal) throws BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, JsonProcessingException {
        logger.debug("RSocket request to count all dto {}", dto);
        ChannelMessage<USER, ModelType> message = getObjectMapper().readValue(dto, getChannelMessageModelTypeReferenceType());

        USER user = getUser(message.getUser(), principal);
        return this.getService().countAll(user);
    }
}