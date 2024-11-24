package ir.msob.jima.crud.service.domain;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
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
public interface BaseCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
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