package ir.msob.jima.crud.service.domain.read;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.ParentDomainCrudService;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface defines a parent service for reading entities based on specific criteria.
 * It provides methods that can be overridden to customize the behavior before and after the get operation.
 *
 * @param <ID>   The type of entity IDs. It must be comparable and serializable.
 * @param <USER> The type of the user associated with the operations. It must extend BaseUser.
 * @param <D>    The type of the domain entities. It must extend BaseDomain.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities. It must extend BaseDto.
 * @param <C>    The type of the criteria used for filtering entities. It must extend BaseCriteria.
 * @param <Q>    The type of the query used for filtering entities. It must extend BaseQuery.
 * @param <R>    The type of the CRUD repository used for data access. It must extend BaseDomainCrudRepository.
 */
public interface ParentReadDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>, Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>> extends ParentDomainCrudService<ID, USER, D, DTO, C, R> {

    /**
     * This method is executed before the get service. It can be overridden to provide custom behavior.
     * By default, it does nothing and returns an empty Mono.
     *
     * @param criteria The criteria used for filtering entities.
     * @param user     A user associated with the operation.
     * @return A Mono indicating the completion of the pre-get operation.
     */
    default Mono<Void> preGet(C criteria, USER user) {
        return Mono.empty();
    }

    /**
     * This method is executed after the get service. It can be overridden to provide custom behavior.
     * By default, it does nothing and returns an empty Mono.
     *
     * @param ids      A collection of entity IDs that were retrieved.
     * @param dtos     A collection of DTO entities corresponding to the retrieved domains.
     * @param criteria The criteria used for filtering entities.
     * @param user     A user associated with the operation.
     * @return A Mono indicating the completion of the post-get operation.
     */
    default Mono<Void> postGet(Collection<ID> ids, Collection<DTO> dtos, C criteria, USER user) {
        return Mono.empty();
    }

}