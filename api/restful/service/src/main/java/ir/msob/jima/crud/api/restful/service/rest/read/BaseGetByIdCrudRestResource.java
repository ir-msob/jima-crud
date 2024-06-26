package ir.msob.jima.crud.api.restful.service.rest.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.rest.ParentCrudRestResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.read.BaseGetOneCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

/**
 * This interface provides a REST resource for getting a domain by its ID.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO of the domain
 * @param <USER> the type of the user
 * @param <C>    the type of the criteria for the domain
 * @param <Q>    the type of the query for the domain
 * @param <R>    the type of the repository for the domain
 * @param <S>    the type of the service for the domain
 * @author Yaqub Abdi
 */
public interface BaseGetByIdCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseGetOneCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseGetByIdCrudRestResource.class);

    /**
     * This method provides a REST endpoint for getting a domain by its ID.
     *
     * @param id                the ID of the domain
     * @param serverWebExchange the server web exchange
     * @param principal         the principal
     * @return a ResponseEntity containing a Mono of the DTO of the domain
     * @throws BadRequestException     if the request is bad
     * @throws DomainNotFoundException if the domain is not found
     * @throws JsonProcessingException if there is an error processing the JSON
     */
    @GetMapping("{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(Operations.GET_BY_ID)
    default ResponseEntity<Mono<DTO>> getById(@PathVariable("id") ID id, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("REST request to get by id domain, id {}", id);

        Optional<USER> user = getUser(serverWebExchange, principal);
        return this.getByIdResponse(this.getService().getOne(id, user), id, user);
    }

    /**
     * This method provides a response for the getById method.
     *
     * @param dto  the DTO of the domain
     * @param id   the ID of the domain
     * @param user the user
     * @return a ResponseEntity containing a Mono of the DTO of the domain
     */
    default ResponseEntity<Mono<DTO>> getByIdResponse(Mono<DTO> dto, ID id, Optional<USER> user) {
        return ResponseEntity.status(OperationsStatus.GET_BY_ID).body(dto);
    }
}