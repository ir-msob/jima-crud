package ir.msob.jima.crud.restful.reactive.resource.domain.read;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.crud.reactive.service.domain.read.BaseCountAllDomainCrudReactiveService;
import ir.msob.jima.crud.restful.reactive.resource.domain.ParentDomainCrudRestReactiveResource;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestResponse;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.scope.Scope;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

/**
 * RESTful API for counting all domains.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 */
public interface BaseCountAllDomainCrudRestReactiveResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseCountAllDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseCountAllDomainCrudRestReactiveResource.class);

    @GetMapping(Operations.COUNT_ALL)
    @Operation(summary = "Count all domains", description = "Returns the count of all domain records")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If domain(s) exist returns the count", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "If validation fails, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.COUNT_ALL)
    default ResponseEntity<@NonNull Mono<@NonNull Long>> countAll(ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to count all domains");

        USER user = getUser(serverWebExchange, principal);
        Mono<@NonNull Long> res = this.getService().countAll(user);
        return ResponseEntity.status(OperationsStatus.COUNT_ALL).body(res);
    }
}
