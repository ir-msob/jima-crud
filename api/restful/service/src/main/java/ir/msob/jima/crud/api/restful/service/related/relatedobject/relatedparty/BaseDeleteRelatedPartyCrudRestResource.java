package ir.msob.jima.crud.api.restful.service.related.relatedobject.relatedparty;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.related.relatedobject.relatedparty.BaseRelatedPartyDto;
import ir.msob.jima.core.commons.related.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.core.commons.related.relatedobject.relatedparty.RelatedPartyCriteriaAbstract;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.ParentRelatedCrudRestResource;
import ir.msob.jima.crud.service.related.relatedobject.relatedparty.BaseRelatedPartyCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;


public interface BaseDeleteRelatedPartyCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RP extends RelatedPartyAbstract<ID>
        , C extends RelatedPartyCriteriaAbstract<ID, RP>
        , S extends BaseRelatedPartyCrudService<ID, USER, DTO, RP, C>
        > extends ParentRelatedCrudRestResource<ID, USER, DTO, RP, C, BaseRelatedPartyDto<ID, RP>, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteRelatedPartyCrudRestResource.class);


    @DeleteMapping("{parentId}/related-party")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(Operations.DELETE)
    default ResponseEntity<Mono<DTO>> delete(@PathVariable("parentId") ID parentId, C criteria, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete related party by criteria, parentId {}, id {}", parentId, criteria);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE).body(getService().delete(parentId, criteria, user));

    }

}