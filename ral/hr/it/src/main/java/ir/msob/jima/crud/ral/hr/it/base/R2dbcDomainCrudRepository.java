package ir.msob.jima.crud.ral.hr.it.base;


import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.core.it.domain.ProjectDomain;
import ir.msob.jima.core.ral.hr.commons.BaseR2dbcRepository;
import ir.msob.jima.crud.ral.hr.commons.BaseDomainCrudR2dbcRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

/**
 * @param <D>
 */
@RequiredArgsConstructor
public abstract class R2dbcDomainCrudRepository<D extends ProjectDomain>
        implements BaseDomainCrudR2dbcRepository<String, D>
        , BaseR2dbcRepository<String, D> {

    @Getter
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    @Getter
    private final BaseQueryBuilder queryBuilder;

}