package ir.msob.jima.crud.api.restful.service.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.domain.ParentDomainCrudRestResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.write.BaseEditDomainCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

/**
 * This interface provides a RESTful API for editing a domain based on a given criteria.
 * It extends the ParentDomainCrudRestResource interface and provides a default implementation for the edit method.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <Q>    the type of the query
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 * @author Yaqub Abdi
 */
public interface BaseEditDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseEditDomainCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentDomainCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseEditDomainCrudRestResource.class);

    /**
     * This method provides a RESTful API for editing a domain based on a given criteria.
     * It validates the operation, retrieves the user, and then calls the service to edit the domain.
     * It returns a ResponseEntity with the edited DTO.
     *
     * @param dto               the JsonPatch object containing the changes to apply to the domain
     * @param criteria          the criteria to edit the domain
     * @param serverWebExchange the ServerWebExchange object
     * @param principal         the Principal object
     * @return a ResponseEntity with the edited DTO
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @PatchMapping(Operations.EDIT)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If the domain was successfully updated return domain otherwise return exception", response = Boolean.class),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class),
            @ApiResponse(code = 409, message = "If the check operation is false throws ConflictException otherwise nothing", response = ConflictResponse.class)})
    @MethodStats
    @Scope(operation = Operations.EDIT)
    default ResponseEntity<Mono<DTO>> edit(@RequestBody JsonPatch dto, C criteria, ServerWebExchange serverWebExchange, Principal principal)
            throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to edit new domain, dto : {}, criteria : {}", dto, criteria);

        USER user = getUser(serverWebExchange, principal);
        Mono<DTO> res = this.getService().edit(criteria, dto, user);
        return ResponseEntity.status(OperationsStatus.EDIT).body(res);
    }
}