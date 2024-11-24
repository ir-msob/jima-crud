package ir.msob.jima.crud.commons.common;

import ir.msob.jima.core.commons.domain.SampleDomain;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.SampleCriteria;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class ConcreteBaseCrudRepository implements BaseCrudRepository<String, BaseUser, SampleDomain<String>, SampleCriteria<String>, BaseQuery> {
    @Override
    public Mono<SampleDomain<String>> insertOne(SampleDomain<String> domain) {
        return null;
    }

    @Override
    public Flux<SampleDomain<String>> insertMany(Collection<SampleDomain<String>> domains) {
        return null;
    }

    @Override
    public Mono<SampleDomain<String>> updateOne(SampleDomain<String> domain) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Flux<SampleDomain<String>> updateMany(Iterable<SampleDomain<String>> domains) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<SampleDomain<String>> getOne(BaseQuery query) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<Page<SampleDomain<String>>> getPage(BaseQuery query, Pageable pageable) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Flux<SampleDomain<String>> getMany(BaseQuery query) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<SampleDomain<String>> removeOne(BaseQuery query) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Flux<SampleDomain<String>> removeMany(BaseQuery query) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Flux<SampleDomain<String>> removeAll() throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<Long> count(BaseQuery query) throws DomainNotFoundException {
        return null;
    }

    @Override
    public Mono<Long> countAll() throws DomainNotFoundException {
        return null;
    }

    @Override
    public BaseQuery generateQuery(SampleCriteria<String> criteria) {
        return null;
    }

    @Override
    public BaseQuery generateQuery(SampleCriteria<String> criteria, Pageable pageable) {
        return null;
    }
}
