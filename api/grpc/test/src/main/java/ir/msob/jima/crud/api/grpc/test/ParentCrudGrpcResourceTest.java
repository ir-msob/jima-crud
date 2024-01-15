package ir.msob.jima.crud.api.grpc.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.google.protobuf.ProtocolStringList;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
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

public interface ParentCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends ParentCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    ReactorCrudServiceGrpc.ReactorCrudServiceStub getReactorCrudServiceStub();


    ObjectMapper getObjectMapper();

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
        return getObjectMapper().reader().readValue(criteria, getCriteriaClass());
    }

    @SneakyThrows
    default DTO convertToDto(String dto) {
        return getObjectMapper().reader().readValue(dto, getDtoClass());
    }

    @SneakyThrows
    default Pageable convertToPageable(String pageable) {
        return getObjectMapper().reader().readValue(pageable, Pageable.class);
    }

    @SneakyThrows
    default Page<DTO> convertToPage(String page) {
        return getObjectMapper().reader().readValue(page, Page.class);
    }

    @SneakyThrows
    default ID convertToId(String id) {
        return getObjectMapper().reader().readValue(id, getIdClass());
    }

    @SneakyThrows
    default Collection<ID> convertToIds(Collection<String> ids) {
        return ids.stream()
                .map(this::convertToId)
                .toList();
    }
}
