package ir.msob.jima.crud.api.grpc.service.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.commons.CriteriaJsonPatchMsg;
import ir.msob.jima.crud.api.grpc.commons.DtosMsg;
import ir.msob.jima.crud.api.grpc.service.ParentCrudGrpcResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import reactor.core.publisher.Mono;

import java.io.Serializable;


public interface BaseEditManyCrudGrpcResource<
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
    default Mono<DtosMsg> editMany(Mono<CriteriaJsonPatchMsg> request) {
        crudValidation(Operations.EDIT_MANY);
        return request.flatMap(this::editMany);
    }

    @Override
    @MethodStats
    default Mono<DtosMsg> editMany(CriteriaJsonPatchMsg request) {
        crudValidation(Operations.EDIT_MANY);
        return getService().editMany(convertToCriteria(request.getCriteria()), convertToJsonPatch(request.getJsonPatch()), getUser())
                .map(result -> DtosMsg.newBuilder()
                        .addAllDtos(convertToStrings(result))
                        .build());
    }

}
