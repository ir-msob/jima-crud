package ir.msob.jima.crud.api.rsocket.service.domain.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.JsonPatchMessage;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.domain.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.write.BaseEditCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * This interface provides a RSocket API for editing a domain based on a specific criteria.
 * It extends the ParentCrudRsocketResource interface and provides a default implementation for the edit method.
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
public interface BaseEditCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseEditCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseEditCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for editing a domain based on a specific criteria.
     * It validates the operation, retrieves the user, and then calls the service to edit the domain.
     * It returns a Mono with the edited DTO.
     *
     * @param dto       the DTO to edit the domain
     * @param principal the Principal object
     * @return a Mono with the edited DTO
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.EDIT)
    @MethodStats
    @Scope(Operations.EDIT)
    default Mono<DTO> edit(@Payload String dto, @AuthenticationPrincipal Jwt principal)
            throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to edit new domain, dto : {}", dto);
        ChannelMessage<USER, JsonPatchMessage<ID, C>> message = getObjectMapper().readValue(dto, getEditReferenceType());

        USER user = getUser(message.getUser(), principal);
        return this.editResponse(message.getData().getJsonPatch(), this.getService().edit(message.getData().getCriteria(), message.getData().getJsonPatch(), user), message.getData().getCriteria(), user);
    }

    /**
     * This method creates a Mono with the edited DTO.
     * It is called by the edit method.
     *
     * @param dto       the JsonPatch with the changes to apply to the domain
     * @param editedDto the Mono with the edited DTO
     * @param criteria  the criteria used to filter the domain
     * @param user      the user
     * @return a Mono with the edited DTO
     */
    default Mono<DTO> editResponse(JsonPatch dto, Mono<DTO> editedDto, C criteria, USER user) {
        return editedDto;
    }
}