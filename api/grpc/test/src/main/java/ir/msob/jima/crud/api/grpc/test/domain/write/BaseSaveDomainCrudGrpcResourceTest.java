package ir.msob.jima.crud.api.grpc.test.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.grpc.commons.DtoMsg;
import ir.msob.jima.crud.api.grpc.test.domain.ParentDomainCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseSaveDomainCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseSaveDomainCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for saving an entity.
 * It extends both the {@code BaseSaveDomainCrudResourceTest} and {@code ParentDomainCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to save an entity using gRPC. The result of the save operation is the DTO of the saved entity.
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
 * @see BaseSaveDomainCrudResourceTest
 * @see ParentDomainCrudGrpcResourceTest
 */
public interface BaseSaveDomainCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseSaveDomainCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Executes a gRPC request to save an entity and extracts the result from the response.
     *
     * @param dto The data transfer object (DTO) representing the entity to be saved.
     */
    @SneakyThrows
    @Override
    default void saveRequest(DTO dto, Assertable<DTO> assertable) {
        // Create an instance of DtoMsg with the DTO of the entity to be saved
        DtoMsg msg = DtoMsg.newBuilder()
                .setDto(convertToString(dto))
                .build();
        // Execute the gRPC request with the created DtoMsg and extract the result from the response
        DtoMsg res = getCrudServiceBlockingStub().save(msg);
        // Convert the result to the DTO type
        assertable.assertThan(convertToDto(res.getDto()));
    }
}