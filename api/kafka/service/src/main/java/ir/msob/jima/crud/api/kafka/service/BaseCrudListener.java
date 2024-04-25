package ir.msob.jima.crud.api.kafka.service;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.kafka.service.read.*;
import ir.msob.jima.crud.api.kafka.service.write.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;

import java.io.Serializable;

/**
 * Interface for a listener that handles CRUD operations.
 * <p>
 * This interface extends multiple other interfaces, each of which handles a specific CRUD operation.
 * The CRUD operations include delete, edit, save, update, count, and get operations.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDto.
 * @param <C>    The type of the Criteria, which must extend BaseCriteria.
 * @param <Q>    The type of the Query, which must extend BaseQuery.
 * @param <R>    The type of the Repository, which must extend BaseCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseCrudService.
 */
public interface BaseCrudListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends
        BaseDeleteByIdCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditByIdCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateByIdCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyCrudListener<ID, USER, D, DTO, C, Q, R, S>,

        BaseCountAllCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetByIdCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneCrudListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageCrudListener<ID, USER, D, DTO, C, Q, R, S> {

}