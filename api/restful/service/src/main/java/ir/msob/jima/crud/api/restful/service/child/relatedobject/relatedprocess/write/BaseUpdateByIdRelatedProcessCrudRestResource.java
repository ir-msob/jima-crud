package ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedprocess.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.child.relatedobject.relatedprocess.BaseRelatedProcessDto;
import ir.msob.jima.core.commons.child.relatedobject.relatedprocess.RelatedProcessAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relatedprocess.RelatedProcessCriteriaAbstract;
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
import ir.msob.jima.crud.service.child.relatedobject.relatedprocess.BaseRelatedProcessCrudService;
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


public interface BaseUpdateByIdRelatedProcessCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RP extends RelatedProcessAbstract<ID>
        , C extends RelatedProcessCriteriaAbstract<ID, RP>
        , S extends BaseRelatedProcessCrudService<ID, USER, DTO, RP, C>
        > extends ParentChildCrudRestResource<ID, USER, DTO, RP, C, BaseRelatedProcessDto<ID, RP>, S> {

    Logger log = LoggerFactory.getLogger(BaseUpdateByIdRelatedProcessCrudRestResource.class);


    @PutMapping("{parentId}/related-process/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.RELATED_PROCESS, operation = Operations.UPDATE_BY_ID)
    default ResponseEntity<Mono<DTO>> updateById(@PathVariable("parentId") ID parentId, @PathVariable("id") ID id, @RequestBody @Valid RP dto, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to update child process, parentId {}, id {}, dto {}", parentId, id, dto);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_ID).body(getService().updateById(parentId, id, dto, user));
    }

}