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

public interface BaseSaveEmbeddedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        ED extends BaseEmbeddedDomain<ID>,
        DTO extends BaseDto<ID>,
        EDS extends BaseEmbeddedDomainCrudService<ID, USER, DTO>
        > extends ParentEmbeddedDomainCrudRestResource<ID, USER, ED, DTO, EDS> {

    Logger logger = LoggerFactory.getLogger(BaseSaveEmbeddedDomainCrudRestResource.class);

    @PostMapping
    @Operation(
            summary = "Save a characteristic",
            description = "Saves a new characteristic for the given parent domain"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Characteristic saved successfully", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Parent domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(operation = Operations.SAVE)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> save(
            @PathVariable("parentId") ID parentId,
            @RequestBody @Valid ED embeddedDomain,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to save characteristic, parentId {}, embeddedDomain {}", parentId, embeddedDomain);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.SAVE)
                .body(getEmbeddedDomainService().save(parentId, embeddedDomain, getEmbeddedDomainClass(), user));
    }
}
