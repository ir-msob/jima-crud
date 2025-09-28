package ir.msob.jima.crud.api.grpc.test.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.grpc.commons.IdMsg;
import ir.msob.jima.crud.api.grpc.test.domain.ParentDomainCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.write.BaseDeleteByIdDomainCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseDeleteByIdDomainCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for deleting entities by their ID.
 * It extends both the {@code BaseDeleteByIdDomainCrudResourceTest} and {@code ParentDomainCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to delete an entity by its ID using gRPC. The result of the deletion operation is an {@code IdMsg}
 * representing the ID of the deleted entity.
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
 * @see BaseDeleteByIdDomainCrudResourceTest
 * @see ParentDomainCrudGrpcResourceTest
 */
public interface BaseDeleteByIdDomainCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseDeleteByIdDomainCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

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