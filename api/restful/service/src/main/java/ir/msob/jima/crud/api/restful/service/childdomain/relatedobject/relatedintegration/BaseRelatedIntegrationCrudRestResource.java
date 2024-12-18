package ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relatedintegration;

import ir.msob.jima.core.commons.childdomain.relatedobject.relatedintegration.RelatedIntegrationAbstract;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedintegration.RelatedIntegrationCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.relatedobject.relatedintegration.write.*;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;


public interface BaseRelatedIntegrationCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends RelatedIntegrationAbstract<ID>
        , CC extends RelatedIntegrationCriteriaAbstract<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>> extends
        BaseDeleteByIdRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteByRelatedIdRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteManyRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseSaveRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseSaveManyRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateByIdRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateByRelatedIdRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateManyRelatedIntegrationCrudRestResource<ID, USER, CD, CC, DTO, CS> {


}