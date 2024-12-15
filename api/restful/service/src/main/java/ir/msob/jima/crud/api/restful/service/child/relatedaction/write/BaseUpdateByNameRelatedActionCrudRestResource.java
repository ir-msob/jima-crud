package ir.msob.jima.crud.api.restful.service.child.relatedaction.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.child.relatedaction.BaseRelatedActionContainer;
import ir.msob.jima.core.commons.child.relatedaction.RelatedActionAbstract;
import ir.msob.jima.core.commons.child.relatedaction.RelatedActionCriteriaAbstract;
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
import ir.msob.jima.crud.service.child.relatedaction.BaseRelatedActionCrudService;
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


public interface BaseUpdateByNameRelatedActionCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , OV extends RelatedActionAbstract<ID>
        , C extends RelatedActionCriteriaAbstract<ID, OV>
        , S extends BaseRelatedActionCrudService<ID, USER, DTO, OV, C>
        > extends ParentChildCrudRestResource<ID, USER, DTO, OV, C, BaseRelatedActionContainer<ID, OV>, S> {

    Logger log = LoggerFactory.getLogger(BaseUpdateByNameRelatedActionCrudRestResource.class);


    @PutMapping("{parentId}/related-action/name/{name}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.RELATED_ACTION, operation = Operations.UPDATE_BY_NAME)
    default ResponseEntity<Mono<DTO>> updateByName(@PathVariable("parentId") ID parentId, @PathVariable("name") String name, @RequestBody @Valid OV dto, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to update child action, parentId {}, name {}, dto {}", parentId, name, dto);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_NAME).body(getService().updateByName(parentId, name, dto, user));
    }

}