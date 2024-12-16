package ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedintegration;

import ir.msob.jima.core.commons.child.relatedobject.relatedintegration.BaseRelatedIntegrationContainer;
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

        , RI extends RelatedIntegrationAbstract<ID>
        , C extends RelatedIntegrationCriteriaAbstract<ID, RI>
        , CNT extends BaseRelatedIntegrationContainer<ID, RI>

        , DTO extends BaseDto<ID> & BaseRelatedIntegrationContainer<ID, RI>

        , S extends BaseRelatedIntegrationCrudService<ID, USER, RI, C, CNT, DTO>
        > extends
        BaseDeleteByIdRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S>
        , BaseDeleteByRelatedIdRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S>
        , BaseDeleteRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S>
        , BaseDeleteManyRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S>
        , BaseSaveRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S>
        , BaseSaveManyRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S>
        , BaseUpdateByIdRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S>
        , BaseUpdateByRelatedIdRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S>
        , BaseUpdateRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S>
        , BaseUpdateManyRelatedIntegrationCrudRestResource<ID, USER, RI, C, CNT, DTO, S> {


}