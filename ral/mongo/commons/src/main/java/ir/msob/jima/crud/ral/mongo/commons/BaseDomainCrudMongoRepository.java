package ir.msob.jima.crud.ral.mongo.commons;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.ral.mongo.commons.query.MongoQuery;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * This interface represents a base repository for CRUD operations on MongoDB.
 * It extends the BaseDomainCrudRepository interface and provides methods for inserting, updating, getting, and removing documents.
 *
 * @param <D> The type of the domain.
 */
public interface BaseDomainCrudMongoRepository<ID extends Comparable<ID> & Serializable, D extends BaseDomain<ID>>
        extends BaseDomainCrudRepository<ID, D> {

    /**
     * Get the ReactiveMongoTemplate instance.
     *
     * @return the ReactiveMongoTemplate instance.
     */
    ReactiveMongoTemplate getReactiveMongoTemplate();

    /**
     * Insert a single domain into the database.
     *
     * @param domain the domain to insert.
     * @return a Mono emitting the inserted domain.
     */
    @Override
    @MethodStats
    default Mono<D> insertOne(D domain) {
        return this.getReactiveMongoTemplate().insert(domain);
    }

    /**
     * Insert multiple domains into the database.
     *
     * @param domains the domains to insert.
     * @return a Flux emitting the inserted domains.
     */
    @Override
    @MethodStats
    default Flux<D> insertMany(Collection<D> domains) {
        return this.getReactiveMongoTemplate().insertAll(domains);
    }

    /**
     * Update a single domain in the database.
     *
     * @param domain the domain to update.
     * @return a Mono emitting the updated domain.
     */
    @Override
    @MethodStats
    default Mono<D> updateOne(D domain) {
        return this.getReactiveMongoTemplate().save(domain);
    }

    /**
     * Update multiple domains in the database.
     *
     * @param domains the domains to update.
     * @return a Flux emitting the updated domains.
     */
    @Override
    @MethodStats
    default Flux<D> updateMany(Iterable<D> domains) {
        return Flux.fromIterable(domains).flatMap(this.getReactiveMongoTemplate()::save);
    }

    /**
     * Get a single domain from the database based on the provided query.
     *
     * @param baseQuery the query to execute.
     * @return a Mono emitting the found domain.
     */
    @Override
    @MethodStats
    default Mono<D> getOne(BaseQuery baseQuery) {
        MongoQuery mongoQuery = (MongoQuery) baseQuery;
        return this.getReactiveMongoTemplate().findOne(mongoQuery.getQuery(), getDomainClass());
    }

    /**
     * Get a page of domains from the database based on the provided query and pageable.
     *
     * @param baseQuery the query to execute.
     * @param pageable  the pageable to apply.
     * @return a Mono emitting a Page of found domains.
     */
    @Override
    @MethodStats
    default Mono<Page<D>> getPage(BaseQuery baseQuery, Pageable pageable) {
        MongoQuery mongoQuery = (MongoQuery) baseQuery;
        return this.getReactiveMongoTemplate().count(mongoQuery.getQuery(), getDomainClass())
                .flatMap(count -> {
                    if (count == 0L) {
                        return Mono.just(new PageImpl<>(Collections.emptyList(), pageable, 0L));
                    } else {
                        baseQuery.with(pageable);
                        return this.getReactiveMongoTemplate().find(mongoQuery.getQuery(), getDomainClass())
                                .collectList()
                                .map(list -> new PageImpl<>(list, pageable, count));
                    }
                });
    }

    /**
     * Get multiple domains from the database based on the provided query.
     *
     * @param baseQuery the query to execute.
     * @return a Flux emitting the found domains.
     */
    @Override
    @MethodStats
    default Flux<D> getMany(BaseQuery baseQuery) {
        MongoQuery mongoQuery = (MongoQuery) baseQuery;
        return this.getReactiveMongoTemplate().find(mongoQuery.getQuery(), getDomainClass());
    }

    /**
     * Remove a single domain from the database based on the provided query.
     *
     * @param baseQuery the query to execute.
     * @return a Mono emitting the removed domain.
     */
    @Override
    @MethodStats
    default Mono<D> removeOne(BaseQuery baseQuery) {
        MongoQuery mongoQuery = (MongoQuery) baseQuery;
        return this.getReactiveMongoTemplate().findAndRemove(mongoQuery.getQuery(), getDomainClass());
    }

    /**
     * Remove multiple domains from the database based on the provided query.
     *
     * @param baseQuery the query to execute.
     * @return a Flux emitting the removed domains.
     */
    @Override
    @MethodStats
    default Flux<D> removeMany(BaseQuery baseQuery) {
        MongoQuery mongoQuery = (MongoQuery) baseQuery;
        return this.getReactiveMongoTemplate().findAllAndRemove(mongoQuery.getQuery(), getDomainClass());
    }

    /**
     * Remove all domains from the database.
     *
     * @return a Flux emitting the removed domains.
     */
    @Override
    @MethodStats
    default Flux<D> removeAll() {
        return this.getReactiveMongoTemplate().findAllAndRemove(new Query(), getDomainClass());
    }

    /**
     * Count the number of domains in the database that match the provided query.
     *
     * @param baseQuery the query to execute.
     * @return a Mono emitting the count.
     */
//    @Override
    @MethodStats
    default Mono<Long> count(BaseQuery baseQuery) {
        MongoQuery mongoQuery = (MongoQuery) baseQuery;
        return this.getReactiveMongoTemplate().count(mongoQuery.getQuery(), getDomainClass());
    }

    /**
     * Count the total number of domains in the database.
     *
     * @return a Mono emitting the count.
     */
    @Override
    @MethodStats
    default Mono<Long> countAll() {
        return this.getReactiveMongoTemplate().count(new MongoQuery().getQuery(), getDomainClass());
    }

}