package ir.msob.jima.crud.api.grpc.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.google.protobuf.ProtocolStringList;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.grpc.commons.ReactorCrudServiceGrpc;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.ParentCrudResourceTest;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code ParentCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for CRUD operations.
 * It extends the {@code ParentCrudResourceTest} interface, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes methods for converting between different data types and formats, such as DTOs, JSON patches, and pageable objects.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <Q>    The type of query used for retrieving entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see ParentCrudResourceTest
 */
public interface ParentCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends ParentCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Returns the gRPC stub for the CRUD service.
     *
     * @return The gRPC stub for the CRUD service.
     */
    ReactorCrudServiceGrpc.ReactorCrudServiceStub getReactorCrudServiceStub();

    /**
     * Returns the ObjectMapper for JSON processing.
     *
     * @return The ObjectMapper for JSON processing.
     */
    ObjectMapper getObjectMapper();

    /**
     * Converts a list of protocol strings to a collection of DTOs.
     *
     * @param dtos The list of protocol strings.
     * @return The collection of DTOs.
     */
    default Collection<DTO> convertToDtos(ProtocolStringList dtos) {
        return dtos.stream()
                .map(this::convertToDto)
                .toList();
    }

    /**
     * Converts a JSON string to a JsonPatch.
     *
     * @param jsonPatch The JSON string.
     * @return The JsonPatch.
     */
    @SneakyThrows
    default JsonPatch convertToJsonPatch(String jsonPatch) {
        return getObjectMapper().reader().readValue(jsonPatch, JsonPatch.class);
    }

    /**
     * Converts a collection of objects to a collection of strings.
     *
     * @param result The collection of objects.
     * @return The collection of strings.
     */
    default Iterable<String> convertToStrings(Collection<?> result) {
        return result.stream()
                .map(this::convertToString)
                .toList();
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param o The object.
     * @return The JSON string.
     */
    @SneakyThrows
    default String convertToString(Object o) {
        return getObjectMapper().writeValueAsString(o);
    }

    /**
     * Converts a JSON string to a criteria object.
     *
     * @param criteria The JSON string.
     * @return The criteria object.
     */
    @SneakyThrows
    default C convertToCriteria(String criteria) {
        return getObjectMapper().reader().readValue(criteria, getCriteriaClass());
    }

    /**
     * Converts a JSON string to a DTO.
     *
     * @param dto The JSON string.
     * @return The DTO.
     */
    @SneakyThrows
    default DTO convertToDto(String dto) {
        return getObjectMapper().reader().readValue(dto, getDtoClass());
    }

    /**
     * Converts a JSON string to a Pageable object.
     *
     * @param pageable The JSON string.
     * @return The Pageable object.
     */
    @SneakyThrows
    default Pageable convertToPageable(String pageable) {
        return getObjectMapper().reader().readValue(pageable, Pageable.class);
    }

    /**
     * Converts a JSON string to a Page of DTOs.
     *
     * @param page The JSON string.
     * @return The Page of DTOs.
     */
    @SneakyThrows
    default Page<DTO> convertToPage(String page) {
        return getObjectMapper().reader().readValue(page, Page.class);
    }

    /**
     * Converts a JSON string to an ID.
     *
     * @param id The JSON string.
     * @return The ID.
     */
    @SneakyThrows
    default ID convertToId(String id) {
        return getObjectMapper().reader().readValue(id, getIdClass());
    }

    /**
     * Converts a collection of JSON strings to a collection of IDs.
     *
     * @param ids The collection of JSON strings.
     * @return The collection of IDs.
     */
    @SneakyThrows
    default Collection<ID> convertToIds(Collection<String> ids) {
        return ids.stream()
                .map(this::convertToId)
                .toList();
    }
}