package ir.msob.jima.crud.api.rsocket.service.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.PageableMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.ParentCrudRsocketResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.read.BaseGetPageCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Optional;

/**
 * This interface provides a RSocket API for retrieving a page of domains based on a specific criteria.
 * It extends the ParentCrudRsocketResource interface and provides a default implementation for the getPage method.
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
public interface BaseGetPageCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseGetPageCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseGetPageCrudRsocketResource.class);

    /**
     * This method provides a RSocket API for retrieving a page of domains based on a specific criteria.
     * It validates the operation, retrieves the user, and then calls the service to get the domains.
     * It returns a Mono with a page of DTOs that meet the criteria.
     *
     * @param dto       the DTO to get the domains
     * @param principal the Principal object
     * @return a Mono with a page of DTOs that meet the criteria
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @MessageMapping(Operations.GET_PAGE)
    @MethodStats
    @Scope(Operations.GET_PAGE)
    default Mono<Page<DTO>> getPage(@Payload String dto, @AuthenticationPrincipal Jwt principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("RSocket request to get page domain, dto {}", dto);
        ChannelMessage<USER, PageableMessage<ID, C>> message = getObjectMapper().readValue(dto, getCriteriaPageReferenceType());

        Optional<USER> user = getUser(message.getUser(), principal);
        return this.getPageResponse(this.getService().getPage(message.getData().getCriteria(), message.getData().getPageable(), user), message.getData().getCriteria(), user);
    }

    /**
     * This method creates a Mono with a page of DTOs that meet the specific criteria.
     * It is called by the getPage method.
     *
     * @param dtoPage  the Mono with a page of DTOs that meet the criteria
     * @param criteria the criteria used to filter the domains
     * @param user     the Optional object containing the user
     * @return a Mono with a page of DTOs that meet the criteria
     */
    default Mono<Page<DTO>> getPageResponse(Mono<Page<DTO>> dtoPage, C criteria, Optional<USER> user) {
        return dtoPage;
    }
}