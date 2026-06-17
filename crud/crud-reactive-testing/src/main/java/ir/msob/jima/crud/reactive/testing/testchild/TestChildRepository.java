package ir.msob.jima.crud.reactive.testing.testchild;

import ir.msob.jima.crud.reactive.testing.base.MongoChildDomainCrudRepository;
import ir.msob.jima.platform.api.repository.BaseQueryBuilder;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildCriteria;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildDomain;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestChildRepository extends MongoChildDomainCrudRepository<TestChildDomain, TestChildCriteria> {

    public TestChildRepository(ReactiveMongoTemplate reactiveMongoTemplate, BaseQueryBuilder queryBuilder) {
        super(reactiveMongoTemplate, queryBuilder);
    }

}

