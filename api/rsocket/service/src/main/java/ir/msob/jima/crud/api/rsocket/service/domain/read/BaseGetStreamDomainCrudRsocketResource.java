package ir.msob.jima.crud.api.rsocket.service.domain.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.CriteriaMessage;
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
import ir.msob.jima.crud.service.domain.read.BaseGetStreamDomainCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;

import java.io.Serializable;

/**
 * This interface provides a RSocket API for retrieving a stream of domains based on a specific criteria.
 * It extends the ParentDomainCrudRsocketResource interface and provides a default implementation for the getStream method.
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
public interface BaseGetStreamDomainCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseGetStreamDomainCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseGetStreamDomainCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for retrieving a stream of domains based on a specific criteria.
     * It validates the operation, retrieves the user, and then calls the service to get the domains.
     * It returns a Flux with the DTOs that meet the criteria.
     *
     * @param dto       the DTO to get the domains
     * @param principal the Principal object
     * @return a Flux with the DTOs that meet the criteria
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.GET_STREAM)
    @MethodStats
    @Scope(operation = Operations.GET_STREAM)
    default Flux<DTO> getStream(@Payload String dto, @AuthenticationPrincipal Jwt principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to get stream domain, dto {} : ", dto);
        ChannelMessage<USER, CriteriaMessage<ID, C>> message = getObjectMapper().readValue(dto, getCriteriaReferenceType());

        USER user = getUser(message.getUser(), principal);
        return this.getService().getStream(message.getData().getCriteria(), user);
    }
}