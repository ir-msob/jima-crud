package ir.msob.jima.crud.api.rsocket.service.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.DtoMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.write.BaseUpdateCrudService;
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
 * This interface provides a RSocket API for updating a single domain by its ID.
 * It extends the ParentCrudRsocketResource interface and provides a default implementation for the updateById method.
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
public interface BaseUpdateByIdCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseUpdateCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseUpdateByIdCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for updating a single domain by its ID.
     * It validates the operation, retrieves the user, and then calls the service to update the domain.
     * It returns a Mono with the updated DTO.
     *
     * @param dto       the DTO to update the domain
     * @param principal the Principal object
     * @return a Mono with the updated DTO
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.UPDATE_BY_ID)
    @MethodStats
    @Scope(Operations.UPDATE_BY_ID)
    default Mono<DTO> updateById(@Payload String dto, @AuthenticationPrincipal Jwt principal)
            throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to update domain, dto : {}", dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> message = getObjectMapper().readValue(dto, getDtoReferenceType());

        Optional<USER> user = getUser(message.getUser(), principal);
        return this.updateByIdResponse(message.getData().getId(), message.getData().getDto(), this.getService().update(message.getData().getId(), message.getData().getDto(), user), user);
    }

    /**
     * This method creates a Mono with the updated DTO.
     * It is called by the updateById method.
     *
     * @param id         the ID of the updated domain
     * @param dto        the DTO of the updated domain
     * @param updatedDto the Mono of the updated DTO
     * @param user       the Optional object containing the user
     * @return a Mono with the updated DTO
     */
    default Mono<DTO> updateByIdResponse(ID id, DTO dto, Mono<DTO> updatedDto, Optional<USER> user) {
        return updatedDto;
    }
}