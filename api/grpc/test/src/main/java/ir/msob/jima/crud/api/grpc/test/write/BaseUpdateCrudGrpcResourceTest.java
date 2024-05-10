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
import ir.msob.jima.crud.test.write.BaseUpdateCrudResourceTest;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The {@code BaseUpdateCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for updating an entity.
 * It extends both the {@code BaseUpdateCrudResourceTest} and {@code ParentCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to update an entity using gRPC. The result of the update operation is the DTO of the updated entity.
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
 * @see BaseUpdateCrudResourceTest
 * @see ParentCrudGrpcResourceTest
 */
public interface BaseUpdateCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseUpdateCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Executes a gRPC request to update an entity and extracts the result from the response.
     *
     * @param dto The data transfer object (DTO) representing the entity to be updated.
     */
    @SneakyThrows
    @Override
    default void updateRequest(DTO dto, Assertable<DTO> assertable) {
        // Create an instance of DtoMsg with the DTO of the entity to be updated
        DtoMsg msg = DtoMsg.newBuilder()
                .setDto(convertToString(dto))
                .build();
        // Execute the gRPC request with the created DtoMsg and extract the result from the response
        DtoMsg res = getReactorCrudServiceStub().update(Mono.just(msg))
                .toFuture()
                .get();
        // Convert the result to the DTO type and return it
        assertable.assertThan(convertToDto(res.getDto()));
    }
}