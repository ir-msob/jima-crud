package ir.msob.jima.crud.api.grpc.service.read;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.commons.CriteriaPageableMsg;
import ir.msob.jima.crud.api.grpc.commons.PageMsg;
import ir.msob.jima.crud.api.grpc.service.ParentCrudGrpcResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import reactor.core.publisher.Mono;

import java.io.Serializable;


public interface BaseGetPageCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends ParentCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S> {

    @Override
    @MethodStats
    default Mono<PageMsg> getPage(Mono<CriteriaPageableMsg> request) {
        crudValidation(Operations.GET_PAGE);
        return request.flatMap(this::getPage);
    }

    @Override
    @MethodStats
    default Mono<PageMsg> getPage(CriteriaPageableMsg request) {
        crudValidation(Operations.GET_PAGE);
        return getService().getPage(convertToCriteria(request.getCriteria()), convertToPageable(request.getPageable()), getUser())
                .map(result -> PageMsg.newBuilder()
                        .setPage(convertToString(result))
                        .build());
    }

}
