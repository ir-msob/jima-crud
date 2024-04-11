package ir.msob.jima.crud.api.grpc.test.write;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.commons.DtosMsg;
import ir.msob.jima.crud.api.grpc.test.ParentCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.write.BaseUpdateManyCrudResourceTest;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseUpdateManyCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for updating multiple entities.
 * It extends both the {@code BaseUpdateManyCrudResourceTest} and {@code ParentCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to update multiple entities using gRPC. The result of the update operation is a collection of DTOs of the updated entities.
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
 * @see BaseUpdateManyCrudResourceTest
 * @see ParentCrudGrpcResourceTest
 */
public interface BaseUpdateManyCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseUpdateManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Executes a gRPC request to update multiple entities and extracts the result from the response.
     *
     * @param dtos A collection of data transfer objects (DTOs) representing the entities to be updated.
     * @return A collection of data transfer objects (DTOs) representing the updated entities.
     */
    @SneakyThrows
    @Override
    default Collection<DTO> updateManyRequest(Collection<DTO> dtos) {
        // Create an instance of DtosMsg with the DTOs of the entities to be updated
        DtosMsg msg = DtosMsg.newBuilder()
                .addAllDtos(convertToStrings(dtos))
                .build();
        // Execute the gRPC request with the created DtosMsg and extract the result from the response
        return getReactorCrudServiceStub().updateMany(Mono.just(msg))
                .toFuture()
                .get()
                .getDtosList()
                .stream()
                .map(this::convertToDto)
                .toList();
    }
}