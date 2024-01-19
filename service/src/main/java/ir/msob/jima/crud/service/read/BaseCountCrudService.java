package ir.msob.jima.crud.service.read;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Optional;

/**
 * This interface defines a service for counting domain entities based on specific criteria.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for counting entities.
 * @param <Q>    The type of the query used for filtering entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface BaseCountCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>, Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentReadCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseCountCrudService.class);

    /**
     * Count domain entities based on specific criteria.
     *
     * @param criteria The criteria used for counting entities.
     * @param user     An optional user associated with the operation.
     * @return A Mono emitting the count of domain entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<Long> count(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        log.debug("Count, criteria: {}, user: {}", criteria, user.orElse(null));
        getBeforeAfterComponent().beforeCount(criteria, user, getBeforeAfterDomainServices());

        return this.countExecute(criteria, user)
                .doOnSuccess(result -> getBeforeAfterComponent().afterCount(criteria, user, getBeforeAfterDomainServices()));
    }

    /**
     * Execute the counting of domain entities based on specific criteria.
     *
     * @param criteria The criteria used for counting entities.
     * @param user     An optional user associated with the operation.
     * @return A Mono emitting the count of domain entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     */
    default Mono<Long> countExecute(C criteria, Optional<USER> user) throws DomainNotFoundException {
        Q baseQuery = this.getRepository().generateQuery(criteria);
        baseQuery = this.getRepository().criteria(baseQuery, criteria, user);
        return this.getRepository().count(baseQuery);
    }
}
