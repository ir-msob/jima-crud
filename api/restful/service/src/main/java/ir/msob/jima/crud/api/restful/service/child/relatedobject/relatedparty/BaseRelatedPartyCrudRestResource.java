package ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedparty;

import ir.msob.jima.core.commons.child.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relatedparty.RelatedPartyCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedparty.write.*;
import ir.msob.jima.crud.service.child.relatedobject.relatedparty.BaseRelatedPartyCrudService;

import java.io.Serializable;


public interface BaseRelatedPartyCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RP extends RelatedPartyAbstract<ID>
        , C extends RelatedPartyCriteriaAbstract<ID, RP>
        , S extends BaseRelatedPartyCrudService<ID, USER, DTO, RP, C>
        > extends
        BaseDeleteByIdRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseDeleteByRelatedIdRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseDeleteRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseDeleteManyRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseSaveRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseSaveManyRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseUpdateByIdRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseUpdateByRelatedIdRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseUpdateRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S>
        , BaseUpdateManyRelatedPartyCrudRestResource<ID, USER, DTO, RP, C, S> {

}