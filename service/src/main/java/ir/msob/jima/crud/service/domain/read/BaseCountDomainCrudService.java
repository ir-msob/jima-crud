package ir.msob.jima.crud.service.domain.read;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * This interface defines a service for counting domain entities based on specific criteria.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for counting entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface BaseCountDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>> extends ParentReadDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger log = LoggerFactory.getLogger(BaseCountDomainCrudService.class);

    /**
     * Count domain entities based on specific criteria.
     *
     * @param criteria The criteria used for counting entities.
     * @param user     A user associated with the operation.
     * @return A Mono emitting the count of domain entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<Long> count(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        log.debug("Count, criteria: {}, user: {}", criteria, user);
        getBeforeAfterComponent().beforeCount(criteria, user, getBeforeAfterDomainOperations());
        BaseQuery baseQuery = this.getRepository().getQueryBuilder().build(criteria);
        return this.getRepository().count(baseQuery)
                .doOnSuccess(result -> getBeforeAfterComponent().afterCount(criteria, user, getBeforeAfterDomainOperations()));
    }
}
