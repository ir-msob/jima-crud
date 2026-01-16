package ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relatedparty.write;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedparty.RelatedPartyAbstract;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

public interface BaseSaveManyRelatedPartyCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        CD extends RelatedPartyAbstract<ID>,
        DTO extends BaseDto<ID>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentRelatedObjectCrudRestResource<ID, String, USER, CD, DTO, CS> {

    Logger logger = LoggerFactory.getLogger(BaseSaveManyRelatedPartyCrudRestResource.class);

    @PostMapping("{parentId}/related-party/" + Operations.SAVE_MANY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return DTOs of saved parties", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(element = Elements.RELATED_PARTY, operation = Operations.SAVE_MANY)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> saveMany(
            @PathVariable("parentId") ID parentId,
            @RequestBody Collection<@Valid CD> childDomains,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {

        logger.debug("REST request to save many related parties, parentId {}, childDomains {}", parentId, childDomains);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.SAVE_MANY)
                .body(getChildService().saveMany(parentId, childDomains, getChildDomainClass(), user));
    }
}
