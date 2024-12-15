package ir.msob.jima.crud.api.restful.service.child.contactmedium.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.child.contactmedium.BaseContactMediumContainer;
import ir.msob.jima.core.commons.child.contactmedium.ContactMediumAbstract;
import ir.msob.jima.core.commons.child.contactmedium.ContactMediumCriteriaAbstract;
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
import ir.msob.jima.crud.service.child.contactmedium.BaseContactMediumCrudService;
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


public interface BaseUpdateByIdContactMediumCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , CM extends ContactMediumAbstract<ID>
        , C extends ContactMediumCriteriaAbstract<ID, CM>
        , S extends BaseContactMediumCrudService<ID, USER, DTO, CM, C>
        > extends ParentChildCrudRestResource<ID, USER, DTO, CM, C, BaseContactMediumContainer<ID, CM>, S> {

    Logger log = LoggerFactory.getLogger(BaseUpdateByIdContactMediumCrudRestResource.class);


    @PutMapping("{parentId}/contact-medium/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.CONTACT_MEDIUM, operation = Operations.UPDATE_BY_ID)
    default ResponseEntity<Mono<DTO>> updateById(@PathVariable("parentId") ID parentId, @PathVariable("id") ID id, @RequestBody @Valid CM dto, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to update contact medium, parentId {}, id {}, dto {}", parentId, id, dto);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_ID).body(getService().updateById(parentId, id, dto, user));
    }

}