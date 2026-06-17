package ir.msob.jima.graphql.restful.reactive.resource.domain.read;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.graphql.restful.api.model.DtoType;
import ir.msob.jima.graphql.restful.api.model.IdInput;
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
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * Interface for a GraphQL resource that provides a method to retrieve an entity by its ID.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDomainDto.
 * @param <C>    The type of the Criteria, which must extend BaseDomainCriteria.
 * @param <R>    The type of the Repository, which must extend BaseReactiveRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface BaseGetByIdDomainCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S> {
    Logger logger = LoggerFactory.getLogger(BaseGetByIdDomainCrudGraphqlRestResource.class);

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
    @Scope(operation = Operations.GET_BY_ID)
    default Mono<@NonNull DtoType> getById(@Argument("input") IdInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException {
        logger.debug("Request to get by id: dto {}", input);

        USER user = getUser(token);

        return getService().getOne(convertToId(input.getId()), user)
                .map(dto -> DtoType.builder().dto(convertToString(dto)).build());
    }

}
