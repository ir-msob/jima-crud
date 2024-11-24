package ir.msob.jima.crud.api.rsocket.service.domain;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.rsocket.service.domain.read.*;
import ir.msob.jima.crud.api.rsocket.service.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;

import java.io.Serializable;

/**
 * This interface provides a RSocket API for CRUD operations.
 * It extends multiple interfaces each providing a specific CRUD operation.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <Q>    the type of the query
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 * @author Yaqub Abdi
 */
public interface BaseCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends
        BaseCountAllCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetByIdCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetStreamCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseSaveCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateByIdCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditByIdCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteByIdCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
}