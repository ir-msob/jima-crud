package ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relatedprocess.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedprocess.RelatedProcessAbstract;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedprocess.RelatedProcessCriteriaAbstract;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;


public interface BaseDeleteByRelatedIdRelatedProcessCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends RelatedProcessAbstract<ID>
        , CC extends RelatedProcessCriteriaAbstract<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>> extends
        ParentRelatedObjectCrudRestResource<ID, String, USER, CD, CC, DTO, CS> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByRelatedIdRelatedProcessCrudRestResource.class);


    @DeleteMapping("{parentId}/related-process/related-id/{relatedId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.RELATED_PROCESS, operation = Operations.DELETE_BY_RELATED_ID)
    default ResponseEntity<Mono<DTO>> deleteByRelatedId(@PathVariable("parentId") ID parentId, @PathVariable("relatedId") String relatedId, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete childdomain process by relatedId, parentId {}, id {}", parentId, relatedId);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE_BY_RELATED_ID).body(getChildService().<String, CD, CC>deleteByRelatedId(parentId, relatedId, getChildDomainClass(), user));

    }

}