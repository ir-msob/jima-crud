package ir.msob.jima.crud.grpc.reactive.service.domain.read;

import io.grpc.stub.StreamObserver;
import ir.msob.jima.crud.grpc.reactive.proto.CriteriaPageableMsg;
import ir.msob.jima.crud.grpc.reactive.proto.PageMsg;
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
import ir.msob.jima.platform.api.shared.PageDto;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

/**
 * Interface for a gRPC resource that provides a method to get a page of entities based on a given criteria.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDomainDto.
 * @param <C>    The type of the Criteria, which must extend BaseDomainCriteria.
 * @param <R>    The type of the Repository, which must extend BaseReactiveRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface BaseGetPageDomainCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>>
        extends ParentDomainCrudGrpcResource<ID, USER, D, DTO, C, R, S> {

    Logger logger = LoggerFactory.getLogger(BaseGetPageDomainCrudGrpcResource.class);

    @MethodStats
    @Scope(operation = Operations.GET_PAGE)
    @Override
    default void getPage(CriteriaPageableMsg request, StreamObserver<PageMsg> responseObserver) {
        logger.debug("Request to get page: dto {}", request);
        getService().getPage(convertToCriteria(request.getCriteria()), convertToPageableDto(request.getPageable()).toPageable(), getUser())
                .map(result -> PageMsg.newBuilder()
                        .setPage(convertToString(PageDto.from(result)))
                        .build())
                .subscribe(
                        responseObserver::onNext,
                        responseObserver::onError,
                        responseObserver::onCompleted
                );
    }
}