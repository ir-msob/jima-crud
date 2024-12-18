package ir.msob.jima.crud.api.restful.service.childdomain;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;


public interface ParentChildCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends BaseChildDomain<ID>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>>
        extends BaseRestResource<ID, USER>, BaseCrudResource {

    default Class<CD> getChildDomainClass() {
        return (Class<CD>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentChildCrudRestResource.class, 2);
    }

    CS getChildService();

}