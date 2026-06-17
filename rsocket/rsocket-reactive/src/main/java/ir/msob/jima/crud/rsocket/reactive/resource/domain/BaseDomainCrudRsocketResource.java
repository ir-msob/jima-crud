package ir.msob.jima.crud.rsocket.reactive.resource.domain;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.rsocket.reactive.resource.domain.read.*;
import ir.msob.jima.crud.rsocket.reactive.resource.domain.write.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

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
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 * @author Yaqub Abdi
 */
public interface BaseDomainCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,

        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends
        BaseCountAllDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseCountDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseGetByIdDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseGetOneDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseGetPageDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseGetManyDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseGetStreamDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,

        BaseSaveDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseSaveManyDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateByIdDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateManyDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseEditByIdDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseEditDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseEditManyDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteByIdDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteManyDomainCrudRsocketResource<ID, USER, D, DTO, C, R, S> {
}