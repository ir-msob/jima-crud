package ir.msob.jima.crud.api.rsocket.service.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.runtime.CommonRuntimeException;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtoMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.ConditionalOnOperationUtil;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.write.BaseSaveCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;
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
public interface BaseSaveCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseSaveCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseSaveCrudRsocketResource.class);

    @MessageMapping(Operations.SAVE)
    @MethodStats
    default Mono<DTO> save(@Payload String dto, @AuthenticationPrincipal Jwt principal)
            throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to create new domain, dto : {}", dto);
        ChannelMessage<ID, USER, DtoMessage<ID, DTO>> message = getObjectMapper().readValue(dto, getDtoReferenceType());

        if (!ConditionalOnOperationUtil.hasOperation(Operations.SAVE, getClass()))
            throw new CommonRuntimeException("Unable to find route");

        Optional<USER> user = getUser(message.getUser(), principal);
        return this.saveResponse(message.getData().getDto(), this.getService().save(message.getData().getDto(), user), user);
    }

    default Mono<DTO> saveResponse(DTO dto, Mono<DTO> savedDto, Optional<USER> user) {
        return savedDto;
    }
}
