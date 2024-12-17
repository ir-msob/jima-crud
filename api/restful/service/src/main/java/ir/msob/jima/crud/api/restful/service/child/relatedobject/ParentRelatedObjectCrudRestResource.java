package ir.msob.jima.crud.api.restful.service.child.relatedobject;

import ir.msob.jima.core.commons.child.BaseChild;
import ir.msob.jima.core.commons.child.BaseChildCriteria;
import ir.msob.jima.core.commons.child.BaseContainer;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.ParentChildCrudRestResource;
import ir.msob.jima.crud.service.child.ParentChildCrudService;

import java.io.Serializable;


public interface ParentRelatedObjectCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser

        , CHILD extends BaseChild<ID>
        , CHILD_C extends BaseChildCriteria<ID, CHILD>
        , CNT extends BaseContainer

        , DTO extends BaseDto<ID> & BaseContainer

        , CHILD_S extends ParentChildCrudService<ID, USER, CHILD, CHILD_C, CNT, DTO>>
        extends ParentChildCrudRestResource<ID, USER, CHILD, CHILD_C, CNT, DTO, CHILD_S> {
}