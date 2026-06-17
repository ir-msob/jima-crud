package ir.msob.jima.crud.restful.reactive.resource.embeddeddomain;

import ir.msob.jima.crud.api.resource.BaseCrudResource;
import ir.msob.jima.crud.reactive.service.embeddeddomain.BaseEmbeddedDomainCrudReactiveService;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.embeddeddomain.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.util.GenericTypeUtil;
import ir.msob.jima.platform.restful.reactive.resource.BaseRestReactiveResource;

import java.io.Serializable;


public interface ParentEmbeddedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , ED extends BaseEmbeddedDomain<ID>
        , DTO extends BaseDomainDto<ID>
        , EDS extends BaseEmbeddedDomainCrudReactiveService<ID, USER, DTO>>
        extends BaseRestReactiveResource<ID, USER>, BaseCrudResource<ID, USER> {

    default Class<ED> getEmbeddedDomainClass() {
        return (Class<ED>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentEmbeddedDomainCrudRestResource.class, 2);
    }

    EDS getEmbeddedDomainService();

}