package ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relatedparty;

import ir.msob.jima.core.commons.childdomain.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedparty.RelatedPartyCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relatedparty.write.*;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;


public interface BaseRelatedPartyCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends RelatedPartyAbstract<ID>
        , CC extends RelatedPartyCriteriaAbstract<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>> extends
        BaseDeleteByIdRelatedPartyCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteByRelatedIdRelatedPartyCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteRelatedPartyCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteManyRelatedPartyCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseSaveRelatedPartyCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseSaveManyRelatedPartyCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseUpdateByIdRelatedPartyCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateByRelatedIdRelatedPartyCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateRelatedPartyCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateManyRelatedPartyCrudRestResource<ID, USER, CD, DTO, CS> {

}