package ir.msob.jima.crud.api.restful.service.childdomain.read;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.ParentChildDomainCrudRestResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.read.BaseGetStreamChildDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.io.Serializable;
import java.security.Principal;

/**
 * RESTful API for retrieving a stream of domains based on criteria.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 */
public interface BaseGetStreamChildDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseGetStreamChildDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseGetStreamChildDomainCrudRestResource.class);

    @GetMapping(Operations.GET_STREAM)
    @Operation(summary = "Get stream of domains by criteria", description = "Returns a Flux of domain DTOs matching the given criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a stream of matching domain DTOs", content = @Content(schema = @Schema(implementation = BaseChildDto.class))),
            @ApiResponse(responseCode = "400", description = "If validation fails, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.GET_STREAM)
    default ResponseEntity<@NonNull Flux<@NonNull DTO>> getStream(@PathVariable("parentDomainId") ID parentId, C criteria, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to get stream of domains with criteria: {}", criteria);

        USER user = getUser(serverWebExchange, principal);
        Flux<@NonNull DTO> res = this.getService().getStream(parentId, criteria, user);
        return ResponseEntity.status(OperationsStatus.GET_STREAM).body(res);
    }
}
