package ir.msob.jima.crud.api.restful.service.related.relatedobject.relatedprocess;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.related.relatedobject.relatedprocess.RelatedProcessAbstract;
import ir.msob.jima.core.commons.related.relatedobject.relatedprocess.RelatedProcessCriteriaAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.relatedobject.relatedprocess.write.*;
import ir.msob.jima.crud.service.related.relatedobject.relatedprocess.BaseRelatedProcessCrudService;

import java.io.Serializable;


public interface BaseRelatedProcessCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RP extends RelatedProcessAbstract<ID>
        , C extends RelatedProcessCriteriaAbstract<ID, RP>
        , S extends BaseRelatedProcessCrudService<ID, USER, DTO, RP, C>
        > extends BaseDeleteByIdRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseDeleteByRelatedIdRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseDeleteRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseDeleteManyRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseSaveRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseSaveManyRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseUpdateByIdRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseUpdateByRelatedIdRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseUpdateRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseUpdateManyRelatedProcessCrudRestResource<ID, USER, DTO, RP, C, S> {

}