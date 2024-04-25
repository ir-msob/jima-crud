package ir.msob.jima.crud.api.restful.test;

import ir.msob.jima.core.api.restful.test.BaseCoreRestResourceTest;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;

import java.io.Serializable;

public interface ParentCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>>
        extends BaseCoreRestResourceTest<ID, USER, D, DTO, C> {

    String getBaseUri();

}
