package ir.msob.jima.crud.api.grpc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.google.protobuf.ProtocolStringList;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.crud.api.grpc.commons.ReactorCrudServiceGrpc;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.service.BaseCrudService;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * The {@code ParentCrudGrpcResource} interface defines the base contract for gRPC (Google Remote Procedure Call) services in a CRUD (Create, Read, Update, Delete) context.
 * It extends {@link ir.msob.jima.core.commons.resource.BaseResource} and {@link ir.msob.jima.crud.commons.BaseCrudResource} interfaces,
 * providing methods and utilities related to gRPC operations, as well as CRUD functionality.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <Q>    The type of query used for retrieving entities.
 * @param <R>    The type of CRUD repository used for database operations.
 * @param <S>    The type of CRUD service providing business logic.
 * @see ir.msob.jima.core.commons.resource.BaseResource
 * @see ir.msob.jima.crud.commons.BaseCrudResource
 */
public interface ParentCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>>
        extends BaseResource<ID, USER>, BaseCrudResource, ReactorCrudServiceGrpc.CrudServiceImplBase {

    ObjectMapper getObjectMapper();

    /**
     * Gets the CRUD service associated with the gRPC service.
     *
     * @return The CRUD service instance.
     */
    S getService();

    /**
     * Gets the user service associated with the gRPC service.
     *
     * @return The user service instance.
     */
    BaseUserService getUserService();

    /**
     * Retrieves the current authenticated user, if available.
     *
     * @return An {@link Optional} containing the authenticated user, or an empty {@link Optional} if no user is authenticated.
     */
    @SneakyThrows
    default Optional<USER> getUser() {
        return getUserService().getUser(getAuthentication());
    }

    /**
     * Retrieves the authentication information for the current request.
     *
     * @return The {@link Authentication} object representing the current request's authentication context.
     * @throws ExecutionException   If the computation threw an exception.
     * @throws InterruptedException If the current thread was interrupted while waiting.
     */
    default Authentication getAuthentication() throws ExecutionException, InterruptedException {
        return ReactiveSecurityContextHolder
                .getContext()
                .switchIfEmpty(Mono.just(SecurityContextHolder.getContext()))
                .mapNotNull(SecurityContext::getAuthentication)
                .toFuture()
                .get();
    }

    default Collection<DTO> convertToDtos(ProtocolStringList dtos) {
        return dtos.stream()
                .map(this::convertToDto)
                .toList();
    }

    @SneakyThrows
    default JsonPatch convertToJsonPatch(String jsonPatch) {
        return getObjectMapper().reader().readValue(jsonPatch, JsonPatch.class);
    }

    default Iterable<String> convertToStrings(Collection<?> result) {
        return result.stream()
                .map(this::convertToString)
                .toList();

    }

    @SneakyThrows
    default String convertToString(Object o) {
        return getObjectMapper().writeValueAsString(o);
    }

    @SneakyThrows
    default C convertToCriteria(String criteria) {
        return getObjectMapper().reader().readValue(criteria, getService().getCriteriaClass());
    }

    @SneakyThrows
    default DTO convertToDto(String dto) {
        return getObjectMapper().reader().readValue(dto, getService().getDtoClass());
    }

    @SneakyThrows
    default Pageable convertToPageable(String pageable) {
        return getObjectMapper().reader().readValue(pageable, Pageable.class);
    }
}
