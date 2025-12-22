package ir.msob.jima.crud.api.grpc.service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.core.commons.shared.PageableDto;
import ir.msob.jima.crud.api.grpc.commons.CrudServiceGrpc;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import lombok.SneakyThrows;
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
 * The {@code ParentDomainCrudGrpcResource} interface defines the base contract for gRPC (Google Remote Procedure Call) services in a CRUD (Create, Read, Update, Delete) context.
 * It extends {@link ir.msob.jima.core.commons.resource.BaseResource} and {@link ir.msob.jima.crud.commons.BaseCrudResource} interfaces,
 * providing methods and utilities childdomain to gRPC operations, as well as CRUD functionality.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of CRUD repository used for database operations.
 * @param <S>    The type of CRUD service providing business logic.
 * @see ir.msob.jima.core.commons.resource.BaseResource
 * @see ir.msob.jima.crud.commons.BaseCrudResource
 */
public interface ParentDomainCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>>
        extends BaseResource<ID, USER>, BaseCrudResource, CrudServiceGrpc.CrudServiceI {

    /**
     * Returns the ObjectMapper instance.
     *
     * @return The ObjectMapper instance.
     */
    ObjectMapper getObjectMapper();

    /**
     * Returns the CRUD service associated with the gRPC service.
     *
     * @return The CRUD service instance.
     */
    S getService();

    /**
     * Returns the user service associated with the gRPC service.
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
    default USER getUser() {
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

    /**
     * Converts a list of ProtocolStringList to a collection of DTOs.
     *
     * @param dtos The list of ProtocolStringList to be converted.
     * @return A collection of DTOs.
     */
    default Collection<DTO> convertToDtos(com.google.protobuf.ProtocolStringList dtos) {
        return dtos.stream()
                .map(this::convertToDto)
                .toList();
    }

    /**
     * Converts a JSON string to a JsonPatch.
     *
     * @param jsonPatch The JSON string to be converted.
     * @return A JsonPatch.
     */
    @SneakyThrows
    default JsonPatch convertToJsonPatch(String jsonPatch) {
        return getObjectMapper().reader().readValue(jsonPatch, JsonPatch.class);
    }

    /**
     * Converts a collection of objects to a list of strings.
     *
     * @param result The collection of objects to be converted.
     * @return A list of strings.
     */
    default Iterable<String> convertToStrings(Collection<?> result) {
        return result.stream()
                .map(this::convertToString)
                .toList();

    }

    /**
     * Converts an object to a string.
     *
     * @param o The object to be converted.
     * @return A string.
     */
    @SneakyThrows
    default String convertToString(Object o) {
        return getObjectMapper().writeValueAsString(o);
    }

    /**
     * Converts a string to a criteria.
     *
     * @param criteria The string to be converted.
     * @return A criteria.
     */
    @SneakyThrows
    default C convertToCriteria(String criteria) {
        return getObjectMapper().reader().readValue(criteria, getService().getCriteriaClass());
    }

    /**
     * Converts a string to a DTO.
     *
     * @param dto The string to be converted.
     * @return A DTO.
     */
    @SneakyThrows
    default DTO convertToDto(String dto) {
        return getObjectMapper().reader().readValue(dto, getService().getDtoClass());
    }

    /**
     * Converts a string to a Pageable.
     *
     * @param pageable The string to be converted.
     * @return A Pageable.
     */
    @SneakyThrows
    default PageableDto convertToPageableDto(String pageable) {
        return getObjectMapper().reader().readValue(pageable, PageableDto.class);
    }

    /**
     * Converts a string to an ID.
     *
     * @param dto The string to be converted.
     * @return An ID.
     */
    @SneakyThrows
    default ID convertToId(String dto) {
        return getObjectMapper().reader().readValue(dto, getService().getIdClass());
    }
}