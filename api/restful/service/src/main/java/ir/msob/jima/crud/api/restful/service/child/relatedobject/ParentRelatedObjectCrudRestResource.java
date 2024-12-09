package ir.msob.jima.crud.api.restful.service.child.relatedobject;

import ir.msob.jima.core.commons.child.BaseChild;
import ir.msob.jima.core.commons.child.BaseChildCriteria;
import ir.msob.jima.core.commons.child.relatedobject.BaseRelatedObjectDto;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.ParentChildCrudRestResource;
import ir.msob.jima.crud.service.child.ParentChildService;

import java.io.Serializable;


public interface ParentRelatedObjectCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RM extends BaseChild<ID>
        , C extends BaseChildCriteria<ID, RM>
        , RDTO extends BaseRelatedObjectDto<ID>
        , S extends ParentChildService<ID, USER, DTO, RM, C, RDTO>>
        extends ParentChildCrudRestResource<ID, USER, DTO, RM, C, RDTO, S> {

    S getService();

}