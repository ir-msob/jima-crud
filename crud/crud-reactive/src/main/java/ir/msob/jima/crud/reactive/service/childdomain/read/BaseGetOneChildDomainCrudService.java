package ir.msob.jima.crud.reactive.service.childdomain.read;

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
 * This interface defines a service for retrieving a single DTO entity based on specific criteria or an entity ID.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for filtering entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface BaseGetOneChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>> extends ParentReadChildDomainCrudService<ID, USER, D, DTO, C, R> {

    Logger logger = LoggerFactory.getLogger(BaseGetOneChildDomainCrudService.class);

    /**
     * Retrieve a single DTO entity based on an entity ID.
     *
     * @param id   The ID of the entity to retrieve.
     * @param user A user associated with the operation.
     * @return A Mono emitting a single DTO entity.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<@NonNull DTO> getOne(ID parentId, ID id, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = CriteriaUtil.idCriteria(getCriteriaClass(), id);
        criteria.setParentId(Filter.eq(parentId));

        return this.doGetOne(criteria, user);
    }

    /**
     * Retrieve a single DTO entity based on specific criteria.
     *
     * @param criteria The criteria used for filtering entities.
     * @param user     A user associated with the operation.
     * @return A Mono emitting a single DTO entity.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<@NonNull DTO> getOne(ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        logger.debug("GetOne, criteria: {}, user: {}", criteria, user);

        criteria.setParentId(Filter.eq(parentId));

        return this.doGetOne(criteria, user);
    }

}