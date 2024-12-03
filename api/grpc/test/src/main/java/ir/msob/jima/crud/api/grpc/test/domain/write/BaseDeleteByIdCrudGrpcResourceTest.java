package ir.msob.jima.crud.api.grpc.test.domain.write;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.grpc.commons.IdMsg;
import ir.msob.jima.crud.api.grpc.test.domain.ParentCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.write.BaseDeleteByIdCrudResourceTest;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * The {@code BaseDeleteByIdCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for deleting entities by their ID.
 * It extends both the {@code BaseDeleteByIdCrudResourceTest} and {@code ParentCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
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
 * @see BaseDeleteByIdCrudResourceTest
 * @see ParentCrudGrpcResourceTest
 */
public interface BaseDeleteByIdCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseDeleteByIdCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

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
        IdMsg res = getReactorCrudServiceStub().deleteById(Mono.just(msg))
                .toFuture()
                .get();
        // Convert the result to the ID type
        assertable.assertThan(convertToId(res.getId()));
    }
}