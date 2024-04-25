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
import ir.msob.jima.crud.service.read.BaseGetManyCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

/**
 * This interface provides a RESTful API for retrieving multiple domains based on a given criteria.
 * It extends the ParentCrudRestResource interface and provides a default implementation for the getMany method.
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
public interface BaseGetManyCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseGetManyCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseGetManyCrudRestResource.class);

    /**
     * This method provides a RESTful API for retrieving multiple domains based on a given criteria.
     * It validates the operation, retrieves the user, and then calls the service to get the domains.
     * It returns a ResponseEntity with the collection of DTOs.
     *
     * @param criteria          the criteria to get the domains
     * @param serverWebExchange the ServerWebExchange object
     * @param principal         the Principal object
     * @return a ResponseEntity with the collection of DTOs
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @GetMapping(Operations.GET_MANY)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @MethodStats
    @Scope(Operations.GET_MANY)
    default ResponseEntity<Mono<Collection<DTO>>> getMany(C criteria, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("REST request to get many domain, criteria {} : ", criteria);

        Optional<USER> user = getUser(serverWebExchange, principal);
        return this.getManyResponse(this.getService().getMany(criteria, user), criteria, user);
    }

    /**
     * This method creates a ResponseEntity with the collection of DTOs.
     * It is called by the getMany method.
     *
     * @param dtoPage  the Mono object containing the collection of DTOs
     * @param criteria the criteria to get the domains
     * @param user     the Optional object containing the user
     * @return a ResponseEntity with the collection of DTOs
     */
    default ResponseEntity<Mono<Collection<DTO>>> getManyResponse(Mono<Collection<DTO>> dtoPage, C criteria, Optional<USER> user) {
        return ResponseEntity.status(OperationsStatus.GET_MANY).body(dtoPage);
    }
}