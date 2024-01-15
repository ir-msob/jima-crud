package ir.msob.jima.crud.api.grpc.test;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.test.read.*;
import ir.msob.jima.crud.api.grpc.test.write.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;

import java.io.Serializable;

public interface BaseCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends
        BaseCountAllCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseCountCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetOneCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetPageCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}
