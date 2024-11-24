package ir.msob.jima.crud.api.kafka.service.domain;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.kafka.service.domain.read.*;
import ir.msob.jima.crud.api.kafka.service.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;

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
public interface BaseCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > extends
        BaseDeleteByIdCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditByIdCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateByIdCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,

        BaseCountAllCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetByIdCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S> {

}