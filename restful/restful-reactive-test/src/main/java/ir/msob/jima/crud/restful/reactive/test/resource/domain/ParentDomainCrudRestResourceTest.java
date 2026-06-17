package ir.msob.jima.crud.restful.reactive.test.resource.domain;

import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.restful.reactive.test.resource.BaseCoreRestReactiveResourceTest;

import java.io.Serializable;

public interface ParentDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>>
        extends BaseCoreRestReactiveResourceTest<ID, USER, D, DTO, C> {

    String getBaseUri();

}
