package ir.msob.jima.crud.api.restful.service.rest.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.conflict.ConflictResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.rest.ParentCrudRestResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.write.BaseEditManyCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * This interface provides a RESTful API for editing multiple domains based on a given criteria.
 * It extends the ParentCrudRestResource interface and provides a default implementation for the editMany method.
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
public interface BaseEditManyCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseEditManyCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseEditManyCrudRestResource.class);

    /**
     * This method provides a RESTful API for editing multiple domains based on a given criteria.
     * It validates the operation, retrieves the user, and then calls the service to edit the domains.
     * It returns a ResponseEntity with the edited DTOs.
     *
     * @param dto               the JsonPatch object containing the changes to apply to the domains
     * @param criteria          the criteria to edit the domains
     * @param serverWebExchange the ServerWebExchange object
     * @param principal         the Principal object
     * @return a ResponseEntity with the edited DTOs
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @PatchMapping(Operations.EDIT_MANY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If the domain was successfully edited return domain otherwise return exception", response = Boolean.class),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class),
            @ApiResponse(code = 409, message = "If the check operation is false throws ConflictException otherwise nothing", response = ConflictResponse.class)})
    @MethodStats
    @Scope(Operations.EDIT_MANY)
    default ResponseEntity<Mono<Collection<DTO>>> editMany(@RequestBody JsonPatch dto, C criteria, ServerWebExchange serverWebExchange, Principal principal)
            throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("REST request to edit many, dto : {}, criteria : {}", dto, criteria);

        USER user = getUser(serverWebExchange, principal);
        return this.editManyResponse(dto, this.getService().editMany(criteria, dto, user), criteria, user);
    }

    /**
     * This method creates a ResponseEntity with the edited DTOs.
     * It is called by the editMany method.
     *
     * @param dto        the JsonPatch object containing the changes to apply to the domains
     * @param editedDtos the Mono object containing the edited DTOs
     * @param criteria   the criteria to edit the domains
     * @param user       the user
     * @return a ResponseEntity with the edited DTOs
     */
    default ResponseEntity<Mono<Collection<DTO>>> editManyResponse(JsonPatch dto, Mono<Collection<DTO>> editedDtos, C criteria, USER user) {
        return ResponseEntity.status(OperationsStatus.EDIT_MANY).body(editedDtos);
    }
}