package ir.msob.jima.crud.service.childdomain;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.read.*;
import ir.msob.jima.crud.service.childdomain.write.*;

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
public interface BaseChildDomainCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>
        > extends
        BaseCountChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseCountAllChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetOneChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetPageChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetManyChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseGetStreamChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseSaveChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseSaveManyChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseUpdateChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseUpdateManyChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseEditChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseEditManyChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseDeleteAllChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseDeleteChildDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseDeleteManyChildDomainCrudService<ID, USER, D, DTO, C, R> {
}