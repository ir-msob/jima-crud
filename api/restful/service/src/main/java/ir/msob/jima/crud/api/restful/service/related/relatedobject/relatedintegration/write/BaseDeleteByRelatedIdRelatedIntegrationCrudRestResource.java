package ir.msob.jima.crud.api.restful.service.related.relatedobject.relatedintegration.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.related.relatedobject.relatedintegration.BaseRelatedIntegrationDto;
import ir.msob.jima.core.commons.related.relatedobject.relatedintegration.RelatedIntegrationAbstract;
import ir.msob.jima.core.commons.related.relatedobject.relatedintegration.RelatedIntegrationCriteriaAbstract;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.ParentRelatedCrudRestResource;
import ir.msob.jima.crud.service.related.relatedobject.relatedintegration.BaseRelatedIntegrationCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;


public interface BaseDeleteByRelatedIdRelatedIntegrationCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RI extends RelatedIntegrationAbstract<ID>
        , C extends RelatedIntegrationCriteriaAbstract<ID, RI>
        , S extends BaseRelatedIntegrationCrudService<ID, USER, DTO, RI, C>
        > extends ParentRelatedCrudRestResource<ID, USER, DTO, RI, C, BaseRelatedIntegrationDto<ID, RI>, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByRelatedIdRelatedIntegrationCrudRestResource.class);


    @DeleteMapping("{parentId}/related-integration/related-id/{relatedId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(Operations.DELETE_BY_RELATED_ID)
    default ResponseEntity<Mono<DTO>> deleteByRelatedId(@PathVariable("parentId") ID parentId, @PathVariable("relatedId") String relatedId, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete related integration by relatedId, parentId {}, id {}", parentId, relatedId);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE_BY_RELATED_ID).body(getService().deleteByRelatedId(parentId, relatedId, user));

    }

}