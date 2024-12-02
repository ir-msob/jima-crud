package ir.msob.jima.crud.service.domain.write;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This service interface defines the contract for executing delete operations, specifically for deleting multiple entities that match a set of criteria or a list of IDs.
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
public interface BaseDeleteManyCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseDeleteManyCrudService.class);

    /**
     * Executes the delete operation to remove multiple entities based on a collection of IDs.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param ids  A collection of entity IDs to be deleted.
     * @param user A user associated with the operation.
     * @return A Mono of a collection of entity IDs that were deleted.
     * @throws DomainNotFoundException if the entities to be deleted are not found.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<Collection<ID>> deleteMany(Collection<ID> ids, USER user) throws DomainNotFoundException, BadRequestException {
        return this.deleteMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
    }

    /**
     * Executes the delete operation to remove multiple entities based on the specified criteria.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param criteria The criteria used for filtering entities to be deleted.
     * @param user     A user associated with the operation.
     * @return A Mono of a collection of entity IDs that were deleted.
     * @throws DomainNotFoundException if the entities to be deleted are not found.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<Collection<ID>> deleteMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        log.debug("DeleteMany, criteria: {}, user: {}", criteria, user);

        getBeforeAfterComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations());

        return getStream(criteria, user)
                .doOnNext(dto -> getBeforeAfterComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations()))
                .flatMap(dto -> this.preDelete(criteria, user).thenReturn(dto))
                .flatMap(dto -> {
                    C criteriaId = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getDomainId());
                    Q baseQuery = this.getRepository().generateQuery(criteriaId);
                    baseQuery = this.getRepository().criteria(baseQuery, criteriaId, user);
                    return this.getRepository().removeOne(baseQuery).thenReturn(dto);
                })
                .flatMap(dto -> this.postDelete(dto, criteria, user).thenReturn(dto))
                .doOnNext(dto -> this.getBeforeAfterComponent().afterDelete(dto, criteria, getDtoClass(), user, getBeforeAfterDomainOperations()))
                .map(BaseDomain::getDomainId)
                .collectList()
                .map(ArrayList::new);
    }
}