package ir.msob.jima.crud.grpc.reactive.test.resource.domain.read;

import ir.msob.jima.crud.grpc.reactive.proto.DtoMsg;
import ir.msob.jima.crud.grpc.reactive.proto.IdMsg;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.ParentDomainCrudGrpcReactiveResourceTest;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseGetByIdDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.test.resource.domain.read.BaseGetByIdDomainCrudResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseGetByIdDomainCrudGrpcReactiveResourceTest} interface represents a set of gRPC-specific test methods for retrieving entities by their ID.
 * It extends both the {@code BaseGetByIdChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGrpcReactiveResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve an entity by its ID using gRPC. The result of the retrieval operation is a {@code DtoMsg}
 * representing the retrieved entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetByIdDomainCrudResourceTest
 * @see ParentDomainCrudGrpcReactiveResourceTest
 */
public interface BaseGetByIdDomainCrudGrpcReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetByIdDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Executes a gRPC request to retrieve an entity by its ID and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity.
     */
    @SneakyThrows
    @Override
    default void getByIdRequest(DTO savedDto, Assertable<DTO> assertable) {
        // Create an instance of IdMsg with the ID of the saved entity
        IdMsg msg = IdMsg.newBuilder()
                .setId(convertToString(savedDto.getId()))
                .build();
        // Execute the gRPC request with the created IdMsg and extract the result from the response
        DtoMsg res = getCrudServiceBlockingStub().getById(msg);
        // Convert the result to the DTO type
        assertable.assertThan(convertToDto(res.getDto()));
    }
}