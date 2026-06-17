package ir.msob.jima.crud.restful.reactive.test.resource.childdomain;

import ir.msob.jima.crud.reactive.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.reactive.test.dataprovider.childdomain.BaseChildDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.read.*;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.write.*;
import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

public interface BaseChildDomainCrudRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateChildDomainCrudRestReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {
}
