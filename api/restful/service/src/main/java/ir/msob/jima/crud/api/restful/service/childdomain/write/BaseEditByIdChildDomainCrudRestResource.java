package ir.msob.jima.crud.api.restful.service.childdomain.write;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.conflict.ConflictResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.ParentChildDomainCrudRestResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.write.BaseEditChildDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

/**
 * RESTful API for editing a domain by its ID.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 */
public interface BaseEditByIdChildDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseEditChildDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseEditByIdChildDomainCrudRestResource.class);

    @PatchMapping(value = "{id}", consumes = "application/json-patch+json")
    @Operation(summary = "Edit a domain by its ID", description = "Edits a domain partially using JSON Patch by providing the domain ID and patch object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If the domain was successfully updated, returns the updated domain", content = @Content(schema = @Schema(implementation = BaseChildDto.class))),
            @ApiResponse(responseCode = "400", description = "If validation fails, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "409", description = "If conflict occurs, throws ConflictException", content = @Content(schema = @Schema(implementation = ConflictResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.EDIT_BY_ID)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> editById(@PathVariable("parentDomainId") ID parentId, @PathVariable("id") ID id, @RequestBody String dto, ServerWebExchange serverWebExchange, Principal principal)
            throws BadRequestException, DomainNotFoundException, IOException {
        logger.debug("REST request to edit domain, id {}, dto {}", id, dto);

        USER user = getUser(serverWebExchange, principal);
        Mono<@NonNull DTO> res = this.getService().edit(parentId, id, JsonPatch.fromJson(getService().getObjectMapper().readTree(dto)), user);
        return ResponseEntity.status(OperationsStatus.EDIT_BY_ID).body(res);
    }
}
