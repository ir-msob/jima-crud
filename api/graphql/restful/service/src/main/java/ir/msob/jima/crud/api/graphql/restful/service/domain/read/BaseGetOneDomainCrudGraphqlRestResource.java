package ir.msob.jima.crud.api.graphql.restful.service.domain.read;

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
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.service.domain.ParentDomainCrudGraphqlRestResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import org.jspecify.annotations.NonNull;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The {@code BaseGetOneDomainCrudGraphqlRestResource} interface defines a GraphQL query for retrieving a single entity
 * based on specified criteria. It extends the {@code ParentDomainCrudGraphqlRestResource} interface and provides a method for
 * fetching a single entity with optional criteria input. The interface is generic, allowing customization
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
public interface BaseGetOneDomainCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S> {
    Logger logger = LoggerFactory.getLogger(BaseGetOneDomainCrudGraphqlRestResource.class);

    /**
     * Retrieves a single entity based on specified criteria for the specified CRUD resource.
     *
     * @param input The criteria input for fetching a single entity.
     * @param token The authorization token for authentication (optional).
     * @return A {@code Mono} emitting a {@code DtoType} containing the retrieved entity as a string.
     * @throws BadRequestException     If the request is malformed or invalid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    @MethodStats
    @QueryMapping
    @Scope(operation = Operations.GET_ONE)
    default Mono<@NonNull DtoType> getOne(@Argument("input") CriteriaInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException {
        logger.debug("Request to get one: dto {}", input);

        USER user = getUser(token);

        return getService().getOne(convertToCriteria(input.getCriteria()), user)
                .map(dto -> DtoType.builder().dto(convertToString(dto)).build());
    }

}
