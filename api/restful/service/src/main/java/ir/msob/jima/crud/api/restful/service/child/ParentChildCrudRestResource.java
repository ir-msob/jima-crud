package ir.msob.jima.crud.api.restful.service.child;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.child.BaseChild;
import ir.msob.jima.core.commons.child.BaseChildCriteria;
import ir.msob.jima.core.commons.child.BaseContainer;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.service.child.ParentChildService;

import java.io.Serializable;


public interface ParentChildCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RM extends BaseChild<ID>
        , C extends BaseChildCriteria<ID, RM>
        , CNT extends BaseContainer
        , S extends ParentChildService<ID, USER, DTO, RM, C, CNT>>
        extends BaseRestResource<ID, USER>,
        BaseCrudResource {

    S getService();

}