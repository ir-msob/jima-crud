package ir.msob.jima.crud.api.restful.service.domain.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.domain.ParentCrudRestResource;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.read.BaseGetPageCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;

/**
 * This interface provides a RESTful API for retrieving a page of domains based on a given criteria.
 * It extends the ParentCrudRestResource interface and provides a default implementation for the getPage method.
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
public interface BaseGetPageCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseGetPageCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseGetPageCrudRestResource.class);

    /**
     * This method provides a RESTful API for retrieving a page of domains based on a given criteria.
     * It validates the operation, retrieves the user, and then calls the service to get the page of domains.
     * It returns a ResponseEntity with the page of DTOs.
     *
     * @param criteria          the criteria to get the page of domains
     * @param page              the page number to retrieve
     * @param size              the size of the page to retrieve
     * @param serverWebExchange the ServerWebExchange object
     * @param principal         the Principal object
     * @return a ResponseEntity with the page of DTOs
     * @throws BadRequestException     if the validation operation is incorrect
     * @throws DomainNotFoundException if the domain is not found
     */
    @GetMapping
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @MethodStats
    @Scope(Operations.GET_PAGE)
    default ResponseEntity<Mono<Page<DTO>>> getPage(C criteria, @RequestParam("page") int page, @RequestParam("size") int size, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("REST request to get page domain, criteria {} : ", criteria);

        USER user = getUser(serverWebExchange, principal);
        return this.getPageResponse(this.getService().getPage(criteria, PageRequest.of(page, size), user), criteria, user);
    }

    /**
     * This method creates a ResponseEntity with the page of DTOs.
     * It is called by the getPage method.
     *
     * @param dtoPage  the Mono object containing the page of DTOs
     * @param criteria the criteria to get the page of domains
     * @param user     the user
     * @return a ResponseEntity with the page of DTOs
     */
    default ResponseEntity<Mono<Page<DTO>>> getPageResponse(Mono<Page<DTO>> dtoPage, C criteria, USER user) {
        return ResponseEntity.status(OperationsStatus.GET_PAGE).body(dtoPage);
    }
}