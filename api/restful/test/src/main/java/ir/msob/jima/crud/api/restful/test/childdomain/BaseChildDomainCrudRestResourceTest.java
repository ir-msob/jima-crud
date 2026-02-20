package ir.msob.jima.crud.api.restful.test.childdomain;

import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.test.childdomain.read.*;
import ir.msob.jima.crud.api.restful.test.childdomain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.childdomain.BaseChildDomainCrudDataProvider;

import java.io.Serializable;

public interface BaseChildDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
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
