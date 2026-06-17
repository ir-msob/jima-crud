package ir.msob.jima.crud.reactive.service.childdomain.write;

import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.filter.Filter;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * This service interface defines the contract for executing delete operations on a single entity or based on specific criteria.
 * It extends the {@link ParentWriteChildDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be deleted.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseDeleteChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>, C extends BaseChildCriteria<ID>, R extends BaseReactiveRepository<ID, D, C>> extends ParentWriteChildDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseDeleteChildDomainCrudService.class);

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
    default Mono<@NonNull ID> delete(ID parentId, ID id, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = CriteriaUtil.idCriteria(getCriteriaClass(), id);
        criteria.setParentId(Filter.eq(parentId));
        return doDelete(criteria, user);
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
    default Mono<@NonNull ID> delete(ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        logger.debug("Delete, criteria: {}, user: {}", criteria, user);
        criteria.setParentId(Filter.eq(parentId));
        return doDelete(criteria, user);
    }

}