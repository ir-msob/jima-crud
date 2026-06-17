package ir.msob.jima.crud.grpc.reactive.service.domain.read;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import ir.msob.jima.crud.grpc.reactive.proto.CountMsg;
import ir.msob.jima.crud.grpc.reactive.service.domain.ParentDomainCrudGrpcResource;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.scope.Scope;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

//import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;

/**
 * Interface for a gRPC resource that provides a count of all entities.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDomainDto.
 * @param <C>    The type of the Criteria, which must extend BaseDomainCriteria.
 * @param <R>    The type of the Repository, which must extend BaseReactiveRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface BaseCountAllDomainCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>>
        extends ParentDomainCrudGrpcResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseCountAllDomainCrudGrpcResource.class);


    @MethodStats
    @Scope(operation = Operations.COUNT_ALL)
    @Override
    default void countAll(Empty request, StreamObserver<CountMsg> responseObserver) {
        logger.debug("Request to count all: dto {}", request);
        getService().countAll(getUser())
                .map(result -> CountMsg.newBuilder()
                        .setCount(result)
                        .build())
                .subscribe(
                        responseObserver::onNext,
                        responseObserver::onError,
                        responseObserver::onCompleted
                );
    }
}