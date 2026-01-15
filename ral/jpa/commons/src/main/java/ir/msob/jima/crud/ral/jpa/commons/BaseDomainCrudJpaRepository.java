package ir.msob.jima.crud.ral.jpa.commons;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.ral.jpa.commons.BaseJpaRepository;
import ir.msob.jima.core.ral.jpa.commons.query.JpaQuery;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.Serializable;
import java.util.Collection;

/**
 * Default CRUD repository base for domain objects over JPA (EntityManager).
 * <p>
 * This interface expects that BaseSqlRepository<ID, D> (JPA helpers like find(), delete(), findPage() etc.)
 * is available in the project. We delegate heavy-lifting to those defaults where appropriate.
 */
public interface BaseDomainCrudJpaRepository<ID extends Comparable<ID> & Serializable, D extends BaseDomain<ID>, C extends BaseCriteria<ID>>
        extends BaseDomainCrudRepository<ID, D, C>, BaseJpaRepository<ID, D> {

    /**
     * Implementations MUST provide EntityManager.
     */
    EntityManager getEntityManager();

    /* ------------------- create / insert ------------------- */

    @Override
    @Transactional
    @MethodStats
    default Mono<D> insertOne(D domain) {
        return Mono.fromCallable(() -> {
            EntityManager em = getEntityManager();
            em.persist(domain);
            return domain;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    @Transactional
    @MethodStats
    default Flux<D> insertMany(Collection<D> domains) {
        return Flux.fromIterable(domains)
                .flatMap(d -> Mono.fromCallable(() -> {
                    EntityManager em = getEntityManager();
                    em.persist(d);
                    return d;
                }).subscribeOn(Schedulers.boundedElastic()));
    }

    /* ------------------- update ------------------- */

    @Override
    @Transactional
    @MethodStats
    default Mono<D> updateOne(D domain) {
        return Mono.fromCallable(() -> {
            EntityManager em = getEntityManager();
            return em.merge(domain);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    @Transactional
    @MethodStats
    default Flux<D> updateMany(Iterable<D> domains) {
        return Flux.fromIterable(domains)
                .flatMap(d -> Mono.fromCallable(() -> {
                    EntityManager em = getEntityManager();
                    return em.merge(d);
                }).subscribeOn(Schedulers.boundedElastic()));
    }

    /* ------------------- read ------------------- */

    @Override
    @Transactional(readOnly = true)
    @MethodStats
    @SuppressWarnings("unchecked")
    default Mono<D> getOne(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        // delegate to BaseSqlRepository.findOne(JpaQuery)
        var sqlQuery = (JpaQuery<D>) baseQuery;
        return findOne(sqlQuery);
    }

    @Override
    @Transactional(readOnly = true)
    @MethodStats
    @SuppressWarnings("unchecked")
    default Mono<Page<D>> getPage(ir.msob.jima.core.commons.repository.BaseQuery baseQuery, Pageable pageable) {
        var sqlQuery = (JpaQuery<D>) baseQuery;
        // if pageable provided by caller, apply it to the JpaQuery
        if (pageable != null) sqlQuery.with(pageable);
        // Delegate to BaseSqlRepository.findPage(JpaQuery)
        return findPage(sqlQuery);
    }

    @Override
    @Transactional(readOnly = true)
    @MethodStats
    @SuppressWarnings("unchecked")
    default Flux<D> getMany(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        var sqlQuery = (JpaQuery<D>) baseQuery;
        return find(sqlQuery);
    }

    /* ------------------- delete ------------------- */

    @Override
    @Transactional
    @MethodStats
    @SuppressWarnings("unchecked")
    default Mono<D> removeOne(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        var sqlQuery = (JpaQuery<D>) baseQuery;
        // find the entity then remove it (in boundedElastic)
        return findOne(sqlQuery)
                .flatMap(entity -> Mono.fromCallable(() -> {
                    EntityManager em = getEntityManager();
                    // ensure managed instance
                    Object managed = em.contains(entity) ? entity : em.merge(entity);
                    em.remove(managed);
                    return entity;
                }).subscribeOn(Schedulers.boundedElastic()));
    }

    @Override
    @Transactional
    @MethodStats
    @SuppressWarnings("unchecked")
    default Flux<D> removeMany(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        var sqlQuery = (JpaQuery<D>) baseQuery;

        return find(sqlQuery)                // Flux<D>
                .collectList()               // Mono<List<D>>
                .flatMapMany(list ->
                        delete(sqlQuery)     // delegate to BaseSqlRepository.delete(JpaQuery) -> Mono<Boolean>
                                .flatMapMany(ignored -> Flux.fromIterable(list))
                );
    }

    @Override
    @Transactional
    @MethodStats
    default Flux<D> removeAll() {
        // fetch all entities, then delete all (delegating to delete with empty JpaQuery)
        return Mono.fromCallable(() -> {
                    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
                    CriteriaQuery<D> cq = cb.createQuery(getDomainClass());
                    Root<D> root = cq.from(getDomainClass());
                    cq.select(root);
                    return getEntityManager().createQuery(cq).getResultList();
                }).subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(list ->
                        delete(new JpaQuery<D>()) // delete all via BaseSqlRepository.delete(JpaQuery)
                                .flatMapMany(ignored -> Flux.fromIterable(list))
                );
    }

    /* ------------------- count ------------------- */

    @Override
    @Transactional(readOnly = true)
    @MethodStats
    @SuppressWarnings("unchecked")
    default Mono<Long> count(ir.msob.jima.core.commons.repository.BaseQuery baseQuery) {
        var sqlQuery = (JpaQuery<D>) baseQuery;
        // build count query here (in boundedElastic)
        return Mono.fromCallable(() -> {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
            Root<D> root = countCq.from(getDomainClass());
            var predicate = buildPredicateFromSpecification(cb, root, countCq, sqlQuery);
            if (predicate != null) countCq.where(predicate);
            countCq.select(cb.count(root));
            return getEntityManager().createQuery(countCq).getSingleResult();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<Long> countAll() {
        return Mono.fromCallable(() -> {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
            Root<D> root = countCq.from(getDomainClass());
            countCq.select(cb.count(root));
            return getEntityManager().createQuery(countCq).getSingleResult();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /* ------------------- helper ------------------- */

    @SneakyThrows
    default String getIdName() {
        return getDomainClass().getConstructor().newInstance().getIdName();
    }
}
