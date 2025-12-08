package ir.msob.jima.crud.ral.sql.it.test;

import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.core.ral.sql.it.test.TestDomain;
import ir.msob.jima.crud.ral.sql.it.base.SqlDomainCrudRepository;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository extends SqlDomainCrudRepository<TestDomain> {

    public TestRepository(R2dbcEntityTemplate r2dbcEntityTemplate, BaseQueryBuilder queryBuilder) {
        super(r2dbcEntityTemplate, queryBuilder);
    }
}

