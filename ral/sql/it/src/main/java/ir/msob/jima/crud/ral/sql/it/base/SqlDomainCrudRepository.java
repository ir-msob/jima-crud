package ir.msob.jima.crud.ral.sql.it.base;


import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.core.it.domain.ProjectDomain;
import ir.msob.jima.core.ral.sql.commons.BaseSqlRepository;
import ir.msob.jima.crud.ral.sql.commons.BaseDomainCrudSqlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

/**
 * @param <D>
 */
@RequiredArgsConstructor
public abstract class SqlDomainCrudRepository<D extends ProjectDomain>
        implements BaseDomainCrudSqlRepository<String, D>
        , BaseSqlRepository<String, D> {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final BaseQueryBuilder queryBuilder;

    @Override
    public R2dbcEntityTemplate getR2dbcEntityTemplate() {
        return r2dbcEntityTemplate;
    }

    @Override
    public BaseQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }
}