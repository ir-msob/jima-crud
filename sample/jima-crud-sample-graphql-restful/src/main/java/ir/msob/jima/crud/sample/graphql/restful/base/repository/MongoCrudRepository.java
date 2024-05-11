package ir.msob.jima.crud.sample.graphql.restful.base.repository;


import ir.msob.jima.core.ral.mongo.commons.BaseMongoRepository;
import ir.msob.jima.crud.ral.mongo.commons.BaseCrudMongoRepository;
import ir.msob.jima.crud.sample.graphql.restful.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.graphql.restful.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.graphql.restful.base.security.ProjectUser;
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
