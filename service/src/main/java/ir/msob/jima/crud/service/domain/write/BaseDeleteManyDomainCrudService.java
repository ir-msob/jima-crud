package ir.msob.jima.crud.service.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This service interface defines the contract for executing delete operations, specifically for deleting multiple entities that match a set of criteria or a list of IDs.
 * It extends the {@link ParentWriteDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be deleted.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseDeleteManyDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, R extends BaseDomainCrudRepository<ID, D, C>> extends ParentWriteDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseDeleteManyDomainCrudService.class);

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
    default Mono<@NonNull Collection<ID>> deleteMany(Collection<ID> ids, USER user) throws DomainNotFoundException, BadRequestException {
        return this.doDeleteMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
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
    default Mono<@NonNull Collection<ID>> deleteMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        logger.debug("DeleteMany, criteria: {}, user: {}", criteria, user);

        return this.doDeleteMany(criteria, user);
    }

    private Mono<@NonNull Collection<ID>> doDeleteMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        getBeforeAfterOperationComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations());

        return getStream(criteria, user)
                .doOnNext(dto -> getBeforeAfterOperationComponent().beforeDelete(criteria, user, getBeforeAfterDomainOperations()))
                .flatMap(dto -> this.preDelete(criteria, user).thenReturn(dto))
                .flatMap(dto -> {
                    C criteriaId = CriteriaUtil.idCriteria(getCriteriaClass(), dto.getId());

                    return this.getRepository().removeOne(criteriaId).thenReturn(dto);
                })
                .flatMap(dto -> this.postDelete(dto, criteria, user).thenReturn(dto))
                .doOnNext(dto -> this.getBeforeAfterOperationComponent().afterDelete(dto, criteria, getDtoClass(), user, getBeforeAfterDomainOperations()))
                .map(BaseDomain::getId)
                .collectList()
                .map(ArrayList::new);
    }
}