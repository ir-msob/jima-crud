package ir.msob.jima.crud.commons.common;

import ir.msob.jima.core.commons.domain.SampleCriteria;
import ir.msob.jima.core.commons.domain.SampleDomain;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class ConcreteBaseDomainCrudRepository implements BaseDomainCrudRepository<String, SampleDomain<String>, SampleCriteria<String>> {
    @Override
    public Mono<@NonNull SampleDomain<String>> insertOne(SampleDomain<String> domain) {
        return null;
    }

    @Override
    public Flux<@NonNull SampleDomain<String>> insertMany(Collection<SampleDomain<String>> domains) {
        return null;
    }

    @Override
    public Mono<@NonNull SampleDomain<String>> updateOne(SampleDomain<String> domain) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Flux<@NonNull SampleDomain<String>> updateMany(Iterable<SampleDomain<String>> domains) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<@NonNull SampleDomain<String>> getOne(SampleCriteria<String> criteria) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<@NonNull Page<@NonNull SampleDomain<String>>> getPage(SampleCriteria<String> criteria, Pageable pageable) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Flux<@NonNull SampleDomain<String>> getMany(SampleCriteria<String> criteria) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<@NonNull SampleDomain<String>> removeOne(SampleCriteria<String> criteria) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Flux<@NonNull SampleDomain<String>> removeMany(SampleCriteria<String> criteria) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Flux<@NonNull SampleDomain<String>> removeAll() throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<@NonNull Long> count(SampleCriteria<String> criteria) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<@NonNull Long> countAll() throws DomainNotFoundException {
        return null;
    }

    @Override
    public BaseQueryBuilder getQueryBuilder() {
        return null;
    }
}
