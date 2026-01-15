package ir.msob.jima.crud.api.restful.test.domain;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.test.domain.read.*;
import ir.msob.jima.crud.api.restful.test.domain.write.*;
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
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP> {
}
