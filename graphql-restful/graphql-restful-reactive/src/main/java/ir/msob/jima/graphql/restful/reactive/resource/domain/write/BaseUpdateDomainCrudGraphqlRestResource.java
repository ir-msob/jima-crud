package ir.msob.jima.graphql.restful.reactive.resource.domain.write;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.graphql.restful.api.model.DtoInput;
import ir.msob.jima.graphql.restful.api.model.DtoType;
import ir.msob.jima.graphql.restful.reactive.resource.domain.ParentDomainCrudGraphqlRestResource;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.scope.Scope;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import org.jspecify.annotations.NonNull;
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
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDomainDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseDomainCriteria<ID, USER>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseReactiveRepository<ID, USER, D, C>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseChildDomainCrudService<ID, USER, D, DTO, C, R>}.
 * @see ParentDomainCrudGraphqlRestResource
 */
public interface BaseUpdateDomainCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S> {
    Logger logger = LoggerFactory.getLogger(BaseUpdateDomainCrudGraphqlRestResource.class);

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
    default Mono<@NonNull DtoType> update(@Argument("input") DtoInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        logger.debug("Request to update: dto {}", input);

        USER user = getUser(token);

        return getService().update(convertToDto(input.getDto()), user)
                .map(d -> DtoType.builder().dto(convertToString(d)).build());
    }
}
