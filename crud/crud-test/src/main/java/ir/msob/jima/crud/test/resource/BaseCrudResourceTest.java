package ir.msob.jima.crud.test.resource;

import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.domain.dto.BaseDtoTypeReference;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.test.resource.BaseResourceTest;

import java.io.Serializable;


public interface BaseCrudResourceTest<ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>>
        extends BaseResourceTest<ID, USER, D, DTO, C>,
        BaseDtoTypeReference<ID, DTO, C> {


}