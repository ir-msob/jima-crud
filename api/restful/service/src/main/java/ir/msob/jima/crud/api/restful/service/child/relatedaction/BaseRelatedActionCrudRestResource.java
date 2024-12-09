package ir.msob.jima.crud.api.restful.service.child.relatedaction;

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
        , DTO extends BaseDto<ID>
        , OV extends RelatedActionAbstract<ID>
        , C extends RelatedActionCriteriaAbstract<ID, OV>
        , S extends BaseRelatedActionCrudService<ID, USER, DTO, OV, C>
        > extends
        BaseDeleteByIdRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseDeleteByNameRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseDeleteRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseDeleteManyRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseSaveRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseSaveManyRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseUpdateByIdRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseUpdateByNameRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseUpdateRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseUpdateManyRelatedActionCrudRestResource<ID, USER, DTO, OV, C, S> {
}