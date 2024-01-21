package ir.msob.jima.crud.api.restful.service.rest.write;

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
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.operation.OperationsStatus;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.rest.ParentCrudRestResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.write.BaseDeleteCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

/**
 * This interface provides a REST resource for deleting a domain by its ID.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <D>    the type of the domain
 * @param <USER> the type of the user
 * @param <C>    the type of the criteria for the domain
 * @param <Q>    the type of the query for the domain
 * @param <R>    the type of the repository for the domain
 * @param <S>    the type of the service for the domain
 * @author Yaqub Abdi
 */
public interface BaseDeleteByIdCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDeleteCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByIdCrudRestResource.class);

    /**
     * This method provides a REST endpoint for deleting a domain by its ID.
     *
     * @param id                the ID of the domain
     * @param serverWebExchange the server web exchange
     * @param principal         the principal
     * @return a ResponseEntity containing a Mono of the ID of the deleted domain
     * @throws BadRequestException     if the request is bad
     * @throws DomainNotFoundException if the domain is not found
     */
    @DeleteMapping("{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(Operations.DELETE_BY_ID)
    default ResponseEntity<Mono<ID>> deleteById(@PathVariable("id") ID id, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete domain by id, id {}", id);

        Optional<USER> user = getUser(serverWebExchange, principal);
        return this.deleteByIdResponse(this.getService().delete(id, user), id, user);
    }

    /**
     * This method provides a response for the deleteById method.
     *
     * @param id        the ID of the deleted domain
     * @param requestId the ID of the request
     * @param user      the user
     * @return a ResponseEntity containing a Mono of the ID of the deleted domain
     */
    default ResponseEntity<Mono<ID>> deleteByIdResponse(Mono<ID> id, ID requestId, Optional<USER> user) {
        return ResponseEntity.status(OperationsStatus.DELETE_BY_ID).body(id);
    }

}