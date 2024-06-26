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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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
     * @param user An optional user associated with the operation.
     * @return A Mono of a collection of entity IDs that were deleted.
     * @throws DomainNotFoundException   if the entities to be deleted are not found.
     * @throws BadRequestException       if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<Collection<ID>> deleteMany(Collection<ID> ids, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        return this.deleteMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
    }

    /**
     * Executes the delete operation to remove multiple entities based on the specified criteria.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param criteria The criteria used for filtering entities to be deleted.
     * @param user     An optional user associated with the operation.
     * @return A Mono of a collection of entity IDs that were deleted.
     * @throws DomainNotFoundException if the entities to be deleted are not found.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<Collection<ID>> deleteMany(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        log.debug("DeleteMany, criteria: {}, user: {}", criteria, user.orElse(null));

        getBeforeAfterComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations());

        return getStream(criteria, user)
                .doOnNext(dto -> getBeforeAfterComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations()))
                .flatMap(dto -> this.preDelete(criteria, user).thenReturn(dto))
                .flatMap(dto -> this.deleteManyExecute(dto, user).thenReturn(dto))
                .flatMap(dto -> this.postDelete(dto, criteria, user).thenReturn(dto))
                .doOnNext(dto -> this.getBeforeAfterComponent().afterDelete(dto, criteria, getDtoClass(), user, getBeforeAfterDomainOperations()))
                .map(BaseDomain::getDomainId)
                .collectList()
                .map(ArrayList::new);
    }
    /**
     * Executes the actual removal of multiple entities based on the specified criteria.
     * This method is called by the deleteMany method after the preDelete method.
     *
     * @param dto The DTO to be deleted.
     * @param user     An optional user associated with the operation.
     * @return A Flux of entities (domains) to be removed.
     * @throws DomainNotFoundException if the entities to be deleted are not found.
     */
    default Mono<DTO> deleteManyExecute(DTO dto, Optional<USER> user) throws DomainNotFoundException {
        C criteria = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getDomainId());
        Q baseQuery = this.getRepository().generateQuery(criteria);
        baseQuery = this.getRepository().criteria(baseQuery, criteria, user);
        return this.getRepository().removeOne(baseQuery).thenReturn(dto);
    }
}