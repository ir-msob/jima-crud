package ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedintegration.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.child.relatedobject.relatedintegration.BaseRelatedIntegrationContainer;
import ir.msob.jima.core.commons.child.relatedobject.relatedintegration.RelatedIntegrationAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relatedintegration.RelatedIntegrationCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.element.Elements;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.relatedobject.ParentRelatedObjectCrudRestResource;
import ir.msob.jima.crud.service.child.relatedobject.relatedintegration.BaseRelatedIntegrationCrudService;
import jakarta.validation.Valid;
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


public interface BaseUpdateByRelatedIdRelatedIntegrationCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser

        , RI extends RelatedIntegrationAbstract<ID>
        , C extends RelatedIntegrationCriteriaAbstract<ID, RI>
        , CNT extends BaseRelatedIntegrationContainer<ID, RI>

        , DTO extends BaseDto<ID> & BaseRelatedIntegrationContainer<ID, RI>

        , S extends BaseRelatedIntegrationCrudService<ID, USER, RI, C, CNT, DTO>
        > extends ParentRelatedObjectCrudRestResource<ID, USER, RI, C, CNT, DTO, S> {

    Logger log = LoggerFactory.getLogger(BaseUpdateByRelatedIdRelatedIntegrationCrudRestResource.class);


    @PutMapping("{parentId}/related-integration/related-id/{relatedId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.RELATED_INTEGRATION, operation = Operations.UPDATE_BY_RELATED_ID)
    default ResponseEntity<Mono<DTO>> updateByRelatedId(@PathVariable("parentId") ID parentId, @PathVariable("relatedId") String relatedId, @RequestBody @Valid RI dto, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to update child integration, parentId {}, relatedId {}, dto {}", parentId, relatedId, dto);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_RELATED_ID).body(getChildService().updateByRelatedId(parentId, relatedId, dto, user));
    }

}