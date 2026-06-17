package ir.msob.jima.crud.core.service.domain;

import ir.msob.jima.crud.core.service.domain.read.*;
import ir.msob.jima.crud.core.service.domain.write.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.security.BaseUser;

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
 * @param <R>    The repository for CRUD operations.
 */
public interface BaseDomainCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>
        > extends
        BaseCountDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseCountAllDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetOneDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetPageDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetManyDomainCrudService<ID, USER, D, DTO, C, R>,
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