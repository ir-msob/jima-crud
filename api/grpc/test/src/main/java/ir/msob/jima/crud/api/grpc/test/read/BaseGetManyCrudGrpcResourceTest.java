package ir.msob.jima.crud.api.grpc.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.grpc.commons.CriteriaMsg;
import ir.msob.jima.crud.api.grpc.test.ParentCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseGetManyCrudResourceTest;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * The {@code BaseGetManyCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for retrieving multiple entities based on a given criteria.
 * It extends both the {@code BaseGetManyCrudResourceTest} and {@code ParentCrudGrpcResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve multiple entities based on a given criteria using gRPC. The result of the retrieval operation is a {@code Collection<DTO>}
 * representing the entities that match the given criteria.
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
 * @see BaseGetManyCrudResourceTest
 * @see ParentCrudGrpcResourceTest
 */
public interface BaseGetManyCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Executes a gRPC request to retrieve multiple entities based on a given criteria and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the saved entity.
     */
    @SneakyThrows
    @Override
    default void getManyRequest(DTO savedDto, Assertable<Collection<DTO>> assertable) {
        // Create an instance of CriteriaMsg with the ID of the saved entity
        CriteriaMsg msg = CriteriaMsg.newBuilder()
                .setCriteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId())))
                .build();
        // Execute the gRPC request with the created CriteriaMsg and extract the result from the response
        Collection<DTO> dtos = getReactorCrudServiceStub().getMany(Mono.just(msg))
                .toFuture()
                .get()
                .getDtosList()
                .stream()
                .map(this::convertToDto)
                .toList();
        assertable.assertThan(dtos);
    }
}