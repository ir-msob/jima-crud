package ir.msob.jima.crud.api.restful.service.childdomain.base;

import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.core.it.childcriteria.ProjectChildCriteria;
import ir.msob.jima.core.it.childdomain.ProjectChildDomain;
import ir.msob.jima.core.it.childdto.ProjectChildDto;
import ir.msob.jima.core.it.criteria.ProjectCriteria;
import ir.msob.jima.core.it.domain.ProjectDomain;
import ir.msob.jima.core.it.dto.ProjectDto;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.crud.api.restful.service.childdomain.BaseChildDomainCrudRestResource;
import ir.msob.jima.crud.api.restful.service.domain.BaseDomainCrudRestResource;
import ir.msob.jima.crud.api.restful.service.domain.base.ProjectUserService;
import ir.msob.jima.crud.ral.mongo.it.base.ChildDomainCrudService;
import ir.msob.jima.crud.ral.mongo.it.base.DomainCrudService;
import ir.msob.jima.crud.ral.mongo.it.base.MongoChildDomainCrudRepository;
import ir.msob.jima.crud.ral.mongo.it.base.MongoDomainCrudRepository;
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
