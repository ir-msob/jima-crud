package ir.msob.jima.crud.api.rsocket.service.domain.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.IdJsonPatchMessage;
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
import ir.msob.jima.crud.service.domain.write.BaseEditDomainCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;

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
public interface BaseEditByIdDomainCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,

        S extends BaseEditDomainCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseEditByIdDomainCrudRsocketResource.class);

    @MessageMapping(Operations.EDIT_BY_ID)
    @MethodStats
    @Scope(operation = Operations.EDIT_BY_ID)
    default Mono<DTO> editById(@Payload String dto, @AuthenticationPrincipal Jwt principal)
            throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to edit domain, dto : {}", dto);
        ChannelMessage<USER, IdJsonPatchMessage<ID>> message = getObjectMapper().readValue(dto, getIdJsonPatchReferenceType());

        USER user = getUser(message.getUser(), principal);
        return this.getService().edit(message.getData().getId(), message.getData().getJsonPatch(), user);
    }
}
