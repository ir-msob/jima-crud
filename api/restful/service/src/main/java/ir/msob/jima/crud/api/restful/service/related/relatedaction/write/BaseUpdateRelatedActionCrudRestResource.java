package ir.msob.jima.crud.api.restful.service.related.relatedaction.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.related.relatedaction.BaseRelatedActionDto;
import ir.msob.jima.core.commons.related.relatedaction.RelatedActionAbstract;
import ir.msob.jima.core.commons.related.relatedaction.RelatedActionCriteriaAbstract;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.ParentRelatedCrudRestResource;
import ir.msob.jima.crud.service.related.relatedaction.BaseRelatedActionCrudService;
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


public interface BaseUpdateRelatedActionCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , OV extends RelatedActionAbstract<ID>
        , C extends RelatedActionCriteriaAbstract<ID, OV>
        , RDTO extends BaseRelatedActionDto<ID, OV>
        , S extends BaseRelatedActionCrudService<ID, USER, DTO, OV, C, RDTO>
        > extends ParentRelatedCrudRestResource<ID, USER, DTO, OV, C, RDTO, S> {

    Logger log = LoggerFactory.getLogger(BaseUpdateRelatedActionCrudRestResource.class);


    @PutMapping("{parentId}/related-action")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(Operations.UPDATE)
    default ResponseEntity<Mono<DTO>> update(@PathVariable("parentId") ID parentId, @RequestBody @Valid OV dto, C criteria, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to update related action, parentId {}, dto {}, criteria {}", parentId, dto, criteria);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE).body(getService().update(parentId, dto, criteria, user));
    }

}