package ir.msob.jima.crud.api.restful.service.rest.read;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.rest.ParentCrudRestResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.read.BaseGetStreamCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

/**
 * @param <ID>
 * @param <D>
 * @param <DTO>
 * @param <USER>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public interface BaseGetStreamCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseGetStreamCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseGetStreamCrudRestResource.class);

    @GetMapping(Operations.GET_STREAM)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Return a domain or null"),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @MethodStats
    //@Scope(Constants.GET_STREAM)
    default ResponseEntity<Flux<DTO>> getStream(C criteria, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException, JsonProcessingException {
        log.debug("REST request to get stream domain, criteria {} : ", criteria);

        crudValidation(Operations.GET_STREAM);

        Optional<USER> user = getUser(serverWebExchange, principal);
        return this.getStreamResponse(this.getService().getStream(criteria, user), criteria, user);
    }

    default ResponseEntity<Flux<DTO>> getStreamResponse(Flux<DTO> dtoFlux, C criteria, Optional<USER> user) {
        return ResponseEntity.status(OperationsStatus.GET_STREAM).body(dtoFlux);
    }
}
