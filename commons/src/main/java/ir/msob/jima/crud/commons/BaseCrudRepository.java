package ir.msob.jima.crud.commons;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.repository.BaseRepository;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface defines CRUD repository methods for domain entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <D>    The type of domain entity.
 * @param <USER> The type of the user context.
 * @param <C>    The type of criteria.
 * @param <Q>    The type of query.
 * @author Yaqub Abdi
 */
public interface BaseCrudRepository<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery>
        extends BaseRepository<ID, USER, D> {

    /**
     * Save a domain entity.
     *
     * @param domain The domain entity to be saved.
     * @return A Mono that emits the saved domain entity.
     */
    Mono<D> insertOne(D domain);

    /**
     * Save multiple domain entities.
     *
     * @param domains A collection of domain entities to be saved.
     * @return A Flux that emits the saved domain entities.
     */
    Flux<D> insertMany(Collection<D> domains);

    /**
     * Update a domain entity.
     *
     * @param domain The domain entity to be updated.
     * @return A Mono that emits the updated domain entity.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Mono<D> updateOne(D domain) throws DomainNotFoundException;

    /**
     * Update multiple domain entities.
     *
     * @param domains An iterable of domain entities to be updated.
     * @return A Flux that emits the updated domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Flux<D> updateMany(Iterable<D> domains) throws DomainNotFoundException;

    /**
     * Get the first domain entity that matches a query.
     *
     * @param query The query for finding a domain entity.
     * @return A Mono that emits the found domain entity.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Mono<D> getOne(Q query) throws DomainNotFoundException;

    /**
     * Get a page of domain entities that match a query.
     *
     * @param query    The query for finding domain entities.
     * @param pageable Pageable information for pagination.
     * @return A Mono that emits a Page containing domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Mono<Page<D>> getPage(Q query, Pageable pageable) throws DomainNotFoundException;

    /**
     * Get a list of domain entities that match a query.
     *
     * @param query The query for finding domain entities.
     * @return A Flux that emits a list of domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Flux<D> getMany(Q query) throws DomainNotFoundException;

    /**
     * Remove one domain entity that matches a query.
     *
     * @param query The query for finding a domain entity to remove.
     * @return A Mono that emits the removed domain entity.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Mono<D> removeOne(Q query) throws DomainNotFoundException;

    /**
     * Remove multiple domain entities that match a query.
     *
     * @param query The query for finding domain entities to remove.
     * @return A Flux that emits the removed domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Flux<D> removeMany(Q query) throws DomainNotFoundException;

    /**
     * Remove all domain entities.
     *
     * @return A Flux that emits all removed domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Flux<D> removeAll() throws DomainNotFoundException;

    /**
     * Get the number of records that match a query.
     *
     * @param query The query for counting domain entities.
     * @return A Mono that emits the count of matching domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Mono<Long> count(Q query) throws DomainNotFoundException;

    /**
     * Get the number of all records.
     *
     * @return A Mono that emits the count of all domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Mono<Long> countAll() throws DomainNotFoundException;

    /**
     * Generate a query from a criteria object.
     *
     * @param criteria The criteria object used to generate the query.
     * @return The generated query.
     */
    Q generateQuery(C criteria);

    /**
     * Generate a query from a criteria object with pagination information.
     *
     * @param criteria The criteria object used to generate the query.
     * @param pageable Pageable information for pagination.
     * @return The generated query with pagination details.
     */
    Q generateQuery(C criteria, Pageable pageable);

    /**
     * Provide default criteria for a query.
     *
     * @param query    The query to be extended with criteria.
     * @param criteria The criteria object used for extending the query.
     * @param user     A user context.
     * @return The extended query with criteria.
     */
    default Q criteria(Q query, C criteria, USER user) {
        return query;
    }

    /**
     * Get the class of the criteria.
     *
     * @return The class representing the criteria.
     */
    default Class<C> getCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), BaseCrudRepository.class, 3);
    }
}
