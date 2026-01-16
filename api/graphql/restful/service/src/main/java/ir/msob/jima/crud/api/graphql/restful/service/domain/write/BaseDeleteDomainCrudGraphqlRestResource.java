package ir.msob.jima.crud.api.graphql.restful.service.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.commons.model.CriteriaInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdType;
import ir.msob.jima.crud.api.graphql.restful.service.domain.ParentDomainCrudGraphqlRestResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The {@code BaseDeleteDomainCrudGraphqlRestResource} interface defines a GraphQL mutation for deleting entities
 * based on specified criteria. It extends the {@code ParentDomainCrudGraphqlRestResource} interface and provides a method
 * for deleting entities with optional criteria input. The interface is generic, allowing customization
 * for different types such as ID, USER, D, DTO, C, R, and S.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseDomainCrudRepository<ID, USER, D, C>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseDomainCrudService<ID, USER, D, DTO, C, R>}.
 * @see ParentDomainCrudGraphqlRestResource
 */
public interface BaseDeleteDomainCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S> {
    Logger logger = LoggerFactory.getLogger(BaseDeleteDomainCrudGraphqlRestResource.class);

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
    @Scope(operation = Operations.DELETE)
    default Mono<@NonNull IdType> delete(@Argument("input") CriteriaInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException {
        logger.debug("Request to delete: dto {}", input);

        USER user = getUser(token);

        return getService().delete(convertToCriteria(input.getCriteria()), user)
                .map(id -> IdType.builder().id(String.valueOf(id)).build());
    }

}
