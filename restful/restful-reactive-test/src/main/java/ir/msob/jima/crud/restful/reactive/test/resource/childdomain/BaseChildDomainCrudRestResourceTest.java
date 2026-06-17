package ir.msob.jima.crud.restful.reactive.test.resource.childdomain;

import ir.msob.jima.crud.reactive.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.reactive.test.dataprovider.childdomain.BaseChildDomainCrudDataProvider;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.read.*;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.write.*;
import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

public interface BaseChildDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateChildDomainCrudRestResourceTest<ID, USER, D, DTO, C, R, S, DP> {
}
