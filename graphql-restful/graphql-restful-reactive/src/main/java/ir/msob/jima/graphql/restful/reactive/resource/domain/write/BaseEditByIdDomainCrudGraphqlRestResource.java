package ir.msob.jima.graphql.restful.reactive.resource.domain.write;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.graphql.restful.api.model.DtoType;
import ir.msob.jima.graphql.restful.api.model.IdJsonPatchInput;
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

/**
 * The {@code BaseEditDomainCrudGraphqlRestResource} interface defines a GraphQL mutation for editing an entity based on JSON Patch
 * operations. It extends the {@code ParentDomainCrudGraphqlRestResource} interface and provides a method for editing an entity with optional
 * JSON Patch input. The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, R, and S.
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
public interface BaseEditByIdDomainCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends ParentDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S> {
    Logger logger = LoggerFactory.getLogger(BaseEditByIdDomainCrudGraphqlRestResource.class);

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
    @Scope(operation = Operations.EDIT_BY_ID)
    default Mono<@NonNull DtoType> editById(@Argument("input") IdJsonPatchInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException {
        logger.debug("Request to edit by id: dto {}", input);

        USER user = getUser(token);

        return getService().edit(convertToId(input.getId()), convertToJsonPatch(input.getJsonPatch()), user)
                .map(d -> DtoType.builder().dto(convertToString(d)).build());
    }

}