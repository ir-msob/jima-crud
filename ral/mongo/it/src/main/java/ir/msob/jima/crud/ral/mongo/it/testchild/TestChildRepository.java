package ir.msob.jima.crud.ral.mongo.it.testchild;

import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildCriteria;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildDomain;
import ir.msob.jima.crud.ral.mongo.it.base.MongoChildDomainCrudRepository;
import ir.msob.jima.crud.ral.mongo.it.base.MongoDomainCrudRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestChildRepository extends MongoChildDomainCrudRepository<TestChildDomain, TestChildCriteria> {

    public TestChildRepository(ReactiveMongoTemplate reactiveMongoTemplate, BaseQueryBuilder queryBuilder) {
        super(reactiveMongoTemplate, queryBuilder);
    }

}

