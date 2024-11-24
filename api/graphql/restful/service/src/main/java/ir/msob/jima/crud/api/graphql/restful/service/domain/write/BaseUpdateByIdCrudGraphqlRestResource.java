package ir.msob.jima.crud.api.graphql.restful.service.domain.write;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.service.domain.ParentCrudGraphqlRestResource;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;
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
 * The {@code BaseUpdateCrudGraphqlRestResource} interface defines a GraphQL mutation for updating an entity.
 * It extends the {@code ParentCrudGraphqlRestResource} interface and provides a method for updating an entity with the specified DTO input.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, Q, R, and S.
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
public interface BaseUpdateByIdCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseUpdateByIdCrudGraphqlRestResource.class);

    /**
     * Updates an entity based on the specified DTO input for the specified CRUD resource.
     *
     * @param input The DTO input for updating an entity. This is an instance of {@code DtoInput} which contains the ID of the entity to be updated and the DTO to be applied.
     * @param token The authorization token for authentication (optional). This is a string representing the user's authorization token. If provided, it will be used for authentication.
     * @return A {@code Mono} emitting a {@code DtoType} containing the updated DTO as a string. This is a reactive stream that emits the updated DTO wrapped in a {@code DtoType} instance.
     * @throws BadRequestException       If the request is malformed or invalid. This exception is thrown when the provided DTO input is not valid.
     * @throws DomainNotFoundException   If the domain is not found. This exception is thrown when the entity with the provided ID does not exist.
     * @throws InvocationTargetException If an error occurs during method invocation. This exception is thrown when there is an application-level error occurred while trying to invoke the method.
     * @throws NoSuchMethodException     If a specified method cannot be found. This exception is thrown when the method that is being called does not exist.
     * @throws InstantiationException    If an instantiation error occurs. This exception is thrown when an application tries to create an instance of a class using the newInstance method in class.
     * @throws IllegalAccessException    If illegal access to a method or field occurs. This exception is thrown when an application tries to reflectively create an instance, set or get a field, or invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor.
     */
    @MethodStats
    @MutationMapping
    @Scope(Operations.UPDATE_BY_ID)
    default Mono<DtoType> updateById(@Argument("input") DtoInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        log.debug("Request to update by id: dto {}", input);

        USER user = getUser(token);

        return getService().update(convertToId(input.getId()), convertToDto(input.getDto()), user)
                .map(d -> DtoType.builder().dto(convertToString(d)).build());
    }
}