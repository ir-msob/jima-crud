package ir.msob.jima.crud.api.grpc.test.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.grpc.commons.CriteriaJsonPatchMsg;
import ir.msob.jima.crud.api.grpc.commons.DtoMsg;
import ir.msob.jima.crud.api.grpc.test.ParentCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.write.BaseEditCrudResourceTest;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The {@code BaseEditCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for editing an entity based on a given criteria.
 * It extends both the {@code BaseEditCrudResourceTest} and {@code ParentCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to edit an entity based on a given criteria using gRPC. The result of the edit operation is the updated DTO of the entity.
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
 * @see BaseEditCrudResourceTest
 * @see ParentCrudGrpcResourceTest
 */
public interface BaseEditCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Executes a gRPC request to edit an entity based on a given criteria and extracts the result from the response.
     *
     * @param savedDto  The data transfer object (DTO) representing the saved entity.
     * @param jsonPatch The JSON Patch representing the changes to be applied to the entity.
     * @return The updated data transfer object (DTO) representing the edited entity.
     */
    @SneakyThrows
    @Override
    default DTO editRequest(DTO savedDto, JsonPatch jsonPatch) {
        // Create an instance of CriteriaJsonPatchMsg with the ID of the saved entity and the JSON Patch
        CriteriaJsonPatchMsg msg = CriteriaJsonPatchMsg.newBuilder()
                .setCriteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId())))
                .setJsonPatch(convertToString(jsonPatch))
                .build();
        // Execute the gRPC request with the created CriteriaJsonPatchMsg and extract the result from the response
        DtoMsg res = getReactorCrudServiceStub().edit(Mono.just(msg))
                .toFuture()
                .get();
        // Convert the result to the DTO type and return it
        return convertToDto(res.getDto());
    }
}