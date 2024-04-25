package ir.msob.jima.crud.api.restful.test;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.test.read.*;
import ir.msob.jima.crud.api.restful.test.write.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;

import java.io.Serializable;

public interface BaseCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends
        BaseCountAllCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseCountCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetManyCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetByIdCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetOneCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetPageCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteByIdCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteManyCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditManyCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditByIdCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveManyCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateManyCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateByIdCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}
