package ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relatedprocess.write;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedprocess.RelatedProcessAbstract;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedprocess.RelatedProcessCriteriaAbstract;
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

public interface BaseUpdateByRelatedIdRelatedProcessCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        CD extends RelatedProcessAbstract<ID>,
        CC extends RelatedProcessCriteriaAbstract<ID, CD>,
        DTO extends BaseDto<ID>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentRelatedObjectCrudRestResource<ID, String, USER, CD, DTO, CS> {

    Logger logger = LoggerFactory.getLogger(BaseUpdateByRelatedIdRelatedProcessCrudRestResource.class);

    @Operation(summary = "Update related process by related ID", description = "Update a related process child domain by its relatedId for a given parent ID")
    @PutMapping("{parentId}/related-process/related-id/{relatedId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return DTO of updated process or null", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(element = Elements.RELATED_PROCESS, operation = Operations.UPDATE_BY_RELATED_ID)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> updateByRelatedId(
            @PathVariable("parentId") ID parentId,
            @PathVariable("relatedId") String relatedId,
            @RequestBody @Valid CD childDomain,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to update childdomain process, parentId {}, relatedId {}, childDomain {}", parentId, relatedId, childDomain);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_RELATED_ID)
                .body(getChildService().<String, CD, CC>updateByRelatedId(parentId, relatedId, childDomain, getChildDomainClass(), user));
    }
}
