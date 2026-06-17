package ir.msob.jima.crud.testing.test;

import ir.msob.jima.crud.testing.base.MongoDomainCrudRepository;
import ir.msob.jima.platform.api.repository.BaseQueryBuilder;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository extends MongoDomainCrudRepository<TestDomain, TestCriteria> {

    public TestRepository(MongoTemplate mongoTemplate, BaseQueryBuilder queryBuilder) {
        super(mongoTemplate, queryBuilder);
    }

}

