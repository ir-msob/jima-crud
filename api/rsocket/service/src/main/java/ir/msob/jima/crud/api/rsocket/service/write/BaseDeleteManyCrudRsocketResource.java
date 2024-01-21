package ir.msob.jima.crud.api.rsocket.service.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.write.BaseDeleteManyCrudService;
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
 * This interface provides a RSocket API for deleting multiple domains based on a specific criteria.
 * It extends the ParentCrudRsocketResource interface and provides a default implementation for the deleteMany method.
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
public interface BaseDeleteManyCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDeleteManyCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseDeleteManyCrudRsocketResource.class);

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
    @Scope(Operations.DELETE_MANY)
    default Mono<Collection<ID>> deleteMany(@Payload String dto, @AuthenticationPrincipal Jwt principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to delete many domain, dto {}", dto);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> message = getObjectMapper().readValue(dto, getCriteriaReferenceType());

        Optional<USER> user = getUser(message.getUser(), principal);
        return this.deleteManyResponse(this.getService().deleteMany(message.getData().getCriteria(), user), message.getData().getCriteria(), user);
    }

    /**
     * This method creates a Mono with the IDs of the deleted domains.
     * It is called by the deleteMany method.
     *
     * @param ids      the Mono with the IDs of the deleted domains
     * @param criteria the criteria used to filter the domains
     * @param user     the Optional object containing the user
     * @return a Mono with the IDs of the deleted domains
     */
    default Mono<Collection<ID>> deleteManyResponse(Mono<Collection<ID>> ids, C criteria, Optional<USER> user) {
        return ids;
    }

}