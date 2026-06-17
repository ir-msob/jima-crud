package ir.msob.jima.crud.testing.base;


import ir.msob.jima.platform.api.repository.BaseQueryBuilder;
import ir.msob.jima.platform.mongo.api.BaseMongoRepository;
import ir.msob.jima.platform.testing.childcriteria.ProjectChildCriteria;
import ir.msob.jima.platform.testing.childdomain.ProjectChildDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @param <D>
 */
@RequiredArgsConstructor
public abstract class MongoChildDomainCrudRepository<D extends ProjectChildDomain, C extends ProjectChildCriteria>
        implements BaseMongoRepository<String, D, C> {


    private final MongoTemplate reactiveMongoTemplate;
    private final BaseQueryBuilder queryBuilder;

    @Override
    public MongoTemplate getMongoTemplate() {
        return reactiveMongoTemplate;
    }

    @Override
    public BaseQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }
}