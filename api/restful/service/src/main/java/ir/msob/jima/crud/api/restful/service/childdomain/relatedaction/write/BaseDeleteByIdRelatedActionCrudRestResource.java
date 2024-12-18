package ir.msob.jima.crud.api.restful.service.childdomain.relatedaction.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.childdomain.relatedaction.RelatedActionAbstract;
import ir.msob.jima.core.commons.childdomain.relatedaction.RelatedActionCriteriaAbstract;
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
import ir.msob.jima.crud.api.restful.service.childdomain.ParentChildCrudRestResource;
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


public interface BaseDeleteByIdRelatedActionCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends RelatedActionAbstract<ID>
        , CC extends RelatedActionCriteriaAbstract<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentChildCrudRestResource<ID, USER, CD, DTO, CS> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByIdRelatedActionCrudRestResource.class);


    @DeleteMapping("{parentId}/related-action/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.RELATED_ACTION, operation = Operations.DELETE_BY_ID)
    default ResponseEntity<Mono<DTO>> deleteById(@PathVariable("parentId") ID parentId, @PathVariable("id") ID id, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to delete childdomain-action by id, parentId {}, id {}", parentId, id);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.DELETE_BY_ID).body(getChildService().<CD, CC>deleteById(parentId, id, getChildDomainClass(), user));

    }

}