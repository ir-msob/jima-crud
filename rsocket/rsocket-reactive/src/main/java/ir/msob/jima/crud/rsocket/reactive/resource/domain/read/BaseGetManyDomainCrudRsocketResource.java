package ir.msob.jima.crud.rsocket.reactive.resource.domain.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.crud.reactive.service.domain.read.BaseGetManyDomainCrudReactiveService;
import ir.msob.jima.crud.rsocket.reactive.resource.domain.ParentDomainCrudRsocketResource;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.message.CriteriaMessage;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.scope.Scope;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface provides a RSocket API for retrieving multiple domains based on a specific criteria.
 * It extends the ParentDomainCrudRsocketResource interface and provides a default implementation for the getMany method.
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
public interface BaseGetManyDomainCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseGetManyDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseGetManyDomainCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for retrieving multiple domains based on a specific criteria.
     * It validates the operation, retrieves the user, and then calls the service to get the domains.
     * It returns a Mono with a collection of DTOs that meet the criteria.
     *
     * @param dto       the DTO to get the domains
     * @param principal the Principal object
     * @return a Mono with a collection of DTOs that meet the criteria
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.GET_MANY)
    @MethodStats
    @Scope(operation = Operations.GET_MANY)
    default Mono<@NonNull Collection<DTO>> getMany(@Payload String dto, @AuthenticationPrincipal Jwt principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        logger.debug("RSocket request to get many domain, dto {}", dto);
        ChannelMessage<USER, CriteriaMessage<ID, C>> message = getObjectMapper().readValue(dto, getChannelMessageCriteriaReferenceType());

        USER user = getUser(message.getUser(), principal);
        return this.getService().getMany(message.getData().getCriteria(), user);
    }
}