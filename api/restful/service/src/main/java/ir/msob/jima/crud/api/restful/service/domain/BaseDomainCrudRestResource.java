package ir.msob.jima.crud.api.restful.service.domain;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.domain.read.*;
import ir.msob.jima.crud.api.restful.service.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;

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
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends
        BaseCountAllDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseCountDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

        BaseGetByIdDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetOneDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetPageDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetManyDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetStreamDomainCrudRestResource<ID, USER, D, DTO, C, R, S>,

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