package ir.msob.jima.crud.api.rsocket.service;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.read.*;
import ir.msob.jima.crud.api.rsocket.service.write.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;

import java.io.Serializable;

/**
 * @param <ID>
 * @param <D>
 * @param <DTO>
 * @param <USER>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public interface BaseCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends
        BaseCountAllCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetStreamCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseSaveCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
}
