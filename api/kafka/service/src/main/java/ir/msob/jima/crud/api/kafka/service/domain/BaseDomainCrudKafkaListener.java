package ir.msob.jima.crud.api.kafka.service.domain;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.kafka.service.domain.read.*;
import ir.msob.jima.crud.api.kafka.service.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;

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
 * @param <R>    The type of the Repository, which must extend BaseDomainCrudRepository.
 * @param <S>    The type of the Service, which must extend BaseDomainCrudService.
 */
public interface BaseDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>
        > extends
        BaseDeleteByIdDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditByIdDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateByIdDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,

        BaseCountAllDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetByIdDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageDomainCrudKafkaListener<ID, USER, D, DTO, C, Q, R, S> {

}