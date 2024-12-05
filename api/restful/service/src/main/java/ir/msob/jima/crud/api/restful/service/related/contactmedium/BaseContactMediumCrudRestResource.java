package ir.msob.jima.crud.api.restful.service.related.contactmedium;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.related.contactmedium.BaseContactMediumDto;
import ir.msob.jima.core.commons.related.contactmedium.ContactMediumAbstract;
import ir.msob.jima.core.commons.related.contactmedium.ContactMediumCriteriaAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.contactmedium.write.*;
import ir.msob.jima.crud.service.related.contactmedium.BaseContactMediumCrudService;

import java.io.Serializable;


public interface BaseContactMediumCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , CM extends ContactMediumAbstract<ID>
        , C extends ContactMediumCriteriaAbstract<ID, CM>
        , RDTO extends BaseContactMediumDto<ID, CM>
        , S extends BaseContactMediumCrudService<ID, USER, DTO, CM, C, RDTO>
        > extends
        BaseDeleteByIdContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S>
        , BaseDeleteByNameContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S>
        , BaseDeleteContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S>
        , BaseDeleteManyContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S>
        , BaseSaveContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S>
        , BaseSaveManyContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S>
        , BaseUpdateByIdContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S>
        , BaseUpdateByNameContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S>
        , BaseUpdateContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S>
        , BaseUpdateManyContactMediumCrudRestResource<ID, USER, DTO, CM, C, RDTO, S> {

}