package ir.msob.jima.crud.grpc.test.service.domain.write;

import ir.msob.jima.crud.grpc.reactive.proto.DtoMsg;
import ir.msob.jima.crud.grpc.test.service.domain.ParentDomainCrudGrpcTestResource;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.test.dataprovider.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.resource.domain.write.BaseSaveDomainCrudTestResource;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseSaveDomainCrudGrpcTestResource} interface represents a set of gRPC-specific test methods for saving an entity.
 * It extends both the {@code BaseSaveChildDomainCrudTestResource} and {@code ParentDomainCrudGrpcTestResource} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to save an entity using gRPC. The result of the save operation is the DTO of the saved entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseSaveDomainCrudTestResource
 * @see ParentDomainCrudGrpcTestResource
 */
public interface BaseSaveDomainCrudGrpcTestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseSaveDomainCrudTestResource<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP> {

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