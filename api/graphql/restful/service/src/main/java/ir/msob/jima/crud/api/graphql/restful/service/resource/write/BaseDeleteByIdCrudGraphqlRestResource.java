package ir.msob.jima.crud.api.graphql.restful.service.resource.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdType;
import ir.msob.jima.crud.api.graphql.restful.service.resource.ParentCrudGraphqlRestResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Optional;

/**
 * The {@code BaseDeleteCrudGraphqlRestResource} interface defines a GraphQL mutation for deleting entities
 * based on specified criteria. It extends the {@code ParentCrudGraphqlRestResource} interface and provides a method
 * for deleting entities with optional criteria input. The interface is generic, allowing customization
 * for different types such as ID, USER, D, DTO, C, Q, R, and S.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseCrudRepository<ID, USER, D, C, Q>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseCrudService<ID, USER, D, DTO, C, Q, R>}.
 * @see ParentCrudGraphqlRestResource
 */
public interface BaseDeleteByIdCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseDeleteByIdCrudGraphqlRestResource.class);

    /**
     * Deletes entities based on the specified criteria for the specified CRUD resource.
     *
     * @param input The criteria input for deleting entities.
     * @param token The authorization token for authentication (optional).
     * @return A {@code Mono} emitting an {@code IdType} containing the ID of the deleted entity as a string.
     * @throws BadRequestException     If the request is malformed or invalid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    @MethodStats
    @MutationMapping
    @Scope(Operations.DELETE_BY_ID)
    default Mono<IdType> deleteById(@Argument("input") IdInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException {
        log.debug("Request to delete by id: dto {}", input);

        Optional<USER> user = getUser(token);

        return getService().delete(convertToId(input.getId()), user)
                .map(id -> IdType.builder().id(String.valueOf(id)).build());
    }

}
