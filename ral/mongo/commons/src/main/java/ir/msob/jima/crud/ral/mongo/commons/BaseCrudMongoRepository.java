package ir.msob.jima.crud.ral.mongo.commons;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.core.ral.mongo.commons.query.QueryGenerator;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;

/**
 * This interface represents a base repository for CRUD operations on MongoDB.
 * It extends the BaseCrudRepository interface and provides methods for inserting, updating, getting, and removing documents.
 *
 * @param <USER> The type of the user.
 * @param <D>    The type of the domain.
 * @param <C>    The type of criteria used for querying.
 */
public interface BaseCrudMongoRepository<USER extends BaseUser, D extends BaseDomain<ObjectId>, C extends BaseCriteria<ObjectId>>
        extends BaseCrudRepository<ObjectId, USER, D, C, QueryBuilder> {

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
     * @param queryBuilder the query to execute.
     * @return a Mono emitting the found domain.
     */
    @Override
    @MethodStats
    default Mono<D> getOne(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().findOne(queryBuilder.getQuery(), getDomainClass());
    }

    /**
     * Get a page of domains from the database based on the provided query and pageable.
     *
     * @param queryBuilder the query to execute.
     * @param pageable     the pageable to apply.
     * @return a Mono emitting a Page of found domains.
     */
    @Override
    @MethodStats
    default Mono<Page<D>> getPage(QueryBuilder queryBuilder, Pageable pageable) {
        return this.getReactiveMongoTemplate().count(queryBuilder.getQuery(), getDomainClass())
                .flatMap(count -> {
                    if (count == 0L) {
                        return Mono.just(new PageImpl<>(Collections.emptyList(), pageable, 0L));
                    } else {
                        queryBuilder.with(pageable);
                        return this.getReactiveMongoTemplate().find(queryBuilder.getQuery(), getDomainClass())
                                .collectList()
                                .map(list -> new PageImpl<>(list, pageable, count));
                    }
                });
    }

    /**
     * Get multiple domains from the database based on the provided query.
     *
     * @param queryBuilder the query to execute.
     * @return a Flux emitting the found domains.
     */
    @Override
    @MethodStats
    default Flux<D> getMany(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().find(queryBuilder.getQuery(), getDomainClass());
    }

    /**
     * Remove a single domain from the database based on the provided query.
     *
     * @param queryBuilder the query to execute.
     * @return a Mono emitting the removed domain.
     */
    @Override
    @MethodStats
    default Mono<D> removeOne(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().findAndRemove(queryBuilder.getQuery(), getDomainClass());
    }

    /**
     * Remove multiple domains from the database based on the provided query.
     *
     * @param queryBuilder the query to execute.
     * @return a Flux emitting the removed domains.
     */
    @Override
    @MethodStats
    default Flux<D> removeMany(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().findAllAndRemove(queryBuilder.getQuery(), getDomainClass());
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
     * @param queryBuilder the query to execute.
     * @return a Mono emitting the count.
     */
    @Override
    @MethodStats
    default Mono<Long> count(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().count(queryBuilder.getQuery(), getDomainClass());
    }

    /**
     * Count the total number of domains in the database.
     *
     * @return a Mono emitting the count.
     */
    @Override
    @MethodStats
    default Mono<Long> countAll() {
        return this.getReactiveMongoTemplate().count(new QueryBuilder().getQuery(), getDomainClass());
    }

    /**
     * Generate a query based on the provided criteria.
     *
     * @param criteria the criteria to use for generating the query.
     * @return the generated query.
     */
    @Override
    @MethodStats
    default QueryBuilder generateQuery(C criteria) {
        return new QueryGenerator<C>().generateQuery(criteria, null);
    }

    /**
     * Generate a query based on the provided criteria and pageable.
     *
     * @param criteria the criteria to use for generating the query.
     * @param pageable the pageable to apply.
     * @return the generated query.
     */
    @Override
    default QueryBuilder generateQuery(C criteria, Pageable pageable) {
        return new QueryGenerator<C>().generateQuery(criteria, pageable);
    }

    /**
     * Create a new QueryBuilder instance for building a query.
     *
     * @return the new QueryBuilder instance.
     */
    default QueryBuilder query() {
        return QueryBuilder.builder();
    }

    /**
     * Create a new QueryBuilder instance for building an aggregation.
     *
     * @return the new QueryBuilder instance.
     */
    default QueryBuilder agg() {
        return QueryBuilder.builder();
    }
}