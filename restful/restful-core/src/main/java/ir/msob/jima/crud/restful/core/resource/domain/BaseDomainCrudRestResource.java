package ir.msob.jima.crud.restful.core.resource.domain;

import ir.msob.jima.crud.core.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.restful.core.resource.domain.read.*;
import ir.msob.jima.crud.restful.core.resource.domain.write.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.security.BaseUser;

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
public interface BaseDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends
        BaseCountAllDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseCountDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseGetByIdDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetOneDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetPageDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetManyDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseSaveDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseSaveManyDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseUpdateByIdDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateManyDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseEditByIdDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseEditDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseEditManyDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseDeleteByIdDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteManyDomainCrudRestResource<ID, USER, D, DTO, C, R, S> {
}