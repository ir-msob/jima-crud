package ir.msob.jima.crud.api.restful.service.child.objectvalidation;

import ir.msob.jima.core.commons.child.objectvalidation.BaseObjectValidationContainer;
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

        , OV extends ObjectValidationAbstract<ID>
        , C extends ObjectValidationCriteriaAbstract<ID, OV>
        , CNT extends BaseObjectValidationContainer<ID, OV>

        , DTO extends BaseDto<ID> & BaseObjectValidationContainer<ID, OV>

        , S extends BaseObjectValidationCrudService<ID, USER, OV, C, CNT, DTO>
        > extends
        BaseDeleteByIdObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S>
        , BaseDeleteByNameObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S>
        , BaseDeleteObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S>
        , BaseDeleteManyObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S>
        , BaseSaveObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S>
        , BaseSaveManyObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S>
        , BaseUpdateByIdObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S>
        , BaseUpdateByNameObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S>
        , BaseUpdateObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S>
        , BaseUpdateManyObjectValidationCrudRestResource<ID, USER, OV, C, CNT, DTO, S> {
}