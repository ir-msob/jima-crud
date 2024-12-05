package ir.msob.jima.crud.api.restful.service.related.relatedobject.relateddomain.write;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestResponse;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.related.relatedobject.relateddomain.BaseRelatedDomainDto;
import ir.msob.jima.core.commons.related.relatedobject.relateddomain.RelatedDomainAbstract;
import ir.msob.jima.core.commons.related.relatedobject.relateddomain.RelatedDomainCriteriaAbstract;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.ParentRelatedCrudRestResource;
import ir.msob.jima.crud.service.related.relatedobject.relateddomain.BaseRelatedDomainCrudService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;


public interface BaseSaveManyRelatedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RD extends RelatedDomainAbstract<ID>
        , C extends RelatedDomainCriteriaAbstract<ID, RD>
        , S extends BaseRelatedDomainCrudService<ID, USER, DTO, RD, C>
        > extends ParentRelatedCrudRestResource<ID, USER, DTO, RD, C, BaseRelatedDomainDto<ID, RD>, S> {

    Logger log = LoggerFactory.getLogger(BaseSaveManyRelatedDomainCrudRestResource.class);


    @PostMapping("{parentId}/related-domain/" + Operations.SAVE_MANY)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return id of deleted domain or null"),
            @ApiResponse(code = 400, message = "Bad request", response = BadRequestResponse.class),
            @ApiResponse(code = 404, message = "Domain not found", response = DomainNotFoundException.class)
    })
    @MethodStats
    @Scope(Operations.SAVE_MANY)
    default ResponseEntity<Mono<DTO>> saveMany(@PathVariable("parentId") ID parentId, @RequestBody Collection<@Valid RD> dtos, ServerWebExchange serverWebExchange, Principal principal) throws BadRequestException, DomainNotFoundException {
        log.debug("REST request to save many related domains, parentId {}, dtos {}", parentId, dtos);

        USER user = getUser(serverWebExchange, principal);
        return ResponseEntity.status(OperationsStatus.SAVE_MANY).body(getService().saveMany(parentId, dtos, user));
    }

}