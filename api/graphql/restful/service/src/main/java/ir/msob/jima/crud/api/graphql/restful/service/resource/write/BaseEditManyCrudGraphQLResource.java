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
import ir.msob.jima.crud.api.graphql.restful.commons.model.CriteriaJsonPatchInput;
import ir.msob.jima.crud.api.graphql.restful.commons.model.DtosType;
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
 * The {@code BaseEditManyCrudGraphQLResource} interface defines a GraphQL mutation for editing multiple entities based on
 * JSON Patch operations. It extends the {@code BaseCrudGraphQL} interface and provides a method for editing multiple entities
 * with optional JSON Patch input. The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, Q, R, and S.
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
public interface BaseEditManyCrudGraphQLResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends BaseCrudGraphQL<ID, USER, D, DTO, C, Q, R, S> {
    Logger log = LoggerFactory.getLogger(BaseEditManyCrudGraphQLResource.class);

    /**
     * Edits multiple entities based on the specified JSON Patch operations for the specified CRUD resource.
     *
     * @param input The JSON Patch input for editing the entities.
     * @param token The authorization token for authentication (optional).
     * @return A {@code Mono} emitting a {@code DtosType} containing the edited DTOs as strings.
     * @throws BadRequestException     If the request is malformed or invalid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    @MethodStats
    @MutationMapping
    @Scope(Operations.EDIT_MANY)
    default Mono<DtosType> editMany(@Argument CriteriaJsonPatchInput input, @ContextValue(value = HttpHeaders.AUTHORIZATION, required = false) String token) throws BadRequestException, DomainNotFoundException {
        log.debug("Request to edit many: dto {}", input);

        Optional<USER> user = getUser(token);

        return getService().editMany(convertToCriteria(input.getCriteria()), convertToJsonPatch(input.getJsonPatch()), user)
                .map(dtos -> DtosType.builder().dtos(convertToStrings(dtos)).build());
    }

}
