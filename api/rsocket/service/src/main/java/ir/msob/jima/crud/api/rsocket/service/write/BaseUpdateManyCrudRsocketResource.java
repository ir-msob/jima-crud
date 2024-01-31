package ir.msob.jima.crud.api.rsocket.service.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtosMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.write.BaseUpdateManyCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * This interface provides a RSocket API for updating multiple domains.
 * It extends the ParentCrudRsocketResource interface and provides a default implementation for the updateMany method.
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
public interface BaseUpdateManyCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseUpdateManyCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseUpdateManyCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for updating multiple domains.
     * It validates the operation, retrieves the user, and then calls the service to update the domains.
     * It returns a Mono with the updated DTOs.
     *
     * @param dto       the DTO to update the domains
     * @param principal the Principal object
     * @return a Mono with the updated DTOs
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.UPDATE_MANY)
    @MethodStats
    @Scope(Operations.UPDATE_MANY)
    default Mono<Collection<DTO>> updateMany(@Payload String dto, @AuthenticationPrincipal Jwt principal)
            throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to update many domain, dtos : {}", dto);
        ChannelMessage<ID, USER, DtosMessage<ID, DTO>> message = getObjectMapper().readValue(dto, getDtosReferenceType());

        Optional<USER> user = getUser(message.getUser(), principal);
        return this.updateManyResponse(message.getData().getDtos(), this.getService().updateMany(message.getData().getDtos(), user), user);
    }

    /**
     * This method creates a Mono with the updated DTOs.
     * It is called by the updateMany method.
     *
     * @param dtos        the DTOs to update the domains
     * @param updatedDtos the Mono with the updated DTOs
     * @param user        the Optional object containing the user
     * @return a Mono with the updated DTOs
     */
    default Mono<Collection<DTO>> updateManyResponse(Collection<DTO> dtos, Mono<Collection<DTO>> updatedDtos, Optional<USER> user) {
        return updatedDtos;
    }
}