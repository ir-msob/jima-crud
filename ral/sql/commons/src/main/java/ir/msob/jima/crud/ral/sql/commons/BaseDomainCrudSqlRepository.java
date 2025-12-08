package ir.msob.jima.crud.ral.sql.commons;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.ral.sql.commons.query.SqlQuery;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * Default CRUD repository base for domain objects over R2DBC.
 */
public interface BaseDomainCrudSqlRepository<ID extends Comparable<ID> & Serializable, D extends BaseDomain<ID>>
        extends BaseDomainCrudRepository<ID, D> {

    R2dbcEntityTemplate getR2dbcEntityTemplate();

    @Override
    @MethodStats
    default Mono<D> insertOne(D domain) {
        return this.getR2dbcEntityTemplate().insert(domain);
    }

    @Override
    @MethodStats
    default Flux<D> insertMany(Collection<D> domains) {
        return Flux.fromIterable(domains).flatMap(this.getR2dbcEntityTemplate()::insert);
    }

    @Override
    @MethodStats
    default Mono<D> updateOne(D domain) {
        return this.getR2dbcEntityTemplate().update(domain);
    }

    @Override
    @MethodStats
    default Flux<D> updateMany(Iterable<D> domains) {
        return Flux.fromIterable(domains).flatMap(this.getR2dbcEntityTemplate()::update);
    }

    @Override
    @MethodStats
    default Mono<D> getOne(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        var sqlQuery = (SqlQuery) baseQuery;
        return this.getR2dbcEntityTemplate().select(getDomainClass()).matching(sqlQuery.getQuery()).one();
    }

    @Override
    @MethodStats
    default Mono<Page<D>> getPage(ir.msob.jima.core.commons.repository.BaseQuery baseQuery, Pageable pageable) {
        var sqlQuery = (SqlQuery) baseQuery;
        Query q = sqlQuery.getQuery();
        return this.getR2dbcEntityTemplate().count(q, getDomainClass())
                .flatMap(count -> {
                    if (count == 0L) {
                        return Mono.just(new PageImpl<>(Collections.emptyList(), pageable, 0L));
                    } else {
                        return this.getR2dbcEntityTemplate().select(getDomainClass()).matching(q).all().collectList()
                                .map(list -> new PageImpl<>(list, pageable, count));
                    }
                });
    }

    @Override
    @MethodStats
    default Flux<D> getMany(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        var sqlQuery = (SqlQuery) baseQuery;
        return this.getR2dbcEntityTemplate().select(getDomainClass()).matching(sqlQuery.getQuery()).all();
    }

    @Override
    @MethodStats
    default Mono<D> removeOne(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        var sqlQuery = (SqlQuery) baseQuery;
        return this.getR2dbcEntityTemplate().select(getDomainClass()).matching(sqlQuery.getQuery()).one()
                .flatMap(entity -> this.getR2dbcEntityTemplate().delete(Query.query(Criteria.where(getIdName()).is(entity.getId())), getDomainClass())
                        .thenReturn(entity));
    }

    @SneakyThrows
    private String getIdName() {
        return getDomainClass().getConstructor().newInstance().getIdName();
    }

    @Override
    @MethodStats
    default Flux<D> removeMany(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        var sqlQuery = (SqlQuery) baseQuery;

        return this.getR2dbcEntityTemplate()
                .select(getDomainClass())
                .matching(sqlQuery.getQuery())
                .all()                          // Flux<D>
                .collectList()                  // Mono<List<D>>
                .flatMapMany(list ->
                        this.getR2dbcEntityTemplate()
                                .delete(getDomainClass())
                                .matching(sqlQuery.getQuery())
                                .all()          // Mono<Long>
                                .thenMany(Flux.fromIterable(list)) // ✔ Flux<D>
                );
    }


    @Override
    @MethodStats
    default Flux<D> removeAll() {
        return this.getR2dbcEntityTemplate().select(getDomainClass()).all()
                .collectList()
                .flatMapMany(list -> this.getR2dbcEntityTemplate().delete(getDomainClass()).all().flatMapMany(c -> Flux.fromIterable(list)));
    }

    @MethodStats
    default Mono<Long> count(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        var sqlQuery = (SqlQuery) baseQuery;
        return this.getR2dbcEntityTemplate().count(sqlQuery.getQuery(), getDomainClass());
    }

    @Override
    @MethodStats
    default Mono<Long> countAll() {
        return this.getR2dbcEntityTemplate().count(Query.empty(), getDomainClass());
    }
}
