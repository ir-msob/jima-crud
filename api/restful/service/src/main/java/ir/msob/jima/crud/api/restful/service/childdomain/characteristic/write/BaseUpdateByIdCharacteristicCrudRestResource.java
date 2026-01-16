package ir.msob.jima.crud.api.restful.service.childdomain.characteristic.write;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.childdomain.characteristic.Characteristic;
import ir.msob.jima.core.commons.childdomain.characteristic.CharacteristicCriteria;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.element.Elements;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.ParentChildCrudRestResource;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

public interface BaseUpdateByIdCharacteristicCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        CD extends Characteristic<ID>,
        CC extends CharacteristicCriteria<ID, CD>,
        DTO extends BaseDto<ID>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends ParentChildCrudRestResource<ID, USER, CD, DTO, CS> {

    Logger logger = LoggerFactory.getLogger(BaseUpdateByIdCharacteristicCrudRestResource.class);

    @PutMapping("{parentId}/characteristic/{id}")
    @Operation(
            summary = "Update characteristic by ID",
            description = "Updates a characteristic for the given parent domain by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Characteristic updated successfully", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Domain not found", content = @Content(schema = @Schema(implementation = DomainNotFoundException.class)))
    })
    @MethodStats
    @Scope(element = Elements.CHARACTERISTIC, operation = Operations.UPDATE_BY_ID)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> updateById(
            @PathVariable("parentId") ID parentId,
            @PathVariable("id") ID id,
            @RequestBody @Valid CD childDomain,
            ServerWebExchange serverWebExchange,
            Principal principal
    ) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to update characteristic, parentId {}, id {}, childDomain {}", parentId, id, childDomain);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_ID)
                .body(getChildService().<CD, CC>updateById(parentId, id, childDomain, getChildDomainClass(), user));
    }
}
