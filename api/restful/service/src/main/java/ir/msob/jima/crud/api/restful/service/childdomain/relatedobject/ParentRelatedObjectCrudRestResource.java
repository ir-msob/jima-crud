package ir.msob.jima.crud.api.restful.service.childdomain.relatedobject;

import ir.msob.jima.core.commons.childdomain.relatedobject.BaseRelatedObject;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.ParentChildCrudRestResource;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;

public interface ParentRelatedObjectCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , RID extends Comparable<RID> & Serializable
        , USER extends BaseUser
        , CD extends BaseRelatedObject<ID, RID>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>>
        extends ParentChildCrudRestResource<ID, USER, CD, DTO, CS> {
}