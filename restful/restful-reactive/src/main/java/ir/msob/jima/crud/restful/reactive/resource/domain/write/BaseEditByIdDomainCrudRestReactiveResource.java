package ir.msob.jima.crud.restful.reactive.resource.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.crud.reactive.service.domain.write.BaseEditDomainCrudReactiveService;
import ir.msob.jima.crud.restful.reactive.resource.domain.ParentDomainCrudRestReactiveResource;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestResponse;
import ir.msob.jima.platform.api.exception.conflict.ConflictResponse;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.scope.Scope;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
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
public interface BaseEditByIdDomainCrudRestReactiveResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseEditDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseEditByIdDomainCrudRestReactiveResource.class);

    @PatchMapping(value = "{id}", consumes = "application/json-patch+json")
    @Operation(summary = "Edit a domain by its ID", description = "Edits a domain partially using JSON Patch by providing the domain ID and patch object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If the domain was successfully updated, returns the updated domain", content = @Content(schema = @Schema(implementation = BaseDomainDto.class))),
            @ApiResponse(responseCode = "400", description = "If validation fails, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "409", description = "If conflict occurs, throws ConflictException", content = @Content(schema = @Schema(implementation = ConflictResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.EDIT_BY_ID)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> editById(@PathVariable("id") ID id, @RequestBody String dto, ServerWebExchange serverWebExchange, Principal principal)
            throws BadRequestException, DomainNotFoundException, IOException {
        logger.debug("REST request to edit domain, id {}, dto {}", id, dto);

        USER user = getUser(serverWebExchange, principal);
        Mono<@NonNull DTO> res = this.getService().edit(id, JsonPatch.fromJson(getService().getObjectMapper().readTree(dto)), user);
        return ResponseEntity.status(OperationsStatus.EDIT_BY_ID).body(res);
    }
}
