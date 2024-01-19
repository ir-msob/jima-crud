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
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.write.BaseSaveManyCrudService;
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
 * This interface provides a RSocket API for saving multiple domains.
 * It extends the ParentCrudRsocketResource interface and provides a default implementation for the saveMany method.
 *
 * @param <ID> the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D> the type of the domain
 * @param <DTO> the type of the DTO
 * @param <C> the type of the criteria
 * @param <Q> the type of the query
 * @param <R> the type of the repository
 * @param <S> the type of the service
 * @author Yaqub Abdi
 */
public interface BaseSaveManyCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseSaveManyCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseSaveManyCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for saving multiple domains.
     * It validates the operation, retrieves the user, and then calls the service to save the domains.
     * It returns a Mono with the saved DTOs.
     *
     * @param dto       the DTO to save the domains
     * @param principal the Principal object
     * @return a Mono with the saved DTOs
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.SAVE_MANY)
    @MethodStats
    default Mono<Collection<DTO>> saveMany(@Payload String dto, @AuthenticationPrincipal Jwt principal)
            throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to create many new domain, dtos : {}", dto);
        ChannelMessage<ID, USER, DtosMessage<ID, DTO>> message = getObjectMapper().readValue(dto, getDtosReferenceType());

        crudValidation(Operations.SAVE_MANY);

        Optional<USER> user = getUser(message.getUser(), principal);
        return this.saveManyResponse(message.getData().getDtos(), this.getService().saveMany(message.getData().getDtos(), user), user);
    }

    /**
     * This method creates a Mono with the saved DTOs.
     * It is called by the saveMany method.
     *
     * @param messages  the DTOs to save the domains
     * @param savedDtos the Mono with the saved DTOs
     * @param user      the Optional object containing the user
     * @return a Mono with the saved DTOs
     */
    default Mono<Collection<DTO>> saveManyResponse(Collection<DTO> messages, Mono<Collection<DTO>> savedDtos, Optional<USER> user) {
        return savedDtos;
    }
}