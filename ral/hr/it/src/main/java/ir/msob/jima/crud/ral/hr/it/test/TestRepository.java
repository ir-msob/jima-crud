package ir.msob.jima.crud.ral.hr.it.test;

import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.core.ral.hr.it.test.TestDomain;
import ir.msob.jima.crud.ral.hr.it.base.R2dbcDomainCrudRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository extends R2dbcDomainCrudRepository<TestDomain> {


    public TestRepository(R2dbcEntityTemplate r2dbcEntityTemplate, BaseQueryBuilder queryBuilder) {
        super(r2dbcEntityTemplate, queryBuilder);
    }
}

