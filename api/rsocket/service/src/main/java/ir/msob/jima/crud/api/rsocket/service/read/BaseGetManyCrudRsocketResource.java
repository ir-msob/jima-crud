package ir.msob.jima.crud.api.rsocket.service.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.runtime.CommonRuntimeException;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.ConditionalOnOperationUtil;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.read.BaseGetManyCrudService;
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
 * @param <ID>
 * @param <D>
 * @param <DTO>
 * @param <USER>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public interface BaseGetManyCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseGetManyCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseGetManyCrudRsocketResource.class);

    @MessageMapping(Operations.GET_MANY)
    @MethodStats
    default Mono<Collection<DTO>> getMany(@Payload String dto, @AuthenticationPrincipal Jwt principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to get many domain, dto {}", dto);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> message = getObjectMapper().readValue(dto, getCriteriaReferenceType());

        if (!ConditionalOnOperationUtil.hasOperation(Operations.GET_MANY, getClass()))
            throw new CommonRuntimeException("Unable to find route");

        Optional<USER> user = getUser(message.getUser(), principal);
        return this.getManyResponse(this.getService().getMany(message.getData().getCriteria(), user), message.getData().getCriteria(), user);
    }

    default Mono<Collection<DTO>> getManyResponse(Mono<Collection<DTO>> dtoPage, C criteria, Optional<USER> user) {
        return dtoPage;
    }
}
