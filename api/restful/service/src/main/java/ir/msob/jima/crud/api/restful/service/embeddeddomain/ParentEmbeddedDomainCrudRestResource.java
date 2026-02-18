package ir.msob.jima.crud.api.restful.service.embeddeddomain;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.service.embeddeddomain.BaseEmbeddedDomainCrudService;

import java.io.Serializable;


public interface ParentEmbeddedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , ED extends BaseEmbeddedDomain<ID>
        , DTO extends BaseDto<ID>
        , EDS extends BaseEmbeddedDomainCrudService<ID, USER, DTO>>
        extends BaseRestResource<ID, USER>, BaseCrudResource {

    default Class<ED> getEmbeddedDomainClass() {
        return (Class<ED>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentEmbeddedDomainCrudRestResource.class, 2);
    }

    EDS getEmbeddedDomainService();

}