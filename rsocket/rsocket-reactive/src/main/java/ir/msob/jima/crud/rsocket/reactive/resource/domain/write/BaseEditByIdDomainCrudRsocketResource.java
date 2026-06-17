package ir.msob.jima.crud.rsocket.reactive.resource.domain.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.crud.reactive.service.domain.write.BaseEditDomainCrudReactiveService;
import ir.msob.jima.crud.rsocket.reactive.resource.domain.ParentDomainCrudRsocketResource;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.message.IdJsonPatchMessage;
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
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,

        S extends BaseEditDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseEditByIdDomainCrudRsocketResource.class);

    @MessageMapping(Operations.EDIT_BY_ID)
    @MethodStats
    @Scope(operation = Operations.EDIT_BY_ID)
    default Mono<@NonNull DTO> editById(@Payload String dto, @AuthenticationPrincipal Jwt principal)
            throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        logger.debug("RSocket request to edit domain, dto : {}", dto);
        ChannelMessage<USER, IdJsonPatchMessage<ID>> message = getObjectMapper().readValue(dto, getChannelMessageIdJsonPatchReferenceType());

        USER user = getUser(message.getUser(), principal);
        return this.getService().edit(message.getData().getId(), message.getData().getJsonPatch(), user);
    }
}
