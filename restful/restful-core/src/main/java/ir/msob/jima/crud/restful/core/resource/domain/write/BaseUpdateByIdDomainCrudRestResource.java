package ir.msob.jima.crud.restful.core.resource.domain.write;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.crud.core.service.domain.write.BaseUpdateDomainCrudService;
import ir.msob.jima.crud.restful.core.resource.domain.ParentDomainCrudRestResource;
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
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.scope.Scope;
import ir.msob.jima.platform.api.security.BaseUser;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.security.Principal;

/**
 * RESTful API for updating a domain by its ID.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 */
public interface BaseUpdateByIdDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>,
        S extends BaseUpdateDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseUpdateByIdDomainCrudRestResource.class);

    @PutMapping("{id}")
    @Operation(summary = "Update domain by ID", description = "Updates an existing domain using the provided DTO and ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated domain", content = @Content(schema = @Schema(implementation = BaseDomainDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict occurred, throws ConflictException", content = @Content(schema = @Schema(implementation = ConflictResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.UPDATE_BY_ID)
    default ResponseEntity<@NonNull DTO> updateById(@PathVariable("id") ID id, @RequestBody DTO dto, HttpServletRequest request, Principal principal)
            throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to update domain, id {}, dto: {}", id, dto);

        USER user = getUser(request, principal);
        DTO res = this.getService().update(id, dto, user);
        return ResponseEntity.status(OperationsStatus.UPDATE_BY_ID).body(res);
    }
}
