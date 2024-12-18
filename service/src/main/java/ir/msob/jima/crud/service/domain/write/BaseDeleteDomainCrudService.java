package ir.msob.jima.crud.service.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * This service interface defines the contract for executing delete operations on a single entity or based on specific criteria.
 * It extends the {@link ParentWriteDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be deleted.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <Q>    The type of query used for database operations.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseDeleteDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseDomainCrudRepository<ID, USER, D, C, Q>> extends ParentWriteDomainCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseDeleteDomainCrudService.class);

    /**
     * Executes the delete operation to remove a single entity based on its ID.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param id   The ID of the entity to be deleted.
     * @param user A user associated with the operation.
     * @return A Mono of the ID of the entity that was deleted.
     * @throws DomainNotFoundException if the entity to be deleted is not found.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<ID> delete(ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return this.delete(CriteriaUtil.idCriteria(getCriteriaClass(), id), user);
    }

    /**
     * Executes the delete operation to remove a single entity based on the specified criteria.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param criteria The criteria used for filtering the entity to be deleted.
     * @param user     A user associated with the operation.
     * @return A Mono of the ID of the entity that was deleted.
     * @throws DomainNotFoundException if the entity to be deleted is not found.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<ID> delete(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        log.debug("Delete, criteria: {}, user: {}", criteria, user);

        getBeforeAfterComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations());

        return getOne(criteria, user)
                .doOnNext(dto -> getBeforeAfterComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations()))
                .flatMap(dto -> this.preDelete(criteria, user).thenReturn(dto))
                .flatMap(dto -> {
                    C criteriaId = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId());
                    Q baseQuery = this.getRepository().generateQuery(criteriaId);
                    baseQuery = this.getRepository().criteria(baseQuery, criteriaId, user);
                    return this.getRepository().removeOne(baseQuery).thenReturn(dto);
                })
                .flatMap(dto -> this.postDelete(dto, criteria, user).thenReturn(dto))
                .doOnNext(dto -> this.getBeforeAfterComponent().afterDelete(dto, criteria, getDtoClass(), user, getBeforeAfterDomainOperations()))
                .map(BaseDomain::getId);
    }
}