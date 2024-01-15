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
import ir.msob.jima.crud.service.read.BaseCountAllCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.Optional;

/**
 * @param <ID>
 * @param <D>
 * @param <USER>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public interface BaseCountAllCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseCountAllCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudRestResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseCountAllCrudRestResource.class);

    @GetMapping(Operations.COUNT_ALL)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "If domain(s) already count return true otherwise return false", response = Long.class),
            @ApiResponse(code = 400, message = "If the validation operation is incorrect throws BadRequestException otherwise nothing", response = BadRequestResponse.class)})
    @MethodStats
    //@Scope(Constants.COUNT_ALL)
    default ResponseEntity<Mono<Long>> countAll(ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, JsonProcessingException {
        log.debug("REST request to count all");

        crudValidation(Operations.COUNT_ALL);

        Optional<USER> user = getUser(serverWebExchange, principal);
        return this.countAllResponse(this.getService().countAll(user), user);
    }

    default ResponseEntity<Mono<Long>> countAllResponse(Mono<Long> result, Optional<USER> user) {
        return ResponseEntity.status(OperationsStatus.COUNT_ALL).body(result);
    }

}