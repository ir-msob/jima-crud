package ir.msob.jima.crud.restful.test.resource.domain.base;

import ir.msob.jima.crud.restful.core.resource.domain.BaseDomainCrudRestResource;
import ir.msob.jima.crud.testing.base.DomainCrudService;
import ir.msob.jima.crud.testing.base.MongoDomainCrudRepository;
import ir.msob.jima.platform.api.security.BaseUserService;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import ir.msob.jima.platform.testing.dto.ProjectDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <D>
 * @param <DTO>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public abstract class DomainCrudRestResource<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudRepository<D, C>,
        S extends DomainCrudService<D, DTO, C, R>
        > implements BaseDomainCrudRestResource<String, ProjectUser, D, DTO, C, R, S> {

    @Autowired
    ProjectUserService projectUserService;

    @Autowired
    S service;

    @Override
    public BaseUserService getUserService() {
        return projectUserService;
    }

    @Override
    public S getService() {
        return service;
    }
}
