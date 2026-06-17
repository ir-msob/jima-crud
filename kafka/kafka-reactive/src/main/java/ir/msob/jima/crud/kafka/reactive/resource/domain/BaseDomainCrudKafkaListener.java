package ir.msob.jima.crud.kafka.reactive.resource.domain;

import ir.msob.jima.crud.kafka.reactive.resource.domain.read.*;
import ir.msob.jima.crud.kafka.reactive.resource.domain.write.*;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

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
 * @param <DTO>  The type of the DTO, which must extend BaseDomainDto.
 * @param <C>    The type of the Criteria, which must extend BaseDomainCriteria.
 * @param <R>    The type of the Repository, which must extend BaseReactiveRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface BaseDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends
        BaseDeleteByIdDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseDeleteDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseDeleteManyDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseEditByIdDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseEditDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseEditManyDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseSaveDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseSaveManyDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseUpdateByIdDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseUpdateDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseUpdateManyDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,

        BaseCountAllDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseCountDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseGetManyDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseGetByIdDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseGetOneDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S>,
        BaseGetPageDomainCrudKafkaListener<ID, USER, D, DTO, C, R, S> {

}