package ir.msob.jima.crud.api.restful.service.childdomain.objectvalidation;

import ir.msob.jima.core.commons.childdomain.objectvalidation.ObjectValidationAbstract;
import ir.msob.jima.core.commons.childdomain.objectvalidation.ObjectValidationCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.objectvalidation.write.*;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;


public interface BaseObjectValidationCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends ObjectValidationAbstract<ID>
        , CC extends ObjectValidationCriteriaAbstract<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>> extends
        BaseDeleteByIdObjectValidationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteByNameObjectValidationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteObjectValidationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteManyObjectValidationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseSaveObjectValidationCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseSaveManyObjectValidationCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseUpdateByIdObjectValidationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateByNameObjectValidationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateObjectValidationCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateManyObjectValidationCrudRestResource<ID, USER, CD, DTO, CS> {
}