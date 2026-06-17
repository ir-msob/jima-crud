package ir.msob.jima.crud.grpc.reactive.test.resource.domain.write;

import ir.msob.jima.crud.grpc.reactive.proto.DtosMsg;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.ParentDomainCrudGrpcReactiveResourceTest;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.write.BaseUpdateManyDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.test.resource.domain.write.BaseUpdateManyDomainCrudResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseUpdateManyDomainCrudGrpcReactiveResourceTest} interface represents a set of gRPC-specific test methods for updating multiple entities.
 * It extends both the {@code BaseUpdateManyChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGrpcReactiveResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to update multiple entities using gRPC. The result of the update operation is a collection of DTOs of the updated entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseUpdateManyDomainCrudResourceTest
 * @see ParentDomainCrudGrpcReactiveResourceTest
 */
public interface BaseUpdateManyDomainCrudGrpcReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseUpdateManyDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Executes a gRPC request to update multiple entities and extracts the result from the response.
     *
     * @param dtos A collection of data transfer objects (DTOs) representing the entities to be updated.
     */
    @SneakyThrows
    @Override
    default void updateManyRequest(Collection<DTO> dtos, Assertable<Collection<DTO>> assertable) {
        // Create an instance of DtosMsg with the DTOs of the entities to be updated
        DtosMsg msg = DtosMsg.newBuilder()
                .addAllDtos(convertToStrings(dtos))
                .build();
        // Execute the gRPC request with the created DtosMsg and extract the result from the response
        Collection<DTO> res = getCrudServiceBlockingStub().updateMany(msg)
                .getDtosList()
                .stream()
                .map(this::convertToDto)
                .toList();
        assertable.assertThan(res);
    }
}