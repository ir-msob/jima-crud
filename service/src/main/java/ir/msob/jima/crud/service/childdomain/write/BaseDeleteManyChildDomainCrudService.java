package ir.msob.jima.crud.service.childdomain.write;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
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
import java.util.Collection;

/**
 * This service interface defines the contract for executing delete operations, specifically for deleting multiple entities that match a set of criteria or a list of IDs.
 * It extends the {@link ParentWriteChildDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be deleted.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseDeleteManyChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>, C extends BaseChildCriteria<ID>, R extends BaseDomainCrudRepository<ID, D, C>> extends ParentWriteChildDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseDeleteManyChildDomainCrudService.class);

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
    default Mono<@NonNull Collection<ID>> deleteMany(ID parentId, Collection<ID> ids, USER user) throws DomainNotFoundException, BadRequestException {
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
    default Mono<@NonNull Collection<ID>> deleteMany(ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        logger.debug("DeleteMany, criteria: {}, user: {}", criteria, user);

        return this.doDeleteMany(criteria, user);
    }


}