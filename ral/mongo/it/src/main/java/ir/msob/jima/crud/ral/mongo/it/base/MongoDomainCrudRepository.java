package ir.msob.jima.crud.ral.mongo.it.base;


import ir.msob.jima.core.ral.mongo.commons.BaseMongoRepository;
import ir.msob.jima.core.ral.mongo.it.criteria.ProjectCriteria;
import ir.msob.jima.core.ral.mongo.it.domain.ProjectDomain;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.crud.ral.mongo.commons.BaseDomainCrudMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * @param <D>
 * @param <C>
 */
@RequiredArgsConstructor
public class MongoDomainCrudRepository<D extends ProjectDomain, C extends ProjectCriteria>
        implements BaseDomainCrudMongoRepository<String, ProjectUser, D, C>
        , BaseMongoRepository<String, ProjectUser, D> {


    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public ReactiveMongoTemplate getReactiveMongoTemplate() {
        return reactiveMongoTemplate;
    }
}