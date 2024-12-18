package ir.msob.jima.crud.api.restful.service.childdomain.contactmedium;

import ir.msob.jima.core.commons.childdomain.contactmedium.ContactMediumAbstract;
import ir.msob.jima.core.commons.childdomain.contactmedium.ContactMediumCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.contactmedium.write.*;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;


public interface BaseContactMediumCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends ContactMediumAbstract<ID>
        , CC extends ContactMediumCriteriaAbstract<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends
        BaseDeleteByIdContactMediumCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteByNameContactMediumCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteContactMediumCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteManyContactMediumCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseSaveContactMediumCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseSaveManyContactMediumCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseUpdateByIdContactMediumCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateByNameContactMediumCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateContactMediumCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateManyContactMediumCrudRestResource<ID, USER, CD, DTO, CS> {

}