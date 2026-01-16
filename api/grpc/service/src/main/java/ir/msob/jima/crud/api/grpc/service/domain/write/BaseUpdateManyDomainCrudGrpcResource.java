package ir.msob.jima.crud.api.grpc.service.domain.write;

import io.grpc.stub.StreamObserver;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.commons.DtosMsg;
import ir.msob.jima.crud.api.grpc.service.domain.ParentDomainCrudGrpcResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;

import java.io.Serializable;

/**
 * Interface for a gRPC resource that provides a method to update multiple entities.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <R>    The type of the Repository, which must extend BaseDomainCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseDomainCrudService.
 */
public interface BaseUpdateManyDomainCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>>
        extends ParentDomainCrudGrpcResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseUpdateManyDomainCrudGrpcResource.class);


    @MethodStats
    @Scope(operation = Operations.UPDATE_MANY)
    @Override
    default void updateMany(DtosMsg request, StreamObserver<DtosMsg> responseObserver) {
        logger.debug("Request to update many: dto {}", request);
        getService().updateMany(convertToDtos(request.getDtosList()), getUser())
                .map(result -> DtosMsg.newBuilder()
                        .addAllDtos(convertToStrings(result))
                        .build())
                .subscribe(
                        responseObserver::onNext,
                        responseObserver::onError,
                        responseObserver::onCompleted
                );
    }
}