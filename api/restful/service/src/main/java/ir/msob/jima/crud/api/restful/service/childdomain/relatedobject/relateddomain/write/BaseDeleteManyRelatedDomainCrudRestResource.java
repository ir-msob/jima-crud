package ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relateddomain.write;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.childdomain.relatedobject.relateddomain.RelatedDomainAbstract;
import ir.msob.jima.core.commons.childdomain.relatedobject.relateddomain.RelatedDomainCriteriaAbstract;
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
import ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.ParentRelatedObjectCrudRestResource;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

public interface BaseDeleteManyRelatedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        CD extends RelatedDomainAbstract<ID>,
        CC extends RelatedDomainCriteriaAbstract<ID, CD>,
        DTO extends BaseDto<ID>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentRelatedObjectCrudRestResource<ID, ID, USER, CD, DTO, CS> {

    Logger logger = LoggerFactory.getLogger(BaseDeleteManyRelatedDomainCrudRestResource.class);

    @DeleteMapping("{parentId}/related-domain/" + Operations.DELETE_MANY)
    @Operation(
            summary = "Delete many related domains",
            description = "Deletes multiple related domains under the specified parent domain based on given criteria"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return DTOs of deleted domains or null", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(element = Elements.RELATED_DOMAIN, operation = Operations.DELETE_MANY)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> deleteMany(
            @PathVariable("parentId") ID parentId,
            CC childCriteria,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {

        logger.debug("REST request to delete related domains by criteria, parentId {}, criteria {}", parentId, childCriteria);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE_MANY)
                .body(getChildService().deleteMany(parentId, childCriteria, getChildDomainClass(), user));
    }

}
