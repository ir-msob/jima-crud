package ir.msob.jima.crud.commons.domain;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface defines CRUD repository methods for domain entities.
 *
 * @param <ID> The type of entity ID.
 * @param <D>  The type of domain entity.
 * @author Yaqub Abdi
 */
public interface BaseDomainCrudRepository<ID extends Comparable<ID> & Serializable, D extends BaseDomain<ID>>
        extends BaseRepository<ID, D> {

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
    Mono<D> getOne(BaseQuery query) throws DomainNotFoundException;

    /**
     * Get a page of domain entities that match a query.
     *
     * @param query    The query for finding domain entities.
     * @param pageable Pageable information for pagination.
     * @return A Mono that emits a Page containing domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Mono<Page<D>> getPage(BaseQuery query, Pageable pageable) throws DomainNotFoundException;

    /**
     * Get a list of domain entities that match a query.
     *
     * @param query The query for finding domain entities.
     * @return A Flux that emits a list of domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Flux<D> getMany(BaseQuery query) throws DomainNotFoundException;

    /**
     * Remove one domain entity that matches a query.
     *
     * @param query The query for finding a domain entity to remove.
     * @return A Mono that emits the removed domain entity.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Mono<D> removeOne(BaseQuery query) throws DomainNotFoundException;

    /**
     * Remove multiple domain entities that match a query.
     *
     * @param query The query for finding domain entities to remove.
     * @return A Flux that emits the removed domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Flux<D> removeMany(BaseQuery query) throws DomainNotFoundException;

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
    Mono<Long> count(BaseQuery query) throws DomainNotFoundException;

    /**
     * Get the number of all records.
     *
     * @return A Mono that emits the count of all domain entities.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Mono<Long> countAll() throws DomainNotFoundException;


}
