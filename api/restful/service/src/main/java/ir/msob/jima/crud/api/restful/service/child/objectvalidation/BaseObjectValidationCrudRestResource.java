package ir.msob.jima.crud.api.restful.service.child.objectvalidation;

import ir.msob.jima.core.commons.child.objectvalidation.ObjectValidationAbstract;
import ir.msob.jima.core.commons.child.objectvalidation.ObjectValidationCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.objectvalidation.write.*;
import ir.msob.jima.crud.service.child.objectvalidation.BaseObjectValidationCrudService;

import java.io.Serializable;


public interface BaseObjectValidationCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , OV extends ObjectValidationAbstract<ID>
        , C extends ObjectValidationCriteriaAbstract<ID, OV>
        , S extends BaseObjectValidationCrudService<ID, USER, DTO, OV, C>
        > extends
        BaseDeleteByIdObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseDeleteByNameObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseDeleteObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseDeleteManyObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseSaveObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseSaveManyObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseUpdateByIdObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseUpdateByNameObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseUpdateObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S>
        , BaseUpdateManyObjectValidationCrudRestResource<ID, USER, DTO, OV, C, S> {
}