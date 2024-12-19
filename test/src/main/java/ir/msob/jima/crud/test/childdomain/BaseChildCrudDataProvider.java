package ir.msob.jima.crud.test.childdomain;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;

public interface BaseChildCrudDataProvider<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends BaseChildDomain<ID>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>> {

    CD getNewChild();

    void updateChild(CD childDomain);

    CS getChildService();

}