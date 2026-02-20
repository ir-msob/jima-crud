package ir.msob.jima.crud.api.restful.service.childdomain.write;

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
import ir.msob.jima.crud.service.childdomain.write.BaseSaveChildDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

/**
 * RESTful API for saving a domain based on a given DTO.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 */
public interface BaseSaveChildDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseSaveChildDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentChildDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseSaveChildDomainCrudRestResource.class);

    @PostMapping
    @Operation(summary = "Save a new domain", description = "Creates a new domain using the provided DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If the domain was successfully created, returns the saved DTO", content = @Content(schema = @Schema(implementation = BaseChildDto.class))),
            @ApiResponse(responseCode = "400", description = "If validation fails, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "409", description = "If conflict occurs, throws ConflictException", content = @Content(schema = @Schema(implementation = ConflictResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.SAVE)
    default ResponseEntity<@NonNull Mono<@NonNull DTO>> save(@PathVariable("parentDomainId") ID parentId, @RequestBody DTO dto, ServerWebExchange serverWebExchange, Principal principal)
            throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to save new domain, dto: {}", dto);

        USER user = getUser(serverWebExchange, principal);
        Mono<@NonNull DTO> res = this.getService().save(parentId, dto, user);
        return ResponseEntity.status(OperationsStatus.SAVE).body(res);
    }
}
