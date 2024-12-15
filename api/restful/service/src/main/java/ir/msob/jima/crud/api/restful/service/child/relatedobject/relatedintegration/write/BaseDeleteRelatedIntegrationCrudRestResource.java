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
import ir.msob.jima.crud.api.restful.service.child.ParentChildCrudRestResource;
import ir.msob.jima.crud.service.child.relatedobject.relatedintegration.BaseRelatedIntegrationCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;


public interface BaseDeleteRelatedIntegrationCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RI extends RelatedIntegrationAbstract<ID>
        , C extends RelatedIntegrationCriteriaAbstract<ID, RI>
        , S extends BaseRelatedIntegrationCrudService<ID, USER, DTO, RI, C>
        > extends ParentChildCrudRestResource<ID, USER, DTO, RI, C, BaseRelatedIntegrationContainer<ID, RI>, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteRelatedIntegrationCrudRestResource.class);


    @DeleteMapping("{parentId}/related-integration")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.RELATED_INTEGRATION, operation = Operations.DELETE)
    default ResponseEntity<Mono<DTO>> delete(@PathVariable("parentId") ID parentId, C criteria, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete child integration by criteria, parentId {}, id {}", parentId, criteria);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE).body(getService().delete(parentId, criteria, user));

    }

}