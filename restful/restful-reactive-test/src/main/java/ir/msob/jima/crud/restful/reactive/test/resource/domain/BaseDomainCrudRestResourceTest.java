package ir.msob.jima.crud.restful.reactive.test.resource.domain;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.read.*;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.write.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

public interface BaseDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
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
