package ir.msob.jima.crud.restful.core.resource.domain.read;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ir.msob.jima.crud.core.service.domain.read.BaseGetManyDomainCrudService;
import ir.msob.jima.crud.restful.core.resource.domain.ParentDomainCrudRestResource;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestResponse;
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
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * RESTful API for retrieving multiple domains based on criteria.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 */
public interface BaseGetManyDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>,
        S extends BaseGetManyDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseGetManyDomainCrudRestResource.class);

    @GetMapping(Operations.GET_MANY)
    @Operation(summary = "Get multiple domains by criteria", description = "Returns a collection of domain DTOs matching the given criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the collection of matching domain DTOs", content = @Content(schema = @Schema(implementation = BaseDomainDto.class))),
            @ApiResponse(responseCode = "400", description = "If validation fails, throws BadRequestException", content = @Content(schema = @Schema(implementation = BadRequestResponse.class)))
    })
    @MethodStats
    @Scope(operation = Operations.GET_MANY)
    default ResponseEntity<@NonNull Collection<DTO>> getMany(C criteria, HttpServletRequest request, Principal principal) throws BadRequestException, DomainNotFoundException {
        logger.debug("REST request to get many domains with criteria: {}", criteria);

        USER user = getUser(request, principal);
        Collection<DTO> res = this.getService().getMany(criteria, user);
        return ResponseEntity.status(OperationsStatus.GET_MANY).body(res);
    }
}
