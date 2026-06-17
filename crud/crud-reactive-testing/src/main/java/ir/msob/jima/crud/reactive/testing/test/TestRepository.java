package ir.msob.jima.crud.reactive.testing.test;

import ir.msob.jima.crud.reactive.testing.base.MongoDomainCrudReactiveRepository;
import ir.msob.jima.platform.api.repository.BaseQueryBuilder;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository extends MongoDomainCrudReactiveRepository<TestDomain, TestCriteria> {

    public TestRepository(ReactiveMongoTemplate reactiveMongoTemplate, BaseQueryBuilder queryBuilder) {
        super(reactiveMongoTemplate, queryBuilder);
    }

}

