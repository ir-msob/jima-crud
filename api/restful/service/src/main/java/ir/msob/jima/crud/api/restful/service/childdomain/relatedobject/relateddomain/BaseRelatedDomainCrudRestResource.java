package ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relateddomain;

import ir.msob.jima.core.commons.childdomain.relatedobject.relateddomain.RelatedDomainAbstract;
import ir.msob.jima.core.commons.childdomain.relatedobject.relateddomain.RelatedDomainCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relateddomain.write.*;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;


public interface BaseRelatedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends RelatedDomainAbstract<ID>
        , CC extends RelatedDomainCriteriaAbstract<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>> extends
        BaseDeleteByIdRelatedDomainCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteByRelatedIdRelatedDomainCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteRelatedDomainCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteManyRelatedDomainCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseSaveRelatedDomainCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseSaveManyRelatedDomainCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseUpdateByIdRelatedDomainCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateByRelatedIdRelatedDomainCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateRelatedDomainCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateManyRelatedDomainCrudRestResource<ID, USER, CD, DTO, CS> {


}