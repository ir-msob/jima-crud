package ir.msob.jima.crud.api.grpc.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.grpc.commons.DtoMsg;
import ir.msob.jima.crud.api.grpc.test.ParentCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.write.BaseUpdateByIdCrudResourceTest;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The {@code BaseUpdateByIdCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for updating entities by their ID.
 * It extends both the {@code BaseUpdateByIdCrudResourceTest} and {@code ParentCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to update an entity by its ID using gRPC. The result of the update operation is a {@code DtoMsg}
 * representing the updated entity.
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
 * @see BaseUpdateByIdCrudResourceTest
 * @see ParentCrudGrpcResourceTest
 */
public interface BaseUpdateByIdCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseUpdateByIdCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Executes a gRPC request to update an entity by its ID and extracts the result from the response.
     *
     * @param dto The data transfer object (DTO) representing the entity to be updated.
     */
    @SneakyThrows
    @Override
    default void updateByIdRequest(DTO dto, Assertable<DTO> assertable) {
        // Create an instance of DtoMsg with the ID and DTO of the entity to be updated
        DtoMsg msg = DtoMsg.newBuilder()
                .setId(convertToString(dto.getDomainId()))
                .setDto(convertToString(dto))
                .build();
        // Execute the gRPC request with the created DtoMsg and extract the result from the response
        DtoMsg res = getReactorCrudServiceStub().updateById(Mono.just(msg))
                .toFuture()
                .get();
        // Convert the result to the DTO type
        assertable.assertThan(convertToDto(res.getDto()));
    }
}