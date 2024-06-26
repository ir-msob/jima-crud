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
public interface BaseDeleteAllCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseDeleteAllCrudService.class);

    /**
     * Executes the delete operation to remove all entities that match the specified criteria.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param user An optional user associated with the operation.
     * @return A Mono of a collection of entity IDs that were deleted.
     * @throws DomainNotFoundException   if the entities to be deleted are not found.
     * @throws BadRequestException       if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<Collection<ID>> deleteAll(Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        log.debug("Delete, user: {}", user.orElse(null));

        C criteria = newCriteriaClass();

        return getStream(criteria, user)
//                .doOnNext(dto -> getBeforeAfterComponent().beforeDelete(criteria, user, getBeforeAfterDomainServices()))
                .flatMap(dto -> this.preDelete(criteria, user).thenReturn(dto))
                .flatMap(dto -> this.deleteAllExecute(dto, user).thenReturn(dto))
                .flatMap(dto -> this.postDelete(dto, criteria, user).thenReturn(dto))
//                .doOnNext(dto -> this.getBeforeAfterComponent().afterDelete(dto, criteria, getDtoClass(), user, getBeforeAfterDomainServices()))
                .map(BaseDomain::getDomainId)
                .collectList()
                .map(ArrayList::new);
    }

    /**
     * Executes the actual removal of all entities based on the specified criteria.
     * This method is called by the deleteAll method after the preDelete method.
     *
     * @param dto The DTO to be deleted.
     * @param user An optional user associated with the operation.
     * @return A Flux of entities (domains) to be removed.
     * @throws DomainNotFoundException if the entities to be deleted are not found.
     */
    default Mono<DTO> deleteAllExecute(DTO dto, Optional<USER> user) throws DomainNotFoundException {
        C criteria = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getDomainId());
        Q baseQuery = this.getRepository().generateQuery(criteria);
        baseQuery = this.getRepository().criteria(baseQuery, criteria, user);
        return this.getRepository().removeOne(baseQuery).thenReturn(dto);
    }
}