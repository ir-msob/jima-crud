package ir.msob.jima.crud.api.restful.service.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.conflict.ConflictResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.domain.ParentDomainCrudRestResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.write.BaseEditManyDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * RESTful API for editing multiple domains based on a given criteria.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 */
public interface BaseEditManyDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseEditManyDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {

    Logger log = LoggerFactory.getLogger(BaseEditManyDomainCrudRestResource.class);

    @PatchMapping(value = Operations.EDIT_MANY, consumes = "application/json-patch+json")
    @Operation(summary = "Edit multiple domains based on criteria", description = "Edits multiple domains partially using JSON Patch based on given criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If domains were successfully edited, returns the updated domains", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "If validation fails, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "409", description = "If conflict occurs, throws ConflictException", content = @Content(schema = @Schema(implementation = ConflictResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.EDIT_MANY)
    default ResponseEntity<@NonNull Mono<@NonNull Collection<DTO>>> editMany(@RequestBody String dto, C criteria, ServerWebExchange serverWebExchange, Principal principal)
            throws BadRequestException, DomainNotFoundException, IOException {
        log.debug("REST request to edit many domains, dto: {}, criteria: {}", dto, criteria);

        USER user = getUser(serverWebExchange, principal);
        Mono<@NonNull Collection<DTO>> res = this.getService().editMany(criteria, JsonPatch.fromJson(getService().getObjectMapper().readTree(dto)), user);
        return ResponseEntity.status(OperationsStatus.EDIT_MANY).body(res);
    }
}
