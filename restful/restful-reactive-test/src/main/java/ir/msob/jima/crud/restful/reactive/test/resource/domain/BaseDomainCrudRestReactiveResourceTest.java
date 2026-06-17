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

public interface BaseDomainCrudRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {
}
