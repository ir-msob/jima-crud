package ir.msob.jima.crud.service.childdomain.write;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.filter.Filter;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This service interface defines the contract for executing delete operations, specifically deleting all entities that match a set of criteria.
 * It extends the {@link ParentWriteChildDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be deleted.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseDeleteAllChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>, C extends BaseChildCriteria<ID>, R extends BaseDomainCrudRepository<ID, D, C>> extends ParentWriteChildDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseDeleteAllChildDomainCrudService.class);

    /**
     * Executes the delete operation to remove all entities that match the specified criteria.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param user A user associated with the operation.
     * @return A Mono of a collection of entity IDs that were deleted.
     * @throws DomainNotFoundException if the entities to be deleted are not found.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     */
    @Transactional
    @MethodStats
    default Mono<@NonNull Collection<ID>> deleteAll(ID parentId, USER user) throws DomainNotFoundException, BadRequestException {
        logger.debug("Delete, user: {}", user);

        C criteria = newCriteriaClass();
        criteria.setParentId(Filter.eq(parentId));

        return doDeleteMany(criteria, user);
    }


}