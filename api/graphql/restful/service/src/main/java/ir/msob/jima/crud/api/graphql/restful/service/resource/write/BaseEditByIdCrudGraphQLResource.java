package ir.msob.jima.crud.api.graphql.restful.service.resource.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtoType;
import ir.msob.jima.crud.api.graphql.restful.commons.model.IdJsonPatchInput;
import ir.msob.jima.crud.api.graphql.restful.service.resource.BaseCrudGraphQL;
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
 * The {@code BaseEditCrudGraphQLResource} interface defines a GraphQL mutation for editing an entity based on JSON Patch
 * operations. It extends the {@code BaseCrudGraphQL} interface and provides a method for editing an entity with optional
 * JSON Patch input. The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, Q, R, and S.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser<ID>}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseCrudRepository<ID, USER, D, C, Q>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseCrudService<ID, USER, D, DTO, C, Q, R>}.
 * @see BaseCrudGraphQL
 */
public interface BaseEditByIdCrudGraphQLResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends BaseCrudGraphQL<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseEditByIdCrudGraphQLResource.class);

    /**
     * Edits an entity based on the specified JSON Patch operations for the specified CRUD resource.
     *
     * @param input The JSON Patch input for editing the entity. This is an instance of {@code IdJsonPatchInput} which contains the ID of the entity to be edited and the JSON Patch operations to be applied.
     * @param token The authorization token for authentication (optional). This is a string representing the user's authorization token. If provided, it will be used for authentication.
     * @return A {@code Mono} emitting a {@code DtoType} containing the edited DTO as a string. This is a reactive stream that emits the edited DTO wrapped in a {@code DtoType} instance.
     * @throws BadRequestException     If the request is malformed or invalid. This exception is thrown when the provided JSON Patch input is not valid.
     * @throws DomainNotFoundException If the domain is not found. This exception is thrown when the entity with the provided ID does not exist.
     */
    @MethodStats
    @MutationMapping
    @Scope(Operations.EDIT_BY_ID)
    default Mono<DtoType> editById(@Argument IdJsonPatchInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException {
        log.debug("Request to edit by id: dto {}", input);

        Optional<USER> user = getUser(token);

        return getService().edit(convertToId(input.getId()), convertToJsonPatch(input.getJsonPatch()), user)
                .map(d -> DtoType.builder().dto(convertToString(d)).build());
    }

}