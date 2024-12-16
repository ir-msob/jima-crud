package ir.msob.jima.crud.api.restful.test;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.test.read.*;
import ir.msob.jima.crud.api.restful.test.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;

import java.io.Serializable;

public interface BaseDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends
        BaseCountAllDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseCountDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetByIdDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetOneDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetPageDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteByIdDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditByIdDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateByIdDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateDomainCrudRestResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}
