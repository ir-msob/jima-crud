package ir.msob.jima.crud.api.grpc.service.read;

import com.google.protobuf.Empty;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.grpc.commons.CountMsg;
import ir.msob.jima.crud.api.grpc.service.ParentCrudGrpcResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * Interface for a gRPC resource that provides a count of all entities.
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
public interface BaseCountAllCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>>
        extends ParentCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseCountAllCrudGrpcResource.class);

    /**
     * Handles a request to count all entities.
     *
     * @param request The request, which is expected to be empty.
     * @return A Mono that emits the count of all entities.
     */
    @Override
    @MethodStats
    @Scope(Operations.COUNT_ALL)
    default Mono<CountMsg> countAll(Mono<Empty> request) {
        return request.flatMap(this::countAll);
    }

    /**
     * Handles a request to count all entities.
     *
     * @param request The request, which is expected to be empty.
     * @return A Mono that emits the count of all entities.
     */
    @Override
    @MethodStats
    @Scope(Operations.COUNT_ALL)
    default Mono<CountMsg> countAll(Empty request) {
        log.debug("Request to count all: dto {}", request);
        return getService().countAll(getUser())
                .map(result -> CountMsg.newBuilder()
                        .setCount(result)
                        .build());
    }

}