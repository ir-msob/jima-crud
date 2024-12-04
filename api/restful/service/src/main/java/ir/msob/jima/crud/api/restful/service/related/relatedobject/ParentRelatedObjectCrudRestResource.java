package ir.msob.jima.crud.api.restful.service.related.relatedobject;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.related.BaseRelatedModel;
import ir.msob.jima.core.commons.related.BaseRelatedModelCriteria;
import ir.msob.jima.core.commons.related.relatedobject.BaseRelatedObjectDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.ParentRelatedCrudRestResource;
import ir.msob.jima.crud.service.related.ParentRelatedService;

import java.io.Serializable;


public interface ParentRelatedObjectCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RM extends BaseRelatedModel<ID>
        , C extends BaseRelatedModelCriteria<ID, RM>
        , RDTO extends BaseRelatedObjectDto<ID>
        , S extends ParentRelatedService<ID, USER, DTO, RM, C, RDTO>>
        extends ParentRelatedCrudRestResource<ID, USER, DTO, RM, C, RDTO, S> {

    S getService();

}