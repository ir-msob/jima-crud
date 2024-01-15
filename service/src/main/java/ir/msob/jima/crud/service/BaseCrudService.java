package ir.msob.jima.crud.service;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.read.*;
import ir.msob.jima.crud.service.write.*;

import java.io.Serializable;

public interface BaseCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>
        > extends
        BaseCountCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseCountAllCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseGetOneCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseGetPageCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseGetManyCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseGetStreamCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseSaveCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseSaveManyCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseUpdateCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseUpdateManyCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseEditCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseEditManyCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseDeleteAllCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseDeleteCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseDeleteManyCrudService<ID, USER, D, DTO, C, Q, R> {
}
