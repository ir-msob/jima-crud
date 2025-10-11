package ir.msob.jima.crud.service.domain;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.read.*;
import ir.msob.jima.crud.service.domain.write.*;

import java.io.Serializable;

/**
 * This interface represents a base service for CRUD operations.
 * It extends multiple interfaces, each representing a specific CRUD operation.
 *
 * @param <ID>   The type of the entity's ID (must be comparable and serializable).
 * @param <USER> The type of the user.
 * @param <D>    The type of the entity domain.
 * @param <DTO>  The type of the Data Transfer Object (DTO).
 * @param <C>    The type of criteria used for querying.
 * @param <Q>    The type of query.
 * @param <R>    The repository for CRUD operations.
 */
public interface BaseDomainCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>
        > extends
        BaseCountDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseCountAllDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetOneDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetPageDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetManyDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetStreamDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseSaveDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseSaveManyDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseUpdateDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseUpdateManyDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseEditDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseEditManyDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseDeleteAllDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseDeleteDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseDeleteManyDomainCrudService<ID, USER, D, DTO, C, R> {
}