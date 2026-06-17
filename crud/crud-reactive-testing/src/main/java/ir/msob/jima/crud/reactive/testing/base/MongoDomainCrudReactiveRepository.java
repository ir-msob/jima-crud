package ir.msob.jima.crud.reactive.testing.base;


import ir.msob.jima.platform.api.repository.BaseQueryBuilder;
import ir.msob.jima.platform.mongo.reactive.BaseMongoReactiveRepository;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * @param <D>
 * @param <C>
 */
@RequiredArgsConstructor
public abstract class MongoDomainCrudReactiveRepository<D extends ProjectDomain, C extends ProjectCriteria>
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