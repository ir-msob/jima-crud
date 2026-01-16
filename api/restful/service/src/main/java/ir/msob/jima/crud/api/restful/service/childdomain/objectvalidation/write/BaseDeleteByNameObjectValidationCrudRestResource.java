package ir.msob.jima.crud.api.restful.service.childdomain.objectvalidation.write;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.childdomain.objectvalidation.ObjectValidationAbstract;
import ir.msob.jima.core.commons.childdomain.objectvalidation.ObjectValidationCriteriaAbstract;
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
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

public interface BaseDeleteByNameObjectValidationCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        CD extends ObjectValidationAbstract<ID>,
        CC extends ObjectValidationCriteriaAbstract<ID, CD>,
        DTO extends BaseDto<ID>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentChildCrudRestResource<ID, USER, CD, DTO, CS> {

    Logger logger = LoggerFactory.getLogger(BaseDeleteByNameObjectValidationCrudRestResource.class);

    @DeleteMapping("{parentId}/object-validation/name/{name}")
    @Operation(
            summary = "Delete object validation by name",
            description = "Deletes a specific object validation under the specified parent domain by name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return deleted domain DTO or null", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(element = Elements.OBJECT_VALIDATION, operation = Operations.DELETE_BY_NAME)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> deleteByName(
            @PathVariable("parentId") ID parentId,
            @PathVariable("name") String name,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to delete object-validation by name, parentId {}, name {}", parentId, name);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE_BY_NAME)
                .body(getChildService().<CD, CC>deleteByName(parentId, name, getChildDomainClass(), user));
    }
}
