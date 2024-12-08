package ir.msob.jima.crud.service.domain;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
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
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>
        > extends
        BaseCountDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseCountAllDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseGetOneDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseGetPageDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseGetManyDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseGetStreamDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseSaveDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseSaveManyDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseUpdateDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseUpdateManyDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseEditDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseEditManyDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseDeleteAllDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseDeleteDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        BaseDeleteManyDomainCrudService<ID, USER, D, DTO, C, Q, R> {
}