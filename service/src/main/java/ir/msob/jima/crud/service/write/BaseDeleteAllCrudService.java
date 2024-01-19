package ir.msob.jima.crud.service.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

/**
 * This service interface defines the contract for executing delete operations, specifically deleting all entities that match a set of criteria.
 * It extends the {@link ParentWriteCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be deleted.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <Q>    The type of query used for database operations.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseDeleteAllCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseDeleteAllCrudService.class);

    /**
     * Executes the delete operation to remove all entities that match the specified criteria.
     *
     * @param user An optional user associated with the operation.
     * @return A collection of entity IDs that were deleted.
     * @throws DomainNotFoundException   if the entities to be deleted are not found.
     * @throws InvocationTargetException if an error occurs during invocation.
     * @throws NoSuchMethodException     if a required method is not found.
     * @throws InstantiationException    if an instance of a class cannot be created.
     * @throws IllegalAccessException    if an illegal access operation occurs.
     * @throws BadRequestException       if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<Collection<ID>> deleteAll(Optional<USER> user) throws DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, BadRequestException {
        log.debug("Delete, user: {}", user.orElse(null));

        C criteria = newCriteriaClass();

        getBeforeAfterComponent().beforeDelete(criteria, user, getBeforeAfterDomainServices());

        return this.preDelete(criteria, user)
                .thenMany(this.deleteAllExecute(user))
                .collectList()
                .flatMap(deletedDomains -> {
                    Collection<ID> ids = prepareIds(deletedDomains);
                    return this.postDelete(ids, criteria, user)
                            .doOnSuccess(x -> getBeforeAfterComponent().afterDelete(ids, criteria, getDtoClass(), user, getBeforeAfterDomainServices()))
                            .thenReturn(ids);
                });

    }

    /**
     * Executes the actual removal of all entities based on the specified criteria.
     *
     * @param user An optional user associated with the operation.
     * @return A Flux of entities (domains) to be removed.
     * @throws DomainNotFoundException if the entities to be deleted are not found.
     */
    default Flux<D> deleteAllExecute(Optional<USER> user) throws DomainNotFoundException {
        return this.getRepository().removeAll();
    }
}
