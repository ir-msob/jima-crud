package ir.msob.jima.crud.api.restful.test.childdomain;

import ir.msob.jima.core.api.restful.test.BaseCoreRestResourceTest;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.security.BaseUser;

import java.io.Serializable;

public interface ParentChildDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>>
        extends BaseCoreRestResourceTest<ID, USER, D, DTO, C> {

    String getDomainUri();

    String getChildDomainUri();

}
