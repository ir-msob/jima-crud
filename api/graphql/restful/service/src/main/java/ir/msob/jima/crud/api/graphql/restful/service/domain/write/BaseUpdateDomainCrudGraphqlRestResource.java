package ir.msob.jima.crud.api.graphql.restful.service.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.service.domain.ParentDomainCrudGraphqlRestResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * The {@code BaseUpdateDomainCrudGraphqlRestResource} interface defines a GraphQL mutation for updating an entity.
 * It extends the {@code ParentDomainCrudGraphqlRestResource} interface and provides a method for updating an entity with the specified DTO input.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, R, and S.
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
public interface BaseUpdateDomainCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S> {
    Logger log = LoggerFactory.getLogger(BaseUpdateDomainCrudGraphqlRestResource.class);

    /**
     * Updates an entity based on the specified DTO input for the specified CRUD resource.
     *
     * @param input The DTO input for updating an entity.
     * @param token The authorization token for authentication (optional).
     * @return A {@code Mono} emitting a {@code DtoType} containing the updated DTO as a string.
     * @throws BadRequestException       If the request is malformed or invalid.
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws InvocationTargetException If an error occurs during method invocation.
     * @throws NoSuchMethodException     If a specified method cannot be found.
     * @throws InstantiationException    If an instantiation error occurs.
     * @throws IllegalAccessException    If illegal access to a method or field occurs.
     */
    @MethodStats
    @MutationMapping
    @Scope(operation = Operations.UPDATE)
    default Mono<DtoType> update(@Argument("input") DtoInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        log.debug("Request to update: dto {}", input);

        USER user = getUser(token);

        return getService().update(convertToDto(input.getDto()), user)
                .map(d -> DtoType.builder().dto(convertToString(d)).build());
    }
}
