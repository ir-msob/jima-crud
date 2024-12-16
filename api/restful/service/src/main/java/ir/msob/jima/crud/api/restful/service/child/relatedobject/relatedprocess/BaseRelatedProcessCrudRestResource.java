package ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedprocess;

import ir.msob.jima.core.commons.child.relatedobject.relatedprocess.BaseRelatedProcessContainer;
import ir.msob.jima.core.commons.child.relatedobject.relatedprocess.RelatedProcessAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relatedprocess.RelatedProcessCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedprocess.write.*;
import ir.msob.jima.crud.service.child.relatedobject.relatedprocess.BaseRelatedProcessCrudService;

import java.io.Serializable;


public interface BaseRelatedProcessCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser

        , RP extends RelatedProcessAbstract<ID>
        , C extends RelatedProcessCriteriaAbstract<ID, RP>
        , CNT extends BaseRelatedProcessContainer<ID, RP>

        , DTO extends BaseDto<ID> & BaseRelatedProcessContainer<ID, RP>

        , S extends BaseRelatedProcessCrudService<ID, USER, RP, C, CNT, DTO>
        > extends BaseDeleteByIdRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseDeleteByRelatedIdRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseDeleteRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseDeleteManyRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseSaveRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseSaveManyRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseUpdateByIdRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseUpdateByRelatedIdRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseUpdateRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseUpdateManyRelatedProcessCrudRestResource<ID, USER, RP, C, CNT, DTO, S> {

}