package ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedintegration;

import ir.msob.jima.core.commons.child.relatedobject.relatedintegration.RelatedIntegrationAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relatedintegration.RelatedIntegrationCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedintegration.write.*;
import ir.msob.jima.crud.service.child.relatedobject.relatedintegration.BaseRelatedIntegrationCrudService;

import java.io.Serializable;


public interface BaseRelatedIntegrationCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RI extends RelatedIntegrationAbstract<ID>
        , C extends RelatedIntegrationCriteriaAbstract<ID, RI>
        , S extends BaseRelatedIntegrationCrudService<ID, USER, DTO, RI, C>
        > extends
        BaseDeleteByIdRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S>
        , BaseDeleteByRelatedIdRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S>
        , BaseDeleteRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S>
        , BaseDeleteManyRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S>
        , BaseSaveRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S>
        , BaseSaveManyRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S>
        , BaseUpdateByIdRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S>
        , BaseUpdateByRelatedIdRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S>
        , BaseUpdateRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S>
        , BaseUpdateManyRelatedIntegrationCrudRestResource<ID, USER, DTO, RI, C, S> {


}