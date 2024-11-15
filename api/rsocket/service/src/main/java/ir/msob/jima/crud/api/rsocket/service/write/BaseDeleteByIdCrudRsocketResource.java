package ir.msob.jima.crud.api.rsocket.service.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.IdMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.write.BaseDeleteCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * This interface provides a RSocket API for deleting a single domain by its ID.
 * It extends the ParentCrudRsocketResource interface and provides a default implementation for the deleteById method.
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
public interface BaseDeleteByIdCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseDeleteCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByIdCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for deleting a single domain by its ID.
     * It validates the operation, retrieves the user, and then calls the service to delete the domain.
     * It returns a Mono with the ID of the deleted domain.
     *
     * @param dto       the DTO to delete the domain
     * @param principal the Principal object
     * @return a Mono with the ID of the deleted domain
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.DELETE_BY_ID)
    @MethodStats
    @Scope(Operations.DELETE_BY_ID)
    default Mono<ID> deleteById(@Payload String dto, @AuthenticationPrincipal Jwt principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to delete domain, dto {}", dto);
        ChannelMessage<USER, IdMessage<ID>> message = getObjectMapper().readValue(dto, getIdReferenceType());

        USER user = getUser(message.getUser(), principal);
        return this.deleteByIdResponse(this.getService().delete(message.getData().getId(), user), message.getData().getId(), user);
    }

    /**
     * This method creates a Mono with the ID of the deleted domain.
     * It is called by the deleteById method.
     *
     * @param id        the ID of the deleted domain
     * @param requestId the ID of the request
     * @param user      the user
     * @return a Mono with the ID of the deleted domain
     */
    default Mono<ID> deleteByIdResponse(Mono<ID> id, ID requestId, USER user) {
        return id;
    }

}