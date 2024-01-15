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
 * @param <D>
 * @author Yaqub Abdi
 */
public interface BaseCrudMongoRepository<USER extends BaseUser<ObjectId>, D extends BaseDomain<ObjectId>, C extends BaseCriteria<ObjectId>>
        extends BaseCrudRepository<ObjectId, USER, D, C, QueryBuilder> {

    ReactiveMongoTemplate getReactiveMongoTemplate();

    @Override
    @MethodStats
    default Mono<D> insertOne(D domain) {
        return this.getReactiveMongoTemplate().insert(domain);
    }

    @Override
    @MethodStats
    default Flux<D> insertMany(Collection<D> domains) {
        return this.getReactiveMongoTemplate().insertAll(domains);
    }

    @Override
    @MethodStats
    default Mono<D> updateOne(D domain) {
        return this.getReactiveMongoTemplate().save(domain);
    }

    @Override
    @MethodStats
    default Flux<D> updateMany(Iterable<D> domains) {
        return Flux.fromIterable(domains).flatMap(this.getReactiveMongoTemplate()::save);
    }

    @Override
    @MethodStats
    default Mono<D> getOne(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().findOne(queryBuilder.getQuery(), getDomainClass());
    }

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


    @Override
    @MethodStats
    default Flux<D> getMany(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().find(queryBuilder.getQuery(), getDomainClass());
    }

    @Override
    @MethodStats
    default Mono<D> removeOne(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().findAndRemove(queryBuilder.getQuery(), getDomainClass());
    }

    @Override
    @MethodStats
    default Flux<D> removeMany(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().findAllAndRemove(queryBuilder.getQuery(), getDomainClass());
    }

    @Override
    @MethodStats
    default Flux<D> removeAll() {
        return this.getReactiveMongoTemplate().findAllAndRemove(new Query(), getDomainClass());
    }

    @Override
    @MethodStats
    default Mono<Long> count(QueryBuilder queryBuilder) {
        return this.getReactiveMongoTemplate().count(queryBuilder.getQuery(), getDomainClass());
    }

    @Override
    @MethodStats
    default Mono<Long> countAll() {
        return this.getReactiveMongoTemplate().count(new QueryBuilder().getQuery(), getDomainClass());
    }

    @Override
    @MethodStats
    default QueryBuilder generateQuery(C criteria) {
        return new QueryGenerator<C>().generateQuery(criteria, null);
    }

    @Override
    default QueryBuilder generateQuery(C criteria, Pageable pageable) {
        return new QueryGenerator<C>().generateQuery(criteria, pageable);
    }

    default QueryBuilder query() {
        return QueryBuilder.builder();
    }

    default QueryBuilder agg() {
        return QueryBuilder.builder();
    }
}
