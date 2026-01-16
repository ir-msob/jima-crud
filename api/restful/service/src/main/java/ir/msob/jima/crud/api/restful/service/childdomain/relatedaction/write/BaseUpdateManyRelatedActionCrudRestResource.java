package ir.msob.jima.crud.api.restful.service.childdomain.relatedaction.write;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.childdomain.relatedaction.RelatedActionAbstract;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.element.Elements;
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
import ir.msob.jima.crud.api.restful.service.childdomain.ParentChildCrudRestResource;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

public interface BaseUpdateManyRelatedActionCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        CD extends RelatedActionAbstract<ID>,
        DTO extends BaseDto<ID>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentChildCrudRestResource<ID, USER, CD, DTO, CS> {

    Logger logger = LoggerFactory.getLogger(BaseUpdateManyRelatedActionCrudRestResource.class);

    @PutMapping("{parentId}/related-action/" + Operations.UPDATE_MANY)
    @Operation(
            summary = "Update many related actions",
            description = "Updates multiple related actions under the specified parent domain"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return DTO of updated domains or null", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(element = Elements.RELATED_ACTION, operation = Operations.UPDATE_MANY)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> updateMany(
            @PathVariable("parentId") ID parentId,
            @RequestBody Collection<@Valid CD> childDomains,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {

        logger.debug(
                "REST request to update many related actions, parentId {}, childDomains {}",
                parentId, childDomains
        );

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_MANY)
                .body(getChildService().updateMany(parentId, childDomains, getChildDomainClass(), user));
    }

}
