package ir.msob.jima.crud.api.grpc.service.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.commons.IdMsg;
import ir.msob.jima.crud.api.grpc.service.domain.ParentDomainCrudGrpcResource;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * Interface for a gRPC resource that provides a method to delete an entity by its ID.
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
public interface BaseDeleteByIdDomainCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>>
        extends ParentDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseDeleteByIdDomainCrudGrpcResource.class);

    /**
     * Handles a request to delete an entity by its ID.
     *
     * @param request The request, which contains the ID of the entity.
     * @return A Mono that emits the ID of the deleted entity.
     */
    @Override
    @MethodStats
    @Scope(operation = Operations.DELETE_BY_ID)
    default Mono<IdMsg> deleteById(Mono<IdMsg> request) {
        return request.flatMap(this::deleteById);
    }

    /**
     * Handles a request to delete an entity by its ID.
     *
     * @param request The request, which contains the ID of the entity.
     * @return A Mono that emits the ID of the deleted entity.
     */
    @Override
    @MethodStats
    @Scope(operation = Operations.DELETE_BY_ID)
    default Mono<IdMsg> deleteById(IdMsg request) {
        log.debug("Request to delete by id: dto {}", request);
        return getService().delete(convertToId(request.getId()), getUser())
                .map(result -> IdMsg.newBuilder()
                        .setId(convertToString(result))
                        .build());
    }

}