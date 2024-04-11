package ir.msob.jima.crud.api.graphql.restful.service.resource.read;

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
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdInput;
import ir.msob.jima.crud.api.graphql.restful.service.resource.BaseCrudGraphQL;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Optional;

/**
 * Interface for a GraphQL resource that provides a method to retrieve an entity by its ID.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <Q>    The type of the Query, which must extend BaseQuery.
 * @param <R>    The type of the Repository, which must extend BaseCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseCrudService.
 */
public interface BaseGetByIdCrudGraphQLResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends BaseCrudGraphQL<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseGetByIdCrudGraphQLResource.class);

    /**
     * Retrieves a single entity based on the provided ID.
     *
     * @param input The ID of the entity to be retrieved.
     * @param token The authorization token for authentication (optional).
     * @return A Mono that emits a DtoType containing the retrieved entity as a string.
     * @throws BadRequestException     If the request is malformed or invalid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    @MethodStats
    @QueryMapping
    @Scope(Operations.GET_BY_ID)
    default Mono<DtoType> getById(@Argument IdInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException {
        log.debug("Request to get by id: dto {}", input);

        Optional<USER> user = getUser(token);

        return getService().getOne(convertToId(input.getId()), user)
                .map(dto -> DtoType.builder().dto(convertToString(dto)).build());
    }

}
