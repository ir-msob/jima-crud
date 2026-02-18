package ir.msob.jima.crud.api.restful.service.embeddeddomain.write;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.core.commons.embeddeddomain.criteria.BaseEmbeddedCriteria;
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
import ir.msob.jima.crud.api.restful.service.embeddeddomain.ParentEmbeddedDomainCrudRestResource;
import ir.msob.jima.crud.service.embeddeddomain.BaseEmbeddedDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

public interface BaseDeleteEmbeddedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        ED extends BaseEmbeddedDomain<ID>,
        EC extends BaseEmbeddedCriteria<ID, ED>,
        DTO extends BaseDto<ID>,
        EDS extends BaseEmbeddedDomainCrudService<ID, USER, DTO>
        > extends ParentEmbeddedDomainCrudRestResource<ID, USER, ED, DTO, EDS> {

    Logger logger = LoggerFactory.getLogger(BaseDeleteEmbeddedDomainCrudRestResource.class);

    @DeleteMapping
    @Operation(summary = "Delete characteristic(s) by criteria",
            description = "Deletes characteristic(s) of a parent domain based on provided criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return id of deleted domain or null"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found",
                    content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(operation = Operations.DELETE)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> delete(
            @PathVariable("parentId") ID parentId,
            EC childCriteria,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to delete characteristic by childCriteria, parentId {}, criteria {}", parentId, childCriteria);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE)
                .body(getEmbeddedDomainService().delete(parentId, childCriteria, getEmbeddedDomainClass(), user));
    }
}
