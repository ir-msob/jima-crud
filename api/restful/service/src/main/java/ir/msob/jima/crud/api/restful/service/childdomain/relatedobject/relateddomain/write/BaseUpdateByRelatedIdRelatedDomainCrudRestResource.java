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
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.ParentRelatedObjectCrudRestResource;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

public interface BaseUpdateByRelatedIdRelatedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        CD extends RelatedDomainAbstract<ID>,
        CC extends RelatedDomainCriteriaAbstract<ID, CD>,
        DTO extends BaseDto<ID>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentRelatedObjectCrudRestResource<ID, ID, USER, CD, DTO, CS> {

    Logger log = LoggerFactory.getLogger(BaseUpdateByRelatedIdRelatedDomainCrudRestResource.class);

    @PutMapping("{parentId}/related-domain/related-id/{relatedId}")
    @Operation(
            summary = "Update related domain by related ID",
            description = "Updates a related domain under the specified parent domain using the related ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return DTO of updated domain or null", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(element = Elements.RELATED_DOMAIN, operation = Operations.UPDATE_BY_RELATED_ID)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> updateByRelatedId(
            @PathVariable("parentId") ID parentId,
            @PathVariable("relatedId") ID relatedId,
            @RequestBody @Valid CD childDomain,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {

        log.debug("REST request to update related domain by relatedId, parentId {}, relatedId {}, childDomain {}", parentId, relatedId, childDomain);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_RELATED_ID)
                .body(getChildService().<ID, CD, CC>updateByRelatedId(parentId, relatedId, childDomain, getChildDomainClass(), user));
    }

}
