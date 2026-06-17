package ir.msob.jima.crud.reactive.service.domain;

import ir.msob.jima.crud.reactive.service.domain.read.*;
import ir.msob.jima.crud.reactive.service.domain.write.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

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
public interface BaseDomainCrudReactiveService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>
        > extends
        BaseCountDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseCountAllDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseGetOneDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseGetPageDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseGetManyDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseGetStreamDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseSaveDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseSaveManyDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseUpdateDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseUpdateManyDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseEditDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseEditManyDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseDeleteAllDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseDeleteDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        BaseDeleteManyDomainCrudReactiveService<ID, USER, D, DTO, C, R> {
}