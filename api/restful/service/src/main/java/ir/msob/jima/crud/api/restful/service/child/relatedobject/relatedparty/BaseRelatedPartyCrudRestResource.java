package ir.msob.jima.crud.api.restful.service.child.relatedobject.relatedparty;

import ir.msob.jima.core.commons.child.relatedobject.relatedparty.BaseRelatedPartyContainer;
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

        , RP extends RelatedPartyAbstract<ID>
        , C extends RelatedPartyCriteriaAbstract<ID, RP>
        , CNT extends BaseRelatedPartyContainer<ID, RP>

        , DTO extends BaseDto<ID> & BaseRelatedPartyContainer<ID, RP>

        , S extends BaseRelatedPartyCrudService<ID, USER, RP, C, CNT, DTO>
        > extends
        BaseDeleteByIdRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseDeleteByRelatedIdRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseDeleteRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseDeleteManyRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseSaveRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseSaveManyRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseUpdateByIdRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseUpdateByRelatedIdRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseUpdateRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S>
        , BaseUpdateManyRelatedPartyCrudRestResource<ID, USER, RP, C, CNT, DTO, S> {

}