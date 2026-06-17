package ir.msob.jima.crud.grpc.test.service.domain.write;

import ir.msob.jima.crud.grpc.reactive.proto.IdMsg;
import ir.msob.jima.crud.grpc.test.service.domain.ParentDomainCrudGrpcTestResource;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.test.dataprovider.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.resource.domain.write.BaseDeleteByIdDomainCrudTestResource;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseDeleteByIdDomainCrudGrpcTestResource} interface represents a set of gRPC-specific test methods for deleting entities by their ID.
 * It extends both the {@code BaseDeleteByIdChildDomainCrudTestResource} and {@code ParentDomainCrudGrpcTestResource} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to delete an entity by its ID using gRPC. The result of the deletion operation is an {@code IdMsg}
 * representing the ID of the deleted entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseDeleteByIdDomainCrudTestResource
 * @see ParentDomainCrudGrpcTestResource
 */
public interface BaseDeleteByIdDomainCrudGrpcTestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseDeleteByIdDomainCrudTestResource<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Executes a gRPC request to delete an entity by its ID and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity.
     * @throws DomainNotFoundException If the entity to be deleted is not found.
     * @throws BadRequestException     If the request to delete the entity is not valid.
     */
    @SneakyThrows
    @Override
    default void deleteByIdRequest(DTO savedDto, Assertable<ID> assertable) throws DomainNotFoundException, BadRequestException {
        // Create an instance of IdMsg with the ID of the saved entity
        IdMsg msg = IdMsg.newBuilder()
                .setId(convertToString(savedDto.getId()))
                .build();
        // Execute the gRPC request with the created IdMsg and extract the result from the response
        IdMsg res = getCrudServiceBlockingStub().deleteById(msg);
        // Convert the result to the ID type
        assertable.assertThan(convertToId(res.getId()));
    }
}