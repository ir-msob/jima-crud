package ir.msob.jima.crud.api.grpc.service.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.commons.CriteriaJsonPatchMsg;
import ir.msob.jima.crud.api.grpc.commons.DtoMsg;
import ir.msob.jima.crud.api.grpc.service.ParentCrudGrpcResource;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import reactor.core.publisher.Mono;

import java.io.Serializable;


public interface BaseEditCrudGrpcResource<
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
    default Mono<DtoMsg> edit(Mono<CriteriaJsonPatchMsg> request) {
        crudValidation(Operations.EDIT);
        return request.flatMap(this::edit);
    }

    @Override
    @MethodStats
    default Mono<DtoMsg> edit(CriteriaJsonPatchMsg request) {
        crudValidation(Operations.EDIT);
        return getService().edit(convertToCriteria(request.getCriteria()), convertToJsonPatch(request.getJsonPatch()), getUser())
                .map(result -> DtoMsg.newBuilder()
                        .setDto(convertToString(result))
                        .build());
    }

}
