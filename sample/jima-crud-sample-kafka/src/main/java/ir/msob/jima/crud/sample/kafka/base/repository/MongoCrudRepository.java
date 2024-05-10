package ir.msob.jima.crud.sample.kafka.base.repository;


import ir.msob.jima.core.ral.mongo.commons.BaseMongoRepository;
import ir.msob.jima.crud.ral.mongo.commons.BaseCrudMongoRepository;
import ir.msob.jima.crud.sample.kafka.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.kafka.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.kafka.base.security.ProjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

/**
 * @param <D>
 * @param <C>
 */
public class MongoCrudRepository<D extends ProjectDomain, C extends ProjectCriteria>
        implements BaseCrudMongoRepository<ProjectUser, D, C>
        , BaseMongoRepository<ProjectUser, D> {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public ReactiveMongoTemplate getReactiveMongoTemplate() {
        return reactiveMongoTemplate;
    }
}
