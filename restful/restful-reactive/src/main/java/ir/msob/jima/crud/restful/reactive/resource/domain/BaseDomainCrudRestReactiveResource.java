package ir.msob.jima.crud.restful.reactive.resource.domain;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.restful.reactive.resource.domain.read.*;
import ir.msob.jima.crud.restful.reactive.resource.domain.write.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

/**
 * This interface provides a RESTful API for CRUD operations on a domain.
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
public interface BaseDomainCrudRestReactiveResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends
        BaseCountAllDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseCountDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,

        BaseGetByIdDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseGetOneDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseGetPageDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseGetManyDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseGetStreamDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,

        BaseSaveDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseSaveManyDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,

        BaseUpdateByIdDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateManyDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,

        BaseEditByIdDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseEditDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseEditManyDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,

        BaseDeleteByIdDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteManyDomainCrudRestReactiveResource<ID, USER, D, DTO, C, R, S> {
}