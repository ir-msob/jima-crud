package ir.msob.jima.crud.service.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * This service interface defines the contract for executing delete operations on a single entity or based on specific criteria.
 * It extends the {@link ParentWriteCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be deleted.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <Q>    The type of query used for database operations.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseDeleteCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseDeleteCrudService.class);

    /**
     * Executes the delete operation to remove a single entity based on its ID.
     *
     * @param id   The ID of the entity to be deleted.
     * @param user An optional user associated with the operation.
     * @return The ID of the entity that was deleted.
     * @throws DomainNotFoundException   if the entity to be deleted is not found.
     * @throws BadRequestException       if the operation encounters a bad request scenario.
     * @throws InvocationTargetException if an error occurs during invocation.
     * @throws NoSuchMethodException     if a required method is not found.
     * @throws InstantiationException    if an instance of a class cannot be created.
     * @throws IllegalAccessException    if an illegal access operation occurs.
     */
    @Transactional
    @MethodStats
    default Mono<ID> delete(ID id, Optional<USER> user) throws DomainNotFoundException, BadRequestException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        return this.delete(CriteriaUtil.idCriteria(getCriteriaClass(), id), user);
    }

    /**
     * Executes the delete operation to remove a single entity based on the specified criteria.
     *
     * @param criteria The criteria used for filtering the entity to be deleted.
     * @param user     An optional user associated with the operation.
     * @return The ID of the entity that was deleted.
     * @throws DomainNotFoundException if the entity to be deleted is not found.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<ID> delete(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        log.debug("Delete, criteria: {}, user: {}", criteria, user.orElse(null));

        getBeforeAfterComponent().beforeDelete(criteria, user);

        return this.preDelete(criteria, user)
                .then(this.deleteExecute(criteria, user))
                .flatMap(deletedDomain -> {
                    Collection<ID> ids = Collections.singletonList(deletedDomain.getDomainId());
                    Collection<D> domains = Collections.singletonList(deletedDomain);
                    return this.postDelete(ids, domains, criteria, user)
                            .doOnSuccess(x -> afterDelete(ids, domains, criteria, user))
                            .thenReturn(deletedDomain.getDomainId());
                });
    }

    /**
     * Executes the actual removal of a single entity based on the specified criteria.
     *
     * @param criteria The criteria used for filtering the entity to be deleted.
     * @param user     An optional user associated with the operation.
     * @return A Mono containing the entity (domain) that was removed.
     * @throws DomainNotFoundException if the entity to be deleted is not found.
     */
    default Mono<D> deleteExecute(C criteria, Optional<USER> user) throws DomainNotFoundException {
        Q baseQuery = this.getRepository().generateQuery(criteria);
        baseQuery = this.getRepository().criteria(baseQuery, criteria, user);
        return this.getRepository().removeOne(baseQuery);
    }
}
