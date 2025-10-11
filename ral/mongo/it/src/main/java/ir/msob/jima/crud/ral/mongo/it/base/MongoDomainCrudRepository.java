package ir.msob.jima.crud.ral.mongo.it.base;


import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.core.ral.mongo.commons.BaseMongoRepository;
import ir.msob.jima.core.ral.mongo.it.domain.ProjectDomain;
import ir.msob.jima.crud.ral.mongo.commons.BaseDomainCrudMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * @param <D>
 */
@RequiredArgsConstructor
public abstract class MongoDomainCrudRepository<D extends ProjectDomain>
        implements BaseDomainCrudMongoRepository<String, D>
        , BaseMongoRepository<String, D> {


    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final BaseQueryBuilder queryBuilder;

    @Override
    public ReactiveMongoTemplate getReactiveMongoTemplate() {
        return reactiveMongoTemplate;
    }

    @Override
    public BaseQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }
}