package ir.msob.jima.crud.api.restful.service.domain.write;

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
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.domain.ParentDomainCrudRestResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.write.BaseSaveManyDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * RESTful API for saving multiple domains based on a given DTO.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 */
public interface BaseSaveManyDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseSaveManyDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseSaveManyDomainCrudRestResource.class);

    @PostMapping(Operations.SAVE_MANY)
    @Operation(summary = "Save multiple domains", description = "Creates multiple new domains using the provided DTOs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If the domains were successfully created, returns the saved DTOs", content = @Content(schema = @Schema(implementation = BaseDto.class))),
            @ApiResponse(responseCode = "400", description = "If validation fails, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "409", description = "If conflict occurs, throws ConflictException", content = @Content(schema = @Schema(implementation = ConflictResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.SAVE_MANY)
    default ResponseEntity<@NonNull Mono<@NonNull Collection<DTO>>> saveMany(@RequestBody Collection<DTO> dtos, ServerWebExchange serverWebExchange, Principal principal)
            throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to save many new domains, dtos: {}", dtos);

        USER user = getUser(serverWebExchange, principal);
        Mono<@NonNull Collection<DTO>> res = this.getService().saveMany(dtos, user);
        return ResponseEntity.status(OperationsStatus.SAVE_MANY).body(res);
    }
}
