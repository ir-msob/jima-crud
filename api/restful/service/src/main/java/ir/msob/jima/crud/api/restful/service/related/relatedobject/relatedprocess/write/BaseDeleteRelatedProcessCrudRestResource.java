package ir.msob.jima.crud.api.restful.service.related.relatedobject.relatedprocess.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.related.relatedobject.relatedprocess.BaseRelatedProcessDto;
import ir.msob.jima.core.commons.related.relatedobject.relatedprocess.RelatedProcessAbstract;
import ir.msob.jima.core.commons.related.relatedobject.relatedprocess.RelatedProcessCriteriaAbstract;
import ir.msob.jima.core.commons.scope.Elements;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.ParentRelatedCrudRestResource;
import ir.msob.jima.crud.service.related.relatedobject.relatedprocess.BaseRelatedProcessCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;


public interface BaseDeleteRelatedProcessCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RP extends RelatedProcessAbstract<ID>
        , C extends RelatedProcessCriteriaAbstract<ID, RP>
        , S extends BaseRelatedProcessCrudService<ID, USER, DTO, RP, C>
        > extends ParentRelatedCrudRestResource<ID, USER, DTO, RP, C, BaseRelatedProcessDto<ID, RP>, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteRelatedProcessCrudRestResource.class);


    @DeleteMapping("{parentId}/related-process")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.RELATED_PROCESS, operation = Operations.DELETE)
    default ResponseEntity<Mono<DTO>> delete(@PathVariable("parentId") ID parentId, C criteria, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete related process by criteria, parentId {}, id {}", parentId, criteria);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE).body(getService().delete(parentId, criteria, user));

    }

}