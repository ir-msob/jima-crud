package ir.msob.jima.crud.api.restful.service.related.relatedobject.relateddomain;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.related.relatedobject.relateddomain.RelatedDomainAbstract;
import ir.msob.jima.core.commons.related.relatedobject.relateddomain.RelatedDomainCriteriaAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.relatedobject.relateddomain.write.*;
import ir.msob.jima.crud.service.related.relatedobject.relateddomain.BaseRelatedDomainCrudService;

import java.io.Serializable;


public interface BaseRelatedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RD extends RelatedDomainAbstract<ID>
        , C extends RelatedDomainCriteriaAbstract<ID, RD>
        , S extends BaseRelatedDomainCrudService<ID, USER, DTO, RD, C>
        > extends
        BaseDeleteByIdRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S>
        , BaseDeleteByRelatedIdRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S>
        , BaseDeleteRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S>
        , BaseDeleteManyRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S>
        , BaseSaveRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S>
        , BaseSaveManyRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S>
        , BaseUpdateByIdRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S>
        , BaseUpdateByRelatedIdRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S>
        , BaseUpdateRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S>
        , BaseUpdateManyRelatedDomainCrudRestResource<ID, USER, DTO, RD, C, S> {


}