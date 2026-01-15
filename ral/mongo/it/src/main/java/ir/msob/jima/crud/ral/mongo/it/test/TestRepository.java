package ir.msob.jima.crud.ral.mongo.it.test;

import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.crud.ral.mongo.it.base.MongoDomainCrudRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository extends MongoDomainCrudRepository<TestDomain, TestCriteria> {

    public TestRepository(ReactiveMongoTemplate reactiveMongoTemplate, BaseQueryBuilder queryBuilder) {
        super(reactiveMongoTemplate, queryBuilder);
    }

}

