package ir.msob.jima.crud.api.rsocket.service.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.read.BaseCountAllCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * This interface provides a RSocket API for counting all domains of a specific type.
 * It extends the ParentCrudRsocketResource interface and provides a default implementation for the countAll method.
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
public interface BaseCountAllCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCountAllCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseCountAllCrudRsocketResource.class);

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
    @Scope(Operations.COUNT_ALL)
    default Mono<Long> countAll(@Payload String dto, @AuthenticationPrincipal Jwt principal) throws BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, JsonProcessingException {
        log.debug("RSocket request to count all dto {}", dto);
        ChannelMessage<ID, USER, ModelType> message = getObjectMapper().readValue(dto, getModelTypeReferenceType());

        Optional<USER> user = getUser(message.getUser(), principal);
        return this.countAllResponse(this.getService().countAll(user), user);
    }

    /**
     * This method creates a Mono with the count of all domains of the specified type.
     * It is called by the countAll method.
     *
     * @param result the Mono with the count of all domains of the specified type
     * @param user   the Optional object containing the user
     * @return a Mono with the count of all domains of the specified type
     */
    default Mono<Long> countAllResponse(Mono<Long> result, Optional<USER> user) {
        return result;
    }

}