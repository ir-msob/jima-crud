package ir.msob.jima.crud.api.grpc.service.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.scope.Scope;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.commons.CriteriaMsg;
import ir.msob.jima.crud.api.grpc.commons.IdsMsg;
import ir.msob.jima.crud.api.grpc.service.ParentCrudGrpcResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * Interface for a gRPC resource that provides a method to delete multiple entities based on a given criteria.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <Q>    The type of the Query, which must extend BaseQuery.
 * @param <R>    The type of the Repository, which must extend BaseCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseCrudService.
 */
public interface BaseDeleteManyCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>>
        extends ParentCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteManyCrudGrpcResource.class);

    /**
     * Handles a request to delete multiple entities based on a given criteria.
     *
     * @param request The request, which contains the criteria.
     * @return A Mono that emits the IDs of the deleted entities.
     */
    @Override
    @MethodStats
    @Scope(Operations.DELETE_MANY)
    default Mono<IdsMsg> deleteMany(Mono<CriteriaMsg> request) {
        return request.flatMap(this::deleteMany);
    }

    /**
     * Handles a request to delete multiple entities based on a given criteria.
     *
     * @param request The request, which contains the criteria.
     * @return A Mono that emits the IDs of the deleted entities.
     */
    @Override
    @MethodStats
    @Scope(Operations.DELETE_MANY)
    default Mono<IdsMsg> deleteMany(CriteriaMsg request) {
        log.debug("Request to delete many: dto {}", request);
        return getService().deleteMany(convertToCriteria(request.getCriteria()), getUser())
                .map(result -> IdsMsg.newBuilder()
                        .addAllIds(convertToStrings(result))
                        .build());
    }

}