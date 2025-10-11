package ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relatedprocess;

import ir.msob.jima.core.commons.childdomain.relatedobject.relatedprocess.RelatedProcessAbstract;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedprocess.RelatedProcessCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relatedprocess.write.*;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;


public interface BaseRelatedProcessCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends RelatedProcessAbstract<ID>
        , CC extends RelatedProcessCriteriaAbstract<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>> extends
        BaseDeleteByIdRelatedProcessCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteByRelatedIdRelatedProcessCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteRelatedProcessCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteManyRelatedProcessCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseSaveRelatedProcessCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseSaveManyRelatedProcessCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseUpdateByIdRelatedProcessCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateByRelatedIdRelatedProcessCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateRelatedProcessCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateManyRelatedProcessCrudRestResource<ID, USER, CD, DTO, CS> {

}