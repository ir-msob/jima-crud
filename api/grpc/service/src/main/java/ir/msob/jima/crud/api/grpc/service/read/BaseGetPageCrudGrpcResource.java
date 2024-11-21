package ir.msob.jima.crud.api.grpc.service.read;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.scope.Scope;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.grpc.commons.CriteriaPageableMsg;
import ir.msob.jima.crud.api.grpc.commons.PageMsg;
import ir.msob.jima.crud.api.grpc.service.ParentCrudGrpcResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * Interface for a gRPC resource that provides a method to get a page of entities based on a given criteria.
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
public interface BaseGetPageCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>>
        extends ParentCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S> {

    Logger log = LoggerFactory.getLogger(BaseGetPageCrudGrpcResource.class);

    /**
     * Handles a request to get a page of entities based on a given criteria.
     *
     * @param request The request, which contains the criteria and the details of the page.
     * @return A Mono that emits the page of entities that match the criteria.
     */
    @Override
    @MethodStats
    @Scope(Operations.GET_PAGE)
    default Mono<PageMsg> getPage(Mono<CriteriaPageableMsg> request) {
        return request.flatMap(this::getPage);
    }

    /**
     * Handles a request to get a page of entities based on a given criteria.
     *
     * @param request The request, which contains the criteria and the details of the page.
     * @return A Mono that emits the page of entities that match the criteria.
     */
    @Override
    @MethodStats
    @Scope(Operations.GET_PAGE)
    default Mono<PageMsg> getPage(CriteriaPageableMsg request) {
        log.debug("Request to get page: dto {}", request);
        return getService().getPage(convertToCriteria(request.getCriteria()), convertToPageable(request.getPageable()), getUser())
                .map(result -> PageMsg.newBuilder()
                        .setPage(convertToString(result))
                        .build());
    }

}