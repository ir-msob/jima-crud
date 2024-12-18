package ir.msob.jima.crud.api.rsocket.service.domain;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.service.domain.read.*;
import ir.msob.jima.crud.api.rsocket.service.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;

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
public interface BaseDomainCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,

        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>
        > extends
        BaseCountAllDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetByIdDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetStreamDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,

        BaseSaveDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateByIdDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditByIdDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteByIdDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyDomainCrudRsocketResource<ID, USER, D, DTO, C, Q, R, S> {
}