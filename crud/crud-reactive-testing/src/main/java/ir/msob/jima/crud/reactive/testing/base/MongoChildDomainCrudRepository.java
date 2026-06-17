package ir.msob.jima.crud.reactive.testing.base;


import ir.msob.jima.platform.api.repository.BaseQueryBuilder;
import ir.msob.jima.platform.mongo.reactive.BaseMongoReactiveRepository;
import ir.msob.jima.platform.testing.childcriteria.ProjectChildCriteria;
import ir.msob.jima.platform.testing.childdomain.ProjectChildDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * @param <D>
 */
@RequiredArgsConstructor
public abstract class MongoChildDomainCrudRepository<D extends ProjectChildDomain, C extends ProjectChildCriteria>
        implements BaseMongoReactiveRepository<String, D, C> {


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