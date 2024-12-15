package ir.msob.jima.crud.api.restful.service.child.relatedobject.relateddomain.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.child.relatedobject.relateddomain.BaseRelatedDomainContainer;
import ir.msob.jima.core.commons.child.relatedobject.relateddomain.RelatedDomainAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relateddomain.RelatedDomainCriteriaAbstract;
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
import ir.msob.jima.crud.service.child.relatedobject.relateddomain.BaseRelatedDomainCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;


public interface BaseDeleteByIdRelatedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RD extends RelatedDomainAbstract<ID>
        , C extends RelatedDomainCriteriaAbstract<ID, RD>
        , S extends BaseRelatedDomainCrudService<ID, USER, DTO, RD, C>
        > extends ParentRelatedObjectCrudRestResource<ID, USER, DTO, RD, C, BaseRelatedDomainContainer<ID, RD>, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByIdRelatedDomainCrudRestResource.class);


    @DeleteMapping("{parentId}/related-domain/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.RELATED_DOMAIN, operation = Operations.DELETE_BY_ID)
    default ResponseEntity<Mono<DTO>> deleteById(@PathVariable("parentId") ID parentId, @PathVariable("id") ID id, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete child domain by id, parentId {}, id {}", parentId, id);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE_BY_ID).body(getService().deleteById(parentId, id, user));

    }

}