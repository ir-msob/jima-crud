package ir.msob.jima.crud.testing.base;


import ir.msob.jima.platform.api.repository.BaseQueryBuilder;
import ir.msob.jima.platform.mongo.api.BaseMongoRepository;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @param <D>
 * @param <C>
 */
@RequiredArgsConstructor
public abstract class MongoDomainCrudRepository<D extends ProjectDomain, C extends ProjectCriteria>
        implements BaseMongoRepository<String, D, C> {


    private final MongoTemplate mongoTemplate;
    private final BaseQueryBuilder queryBuilder;

    @Override
    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    @Override
    public BaseQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }
}