package ir.msob.jima.crud.api.restful.service.related;

import ir.msob.jima.core.api.restful.commons.rest.BaseRestResource;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.related.BaseRelatedDto;
import ir.msob.jima.core.commons.related.BaseRelatedModel;
import ir.msob.jima.core.commons.related.BaseRelatedModelCriteria;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.service.related.ParentRelatedService;

import java.io.Serializable;


public interface ParentRelatedCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RM extends BaseRelatedModel<ID>
        , C extends BaseRelatedModelCriteria<ID, RM>
        , RDTO extends BaseRelatedDto<ID>
        , S extends ParentRelatedService<ID, USER, DTO, RM, C, RDTO>>
        extends BaseRestResource<ID, USER>,
        BaseCrudResource {

    S getService();

}