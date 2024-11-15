package ir.msob.jima.crud.ral.mongo.it.test;

import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.crud.ral.mongo.it.base.MongoCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository extends MongoCrudRepository<TestDomain, TestCriteria> {
    @Override
    public QueryBuilder criteria(QueryBuilder query, TestCriteria criteria, ProjectUser projectUser) {
        return super.criteria(query, criteria, projectUser);
    }

}

