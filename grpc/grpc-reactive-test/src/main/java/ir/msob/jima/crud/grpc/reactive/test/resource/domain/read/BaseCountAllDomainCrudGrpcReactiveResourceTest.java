package ir.msob.jima.crud.grpc.reactive.test.resource.domain.read;

import com.google.protobuf.Empty;
import ir.msob.jima.crud.grpc.reactive.proto.CountMsg;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.ParentDomainCrudGrpcReactiveResourceTest;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseCountAllDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.test.resource.domain.read.BaseCountAllDomainCrudResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseCountAllDomainCrudGrpcReactiveResourceTest} interface represents a set of gRPC-specific test methods for counting all entities.
 * It extends both the {@code BaseCountAllChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudGrpcReactiveResourceTest} interfaces, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to count all entities using gRPC. The result of the count operation is a {@code Long}
 * representing the total number of entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountAllDomainCrudResourceTest
 * @see ParentDomainCrudGrpcReactiveResourceTest
 */
public interface BaseCountAllDomainCrudGrpcReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseCountAllDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Executes a gRPC request to count all entities and extracts the result from the response.
     */
    @SneakyThrows
    @Override
    default void countAllRequest(Assertable<Long> assertable) {
        // Execute the gRPC request to count all entities and extract the result from the response
        CountMsg countMsg = getCrudServiceBlockingStub().countAll(Empty.newBuilder().build());
        assertable.assertThan(countMsg.getCount());
    }
}