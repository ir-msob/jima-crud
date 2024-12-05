package ir.msob.jima.crud.api.restful.service.related.relatedaction;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.related.relatedaction.BaseRelatedActionDto;
import ir.msob.jima.core.commons.related.relatedaction.RelatedActionAbstract;
import ir.msob.jima.core.commons.related.relatedaction.RelatedActionCriteriaAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.relatedaction.write.*;
import ir.msob.jima.crud.service.related.relatedaction.BaseRelatedActionCrudService;

import java.io.Serializable;


public interface BaseRelatedActionCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , OV extends RelatedActionAbstract<ID>
        , C extends RelatedActionCriteriaAbstract<ID, OV>
        , RDTO extends BaseRelatedActionDto<ID, OV>
        , S extends BaseRelatedActionCrudService<ID, USER, DTO, OV, C, RDTO>
        > extends
        BaseDeleteByIdRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseDeleteByNameRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseDeleteRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseDeleteManyRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseSaveRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseSaveManyRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseUpdateByIdRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseUpdateByNameRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseUpdateRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseUpdateManyRelatedActionCrudRestResource<ID, USER, DTO, OV, C, RDTO, S> {
}