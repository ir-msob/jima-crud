package ir.msob.jima.crud.ral.jpa.it.test;

import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.core.ral.jpa.it.test.TestDomain;
import ir.msob.jima.crud.ral.jpa.it.base.SqlDomainCrudRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository extends SqlDomainCrudRepository<TestDomain> {


    public TestRepository(EntityManager entityManager, BaseQueryBuilder queryBuilder) {
        super(entityManager, queryBuilder);
    }
}

