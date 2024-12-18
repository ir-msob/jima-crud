package ir.msob.jima.crud.api.grpc.service.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.commons.DtosMsg;
import ir.msob.jima.crud.api.grpc.service.domain.ParentDomainCrudGrpcResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * Interface for a gRPC resource that provides a method to update multiple entities.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <Q>    The type of the Query, which must extend BaseQuery.
 * @param <R>    The type of the Repository, which must extend BaseDomainCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseDomainCrudService.
 */
public interface BaseUpdateManyDomainCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>>
        extends ParentDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseUpdateManyDomainCrudGrpcResource.class);

    /**
     * Handles a request to update multiple entities.
     *
     * @param request The request, which contains the entities to be updated.
     * @return A Mono that emits the updated entities.
     */
    @Override
    @MethodStats
    @Scope(operation = Operations.UPDATE_MANY)
    default Mono<DtosMsg> updateMany(Mono<DtosMsg> request) {
        return request.flatMap(this::updateMany);
    }

    /**
     * Handles a request to update multiple entities.
     *
     * @param request The request, which contains the entities to be updated.
     * @return A Mono that emits the updated entities.
     */
    @Override
    @MethodStats
    @Scope(operation = Operations.UPDATE_MANY)
    default Mono<DtosMsg> updateMany(DtosMsg request) {
        log.debug("Request to update many: dto {}", request);
        return getService().updateMany(convertToDtos(request.getDtosList()), getUser())
                .map(result -> DtosMsg.newBuilder()
                        .addAllDtos(convertToStrings(result))
                        .build());
    }

}