package ir.msob.jima.crud.api.grpc.test.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.google.protobuf.ProtocolStringList;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.PageDto;
import ir.msob.jima.core.commons.shared.PageableDto;
import ir.msob.jima.crud.api.grpc.commons.CrudServiceGrpc;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.ParentDomainCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code ParentDomainCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for CRUD operations.
 * It extends the {@code ParentDomainCrudResourceTest} interface, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes methods for converting between different data types and formats, such as DTOs, JSON patches, and pageable objects.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see ParentDomainCrudResourceTest
 */
public interface ParentDomainCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends ParentDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Returns the gRPC stub for the CRUD service.
     *
     * @return The gRPC stub for the CRUD service.
     */
    CrudServiceGrpc.CrudServiceBlockingStub getCrudServiceBlockingStub();

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
     * Converts a JSON string to a PageableDto object.
     *
     * @param pageable The JSON string.
     * @return The PageableDto object.
     */
    @SneakyThrows
    default PageableDto convertToPageable(String pageable) {
        return getObjectMapper().reader().readValue(pageable, PageableDto.class);
    }

    /**
     * Converts a JSON string to a Page of DTOs.
     *
     * @param page The JSON string.
     * @return The Page of DTOs.
     */
    @SneakyThrows
    default PageDto<DTO> convertToPage(String page) {
        return getObjectMapper().reader().readValue(page, PageDto.class);
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