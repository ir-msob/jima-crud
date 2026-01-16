package ir.msob.jima.crud.api.restful.service.childdomain.contactmedium.write;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.childdomain.contactmedium.ContactMediumAbstract;
import ir.msob.jima.core.commons.childdomain.contactmedium.ContactMediumCriteriaAbstract;
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

public interface BaseUpdateByNameContactMediumCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        CD extends ContactMediumAbstract<ID>,
        CC extends ContactMediumCriteriaAbstract<ID, CD>,
        DTO extends BaseDto<ID>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentChildCrudRestResource<ID, USER, CD, DTO, CS> {

    Logger logger = LoggerFactory.getLogger(BaseUpdateByNameContactMediumCrudRestResource.class);

    @PutMapping("{parentId}/contact-medium/name/{name}")
    @Operation(
            summary = "Update contact medium by name",
            description = "Updates a specific contact medium under the specified parent domain using its name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return updated domain DTO or null", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(element = Elements.CONTACT_MEDIUM, operation = Operations.UPDATE_BY_NAME)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> updateByName(
            @PathVariable("parentId") ID parentId,
            @PathVariable("name") String name,
            @RequestBody @Valid CD childDomain,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to update contact medium, parentId {}, name {}, childDomain {}", parentId, name, childDomain);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_NAME)
                .body(getChildService().<CD, CC>updateByName(parentId, name, childDomain, getChildDomainClass(), user));
    }
}
