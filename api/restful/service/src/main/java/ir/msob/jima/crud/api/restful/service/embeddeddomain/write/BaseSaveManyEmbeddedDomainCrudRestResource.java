package ir.msob.jima.crud.api.restful.service.embeddeddomain.write;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.embeddeddomain.BaseEmbeddedDomain;
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
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

public interface BaseSaveManyEmbeddedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        ED extends BaseEmbeddedDomain<ID>,
        DTO extends BaseDto<ID>,
        EDS extends BaseEmbeddedDomainCrudService<ID, USER, DTO>
        > extends ParentEmbeddedDomainCrudRestResource<ID, USER, ED, DTO, EDS> {

    Logger logger = LoggerFactory.getLogger(BaseSaveManyEmbeddedDomainCrudRestResource.class);

    @PostMapping(Operations.SAVE_MANY)
    @Operation(
            summary = "Save multiple embedded domains",
            description = "Saves a collection of embedded domains for the given parent domain"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Characteristics saved successfully", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Parent domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(operation = Operations.SAVE_MANY)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> saveMany(
            @PathVariable("parentId") ID parentId,
            @RequestBody Collection<@Valid ED> embeddedDomains,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to save many embedded domains, parentId {}, embeddedDomains {}", parentId, embeddedDomains);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.SAVE_MANY)
                .body(getEmbeddedDomainService().saveMany(parentId, embeddedDomains, getEmbeddedDomainClass(), user));
    }
}
