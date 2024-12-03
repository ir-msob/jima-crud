package ir.msob.jima.crud.api.grpc.test.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.grpc.commons.CriteriaJsonPatchMsg;
import ir.msob.jima.crud.api.grpc.test.domain.ParentCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.write.BaseEditManyCrudResourceTest;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseEditManyCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for editing multiple entities based on a given criteria.
 * It extends both the {@code BaseEditManyCrudResourceTest} and {@code ParentCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit multiple entities based on a given criteria using gRPC. The result of the edit operation is a collection of updated DTOs of the entities.
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
 * @see BaseEditManyCrudResourceTest
 * @see ParentCrudGrpcResourceTest
 */
public interface BaseEditManyCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Executes a gRPC request to edit multiple entities based on a given criteria and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the saved entity.
     * @param jsonPatch The JSON Patch representing the changes to be applied to the entity.
     */
    @SneakyThrows
    @Override
    default void editManyRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<Collection<DTO>> assertable) {
        // Create an instance of CriteriaJsonPatchMsg with the ID of the saved entity and the JSON Patch
        CriteriaJsonPatchMsg msg = CriteriaJsonPatchMsg.newBuilder()
                .setCriteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId())))
                .setJsonPatch(convertToString(jsonPatch))
                .build();
        // Execute the gRPC request with the created CriteriaJsonPatchMsg and extract the result from the response
        Collection<DTO> dtos = getReactorCrudServiceStub().editMany(Mono.just(msg))
                .toFuture()
                .get()
                .getDtosList()
                .stream()
                .map(this::convertToDto)
                .toList();
        assertable.assertThan(dtos);

    }
}