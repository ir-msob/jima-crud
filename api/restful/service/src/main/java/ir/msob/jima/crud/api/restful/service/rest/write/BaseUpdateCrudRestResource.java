package ir.msob.jima.crud.api.restful.service.rest.write;

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
import ir.msob.jima.crud.service.write.BaseUpdateCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

/**
 * This interface provides a RESTful API for updating a domain based on a given DTO.
 * It extends the ParentCrudRestResource interface and provides a default implementation for the update method.
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
public interface BaseUpdateCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseUpdateCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseUpdateCrudRestResource.class);

    /**
     * This method provides a RESTful API for updating a domain based on a given DTO.
     * It validates the operation, retrieves the user, and then calls the service to update the domain.
     * It returns a ResponseEntity with the updated DTO.
     *
     * @param dto               the DTO to update the domain
     * @param serverWebExchange the ServerWebExchange object
     * @param principal         the Principal object
     * @return a ResponseEntity with the updated DTO
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @PutMapping(Operations.UPDATE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If the domain was successfully updated return domain otherwise return exception", response = Boolean.class),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class),
            @ApiResponse(code = 409, message = "If the check operation is false throws ConflictException otherwise nothing", response = ConflictResponse.class)})
    @MethodStats
    @Scope(Operations.UPDATE)
    default ResponseEntity<Mono<DTO>> update(@RequestBody DTO dto, ServerWebExchange serverWebExchange, Principal principal)
            throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to update new domain, dto : {}", dto);

        Optional<USER> user = getUser(serverWebExchange, principal);
        return this.updateResponse(dto, this.getService().update(dto, user), user);
    }

    /**
     * This method creates a ResponseEntity with the updated DTO.
     * It is called by the update method.
     *
     * @param dto        the DTO to update the domain
     * @param updatedDto the Mono object containing the updated DTO
     * @param user       the Optional object containing the user
     * @return a ResponseEntity with the updated DTO
     */
    default ResponseEntity<Mono<DTO>> updateResponse(DTO dto, Mono<DTO> updatedDto, Optional<USER> user) {
        return ResponseEntity.status(OperationsStatus.UPDATE).body(updatedDto);
    }
}