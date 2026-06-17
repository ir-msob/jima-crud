package ir.msob.jima.crud.restful.reactive.test.resource.childdomain.base;

import ir.msob.jima.crud.reactive.testing.base.ChildDomainCrudService;
import ir.msob.jima.crud.reactive.testing.base.MongoChildDomainCrudRepository;
import ir.msob.jima.crud.restful.reactive.resource.childdomain.BaseChildDomainCrudRestResource;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.base.ProjectUserService;
import ir.msob.jima.platform.api.security.BaseUserService;
import ir.msob.jima.platform.testing.childcriteria.ProjectChildCriteria;
import ir.msob.jima.platform.testing.childdomain.ProjectChildDomain;
import ir.msob.jima.platform.testing.childdto.ProjectChildDto;
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
public abstract class ChildDomainCrudRestResource<
        D extends ProjectChildDomain,
        DTO extends ProjectChildDto,
        C extends ProjectChildCriteria,
        R extends MongoChildDomainCrudRepository<D, C>,
        S extends ChildDomainCrudService<D, DTO, C, R>
        > implements
        BaseChildDomainCrudRestResource<String, ProjectUser, D, DTO, C, R, S> {

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
