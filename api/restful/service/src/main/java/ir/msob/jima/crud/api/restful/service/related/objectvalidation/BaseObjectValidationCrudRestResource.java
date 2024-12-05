package ir.msob.jima.crud.api.restful.service.related.objectvalidation;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.related.objectvalidation.BaseObjectValidationDto;
import ir.msob.jima.core.commons.related.objectvalidation.ObjectValidationAbstract;
import ir.msob.jima.core.commons.related.objectvalidation.ObjectValidationCriteriaAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.objectvalidation.write.*;
import ir.msob.jima.crud.service.related.objectvalidation.BaseObjectValidationCrudService;

import java.io.Serializable;


public interface BaseObjectValidationCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , OV extends ObjectValidationAbstract<ID>
        , C extends ObjectValidationCriteriaAbstract<ID, OV>
        , RDTO extends BaseObjectValidationDto<ID, OV>
        , S extends BaseObjectValidationCrudService<ID, USER, DTO, OV, C, RDTO>
        > extends
        BaseDeleteByIdObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseDeleteByNameObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseDeleteObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseDeleteManyObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseSaveObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseSaveManyObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseUpdateByIdObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseUpdateByNameObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseUpdateObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S>
        , BaseUpdateManyObjectValidationCrudRestResource<ID, USER, DTO, OV, C, RDTO, S> {
}