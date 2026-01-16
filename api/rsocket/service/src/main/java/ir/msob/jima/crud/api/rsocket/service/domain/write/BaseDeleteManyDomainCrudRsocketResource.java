package ir.msob.jima.crud.api.rsocket.service.domain.write;

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
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.domain.ParentDomainCrudRsocketResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.write.BaseDeleteManyDomainCrudService;
import org.jspecify.annotations.NonNull;
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
 * This interface provides a RSocket API for deleting multiple domains based on a specific criteria.
 * It extends the ParentDomainCrudRsocketResource interface and provides a default implementation for the deleteMany method.
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
public interface BaseDeleteManyDomainCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDeleteManyDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S> {
    Logger log = LoggerFactory.getLogger(BaseDeleteManyDomainCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for deleting multiple domains based on a specific criteria.
     * It validates the operation, retrieves the user, and then calls the service to delete the domains.
     * It returns a Mono with the IDs of the deleted domains.
     *
     * @param dto       the DTO to delete the domains
     * @param principal the Principal object
     * @return a Mono with the IDs of the deleted domains
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.DELETE_MANY)
    @MethodStats
    @Scope(operation = Operations.DELETE_MANY)
    default Mono<@NonNull Collection<ID>> deleteMany(@Payload String dto, @AuthenticationPrincipal Jwt principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to delete many domain, dto {}", dto);
        ChannelMessage<USER, CriteriaMessage<ID, C>> message = getObjectMapper().readValue(dto, getChannelMessageCriteriaReferenceType());

        USER user = getUser(message.getUser(), principal);
        return this.getService().deleteMany(message.getData().getCriteria(), user);
    }
}