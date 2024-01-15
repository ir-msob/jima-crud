package ir.msob.jima.crud.api.kafka.service;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.kafka.service.read.*;
import ir.msob.jima.crud.api.kafka.service.write.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;

import java.io.Serializable;

public interface BaseCrudListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>>
        extends
        BaseCountAllCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyCrudListener<ID, USER, D, DTO, C, Q, R, S> {

}
