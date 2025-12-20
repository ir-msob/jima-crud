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
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.ParentChildCrudRestResource;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

public interface BaseDeleteManyObjectValidationCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        CD extends ObjectValidationAbstract<ID>,
        CC extends ObjectValidationCriteriaAbstract<ID, CD>,
        DTO extends BaseDto<ID>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentChildCrudRestResource<ID, USER, CD, DTO, CS> {

    Logger log = LoggerFactory.getLogger(BaseDeleteManyObjectValidationCrudRestResource.class);

    @DeleteMapping("{parentId}/object-validation/" + Operations.DELETE_MANY)
    @Operation(
            summary = "Delete multiple object validations",
            description = "Deletes multiple object validations under the specified parent domain using criteria"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return deleted domain DTOs or null", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(element = Elements.OBJECT_VALIDATION, operation = Operations.DELETE_MANY)
    default ResponseEntity<Mono<DTO>> deleteMany(
            @PathVariable("parentId") ID parentId,
            CC childCriteria,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete object-validations by criteria, parentId {}, childCriteria {}", parentId, childCriteria);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE_MANY)
                .body(getChildService().deleteMany(parentId, childCriteria, getChildDomainClass(), user));
    }
}
