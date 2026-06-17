package ir.msob.jima.crud.restful.core.resource.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.crud.core.service.domain.write.BaseEditManyDomainCrudService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>,
        S extends BaseEditManyDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseEditManyDomainCrudRestResource.class);

    @PatchMapping(value = Operations.EDIT_MANY, consumes = "application/json-patch+json")
    @Operation(summary = "Edit multiple domains based on criteria", description = "Edits multiple domains partially using JSON Patch based on given criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "If domains were successfully edited, returns the updated domains", content = @Content(schema = @Schema(implementation = BaseDomainDto.class))),
            @ApiResponse(responseCode = "400", description = "If validation fails, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "409", description = "If conflict occurs, throws ConflictException", content = @Content(schema = @Schema(implementation = ConflictResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.EDIT_MANY)
    default ResponseEntity<@NonNull Collection<DTO>> editMany(@RequestBody String dto, C criteria, HttpServletRequest request, Principal principal)
            throws BadRequestException, DomainNotFoundException, IOException {
        logger.debug("REST request to edit many domains, dto: {}, criteria: {}", dto, criteria);

        USER user = getUser(request, principal);
        Collection<DTO> res = this.getService().editMany(criteria, JsonPatch.fromJson(getService().getObjectMapper().readTree(dto)), user);
        return ResponseEntity.status(OperationsStatus.EDIT_MANY).body(res);
    }
}
