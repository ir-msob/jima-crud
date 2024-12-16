package ir.msob.jima.crud.api.restful.service.child.relatedobject.relateddomain;

import ir.msob.jima.core.commons.child.relatedobject.relateddomain.BaseRelatedDomainContainer;
import ir.msob.jima.core.commons.child.relatedobject.relateddomain.RelatedDomainAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relateddomain.RelatedDomainCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.relatedobject.relateddomain.write.*;
import ir.msob.jima.crud.service.child.relatedobject.relateddomain.BaseRelatedDomainCrudService;

import java.io.Serializable;


public interface BaseRelatedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , RD extends RelatedDomainAbstract<ID>
        , C extends RelatedDomainCriteriaAbstract<ID, RD>
        , CNT extends BaseRelatedDomainContainer<ID, RD>

        , DTO extends BaseDto<ID> & BaseRelatedDomainContainer<ID, RD>

        , S extends BaseRelatedDomainCrudService<ID, USER, RD, C, CNT, DTO>
        > extends
        BaseDeleteByIdRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S>
        , BaseDeleteByRelatedIdRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S>
        , BaseDeleteRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S>
        , BaseDeleteManyRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S>
        , BaseSaveRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S>
        , BaseSaveManyRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S>
        , BaseUpdateByIdRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S>
        , BaseUpdateByRelatedIdRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S>
        , BaseUpdateRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S>
        , BaseUpdateManyRelatedDomainCrudRestResource<ID, USER, RD, C, CNT, DTO, S> {


}