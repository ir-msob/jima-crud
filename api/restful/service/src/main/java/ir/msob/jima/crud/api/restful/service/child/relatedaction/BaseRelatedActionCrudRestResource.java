package ir.msob.jima.crud.api.restful.service.child.relatedaction;

import ir.msob.jima.core.commons.child.relatedaction.BaseRelatedActionContainer;
import ir.msob.jima.core.commons.child.relatedaction.RelatedActionAbstract;
import ir.msob.jima.core.commons.child.relatedaction.RelatedActionCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.relatedaction.write.*;
import ir.msob.jima.crud.service.child.relatedaction.BaseRelatedActionCrudService;

import java.io.Serializable;


public interface BaseRelatedActionCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser

        , RA extends RelatedActionAbstract<ID>
        , C extends RelatedActionCriteriaAbstract<ID, RA>
        , CNT extends BaseRelatedActionContainer<ID, RA>

        , DTO extends BaseDto<ID> & BaseRelatedActionContainer<ID, RA>
        , S extends BaseRelatedActionCrudService<ID, USER, RA, C, CNT, DTO>
        > extends
        BaseDeleteByIdRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S>
        , BaseDeleteByNameRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S>
        , BaseDeleteRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S>
        , BaseDeleteManyRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S>
        , BaseSaveRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S>
        , BaseSaveManyRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S>
        , BaseUpdateByIdRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S>
        , BaseUpdateByNameRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S>
        , BaseUpdateRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S>
        , BaseUpdateManyRelatedActionCrudRestResource<ID, USER, RA, C, CNT, DTO, S> {
}