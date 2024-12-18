package ir.msob.jima.crud.api.restful.service.childdomain.relatedaction;

import ir.msob.jima.core.commons.childdomain.relatedaction.RelatedActionAbstract;
import ir.msob.jima.core.commons.childdomain.relatedaction.RelatedActionCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.relatedaction.write.*;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;


public interface BaseRelatedActionCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends RelatedActionAbstract<ID>
        , CC extends RelatedActionCriteriaAbstract<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends
        BaseDeleteByIdRelatedActionCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteByNameRelatedActionCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteRelatedActionCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteManyRelatedActionCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseSaveRelatedActionCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseSaveManyRelatedActionCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseUpdateByIdRelatedActionCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateByNameRelatedActionCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateRelatedActionCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateManyRelatedActionCrudRestResource<ID, USER, CD, DTO, CS> {
}