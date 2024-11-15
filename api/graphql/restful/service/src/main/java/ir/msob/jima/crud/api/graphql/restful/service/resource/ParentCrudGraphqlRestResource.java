package ir.msob.jima.crud.api.graphql.restful.service.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.service.BaseCrudService;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * The {@code ParentCrudGraphqlRestResource} interface defines a set of common methods for GraphQL-based CRUD operations.
 * It extends {@code BaseResource<ID, USER>} and implements {@code BaseCrudResource}.
 * This interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, Q, R, and S.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseCrudRepository<ID, USER, D, C, Q>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseCrudService<ID, USER, D, DTO, C, Q, R>}.
 * @see BaseResource
 * @see BaseCrudResource
 */
public interface ParentCrudGraphqlRestResource<ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>>
        extends BaseResource<ID, USER>, BaseCrudResource {

    /**
     * Gets the object mapper used for JSON serialization and deserialization.
     *
     * @return The object mapper.
     */
    ObjectMapper getObjectMapper();

    /**
     * Gets the CRUD service associated with the resource.
     *
     * @return The CRUD service.
     */
    S getService();

    /**
     * Gets the user service responsible for retrieving user information.
     *
     * @return The user service.
     */
    BaseUserService getUserService();

    /**
     * Retrieves the user associated with the provided authentication token.
     *
     * @param token The authentication token (optional).
     * @return the user associated with the token, or an empty optional if not authenticated.
     */
    default USER getUser(String token) {
        return getUserService().getUser(token);
    }

    /**
     * Converts a list of DTO strings to a collection of DTOs.
     *
     * @param dtos The list of DTO strings.
     * @return A collection of DTOs.
     */
    default Collection<DTO> convertToDtos(List<String> dtos) {
        return dtos.stream()
                .map(this::convertToDto)
                .toList();
    }

    /**
     * Converts a JSON patch string to a {@code JsonPatch} object.
     *
     * @param jsonPatch The JSON patch string.
     * @return The {@code JsonPatch} object.
     */
    @SneakyThrows
    default JsonPatch convertToJsonPatch(String jsonPatch) {
        return getObjectMapper().reader().readValue(jsonPatch, JsonPatch.class);
    }

    /**
     * Converts a collection of results to a list of strings.
     *
     * @param result The collection of results.
     * @return A list of strings.
     */
    default List<String> convertToStrings(Collection<?> result) {
        return result.stream()
                .map(this::convertToString)
                .toList();
    }

    /**
     * Converts an object to its string representation using JSON serialization.
     *
     * @param o The object to convert.
     * @return The string representation of the object.
     */
    @SneakyThrows
    default String convertToString(Object o) {
        return getObjectMapper().writeValueAsString(o);
    }

    /**
     * Converts a criteria string to a criteria object.
     *
     * @param criteria The criteria string.
     * @return The criteria object.
     */
    @SneakyThrows
    default C convertToCriteria(String criteria) {
        return getObjectMapper().reader().readValue(criteria, getService().getCriteriaClass());
    }

    /**
     * Converts an ID string to an ID object.
     *
     * @param id The ID string.
     * @return The ID object.
     */
    @SneakyThrows
    default ID convertToId(String id) {
        return getObjectMapper().reader().readValue(id, getService().getIdClass());
    }

    /**
     * Converts a DTO string to a DTO object.
     *
     * @param dto The DTO string.
     * @return The DTO object.
     */
    @SneakyThrows
    default DTO convertToDto(String dto) {
        return getObjectMapper().reader().readValue(dto, getService().getDtoClass());
    }

    /**
     * Converts a pageable string to a {@code Pageable} object.
     *
     * @param pageable The pageable string.
     * @return The {@code Pageable} object.
     */
    @SneakyThrows
    default Pageable convertToPageable(String pageable) {
        return getObjectMapper().reader().readValue(pageable, Pageable.class);
    }
}
