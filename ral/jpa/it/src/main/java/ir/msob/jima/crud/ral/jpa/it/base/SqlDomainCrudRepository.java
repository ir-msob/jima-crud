package ir.msob.jima.crud.ral.jpa.it.base;


import ir.msob.jima.core.commons.repository.BaseQueryBuilder;
import ir.msob.jima.core.it.domain.ProjectDomain;
import ir.msob.jima.core.ral.jpa.commons.BaseJpaRepository;
import ir.msob.jima.crud.ral.jpa.commons.BaseDomainCrudJpaRepository;
import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @param <D>
 */
@RequiredArgsConstructor
public abstract class SqlDomainCrudRepository<D extends ProjectDomain>
        implements BaseDomainCrudJpaRepository<String, D>
        , BaseJpaRepository<String, D> {

    @Getter
    private final EntityManager entityManager;
    @Getter
    private final BaseQueryBuilder queryBuilder;

}