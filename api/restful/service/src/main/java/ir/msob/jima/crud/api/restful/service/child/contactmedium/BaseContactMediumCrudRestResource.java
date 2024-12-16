package ir.msob.jima.crud.api.restful.service.child.contactmedium;

import ir.msob.jima.core.commons.child.contactmedium.BaseContactMediumContainer;
import ir.msob.jima.core.commons.child.contactmedium.ContactMediumAbstract;
import ir.msob.jima.core.commons.child.contactmedium.ContactMediumCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.contactmedium.write.*;
import ir.msob.jima.crud.service.child.contactmedium.BaseContactMediumCrudService;

import java.io.Serializable;


public interface BaseContactMediumCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser

        , CM extends ContactMediumAbstract<ID>
        , C extends ContactMediumCriteriaAbstract<ID, CM>
        , CNT extends BaseContactMediumContainer<ID, CM>

        , DTO extends BaseDto<ID> & BaseContactMediumContainer<ID, CM>

        , S extends BaseContactMediumCrudService<ID, USER, CM, C, CNT, DTO>
        > extends
        BaseDeleteByIdContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S>
        , BaseDeleteByNameContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S>
        , BaseDeleteContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S>
        , BaseDeleteManyContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S>
        , BaseSaveContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S>
        , BaseSaveManyContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S>
        , BaseUpdateByIdContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S>
        , BaseUpdateByNameContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S>
        , BaseUpdateContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S>
        , BaseUpdateManyContactMediumCrudRestResource<ID, USER, CM, C, CNT, DTO, S> {

}