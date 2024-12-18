package ir.msob.jima.crud.api.rsocket.service.domain.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.DtosMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.domain.ParentDomainCrudRsocketResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.write.BaseSaveManyDomainCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface provides a RSocket API for saving multiple domains.
 * It extends the ParentDomainCrudRsocketResource interface and provides a default implementation for the saveMany method.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <Q>    the type of the query
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 * @author Yaqub Abdi
 */
public interface BaseSaveManyDomainCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseSaveManyDomainCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseSaveManyDomainCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for saving multiple domains.
     * It validates the operation, retrieves the user, and then calls the service to save the domains.
     * It returns a Mono with the saved DTOs.
     *
     * @param dto       the DTO to save the domains
     * @param principal the Principal object
     * @return a Mono with the saved DTOs
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.SAVE_MANY)
    @MethodStats
    @Scope(operation = Operations.SAVE_MANY)
    default Mono<Collection<DTO>> saveMany(@Payload String dto, @AuthenticationPrincipal Jwt principal)
            throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to create many new domain, dtos : {}", dto);
        ChannelMessage<USER, DtosMessage<ID, DTO>> message = getObjectMapper().readValue(dto, getDtosReferenceType());

        USER user = getUser(message.getUser(), principal);
        return this.getService().saveMany(message.getData().getDtos(), user);
    }
}