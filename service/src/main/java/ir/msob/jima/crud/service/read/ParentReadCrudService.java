package ir.msob.jima.crud.service.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.commons.ParentCrudService;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * This interface defines a parent service for reading entities based on specific criteria.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for filtering entities.
 * @param <Q>    The type of the query used for filtering entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface ParentReadCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>, Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentCrudService<ID, USER, D, DTO, C, R> {

    /**
     * This method is executed before the get service.
     *
     * @param criteria The criteria used for filtering entities.
     * @param user     An optional user associated with the operation.
     * @return A Mono indicating the completion of the pre-get operation.
     */
    default Mono<Void> preGet(C criteria, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * This method is executed after the get service.
     *
     * @param ids      A collection of entity IDs that were retrieved.
     * @param domains  A collection of domain entities retrieved.
     * @param dtos     A collection of DTO entities corresponding to the retrieved domains.
     * @param criteria The criteria used for filtering entities.
     * @param user     An optional user associated with the operation.
     * @return A Mono indicating the completion of the post-get operation.
     */
    default Mono<Void> postGet(Collection<ID> ids, Collection<D> domains, Collection<DTO> dtos, C criteria, Optional<USER> user) {
        return Mono.empty();
    }

}
