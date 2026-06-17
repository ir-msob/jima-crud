package ir.msob.jima.crud.restful.reactive.test.resource.childdomain;

import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.restful.reactive.test.resource.BaseCoreRestReactiveResourceTest;

import java.io.Serializable;

public interface ParentChildDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>>
        extends BaseCoreRestReactiveResourceTest<ID, USER, D, DTO, C> {

    String getDomainUri();

    String getChildDomainUri();

}
