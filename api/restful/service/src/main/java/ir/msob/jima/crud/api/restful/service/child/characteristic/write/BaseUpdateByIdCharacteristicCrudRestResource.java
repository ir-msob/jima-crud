package ir.msob.jima.crud.api.restful.service.child.characteristic.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.child.characteristic.BaseCharacteristicContainer;
import ir.msob.jima.core.commons.child.characteristic.Characteristic;
import ir.msob.jima.core.commons.child.characteristic.CharacteristicCriteria;
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
import ir.msob.jima.crud.service.child.characteristic.BaseCharacteristicCrudService;
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


public interface BaseUpdateByIdCharacteristicCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , CH extends Characteristic<ID>
        , C extends CharacteristicCriteria<ID, CH>
        , S extends BaseCharacteristicCrudService<ID, USER, DTO, CH, C>
        > extends ParentChildCrudRestResource<ID, USER, DTO, CH, C, BaseCharacteristicContainer<ID, CH>, S> {

    Logger log = LoggerFactory.getLogger(BaseUpdateByIdCharacteristicCrudRestResource.class);


    @PutMapping("{parentId}/characteristic/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(element = Elements.CHARACTERISTIC, operation = Operations.UPDATE_BY_ID)
    default ResponseEntity<Mono<DTO>> updateById(@PathVariable("parentId") ID parentId, @PathVariable("id") ID id, @RequestBody @Valid CH dto, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to update characteristic, parentId {}, id {}, dto {}", parentId, id, dto);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_ID).body(getService().updateById(parentId, id, dto, user));
    }

}