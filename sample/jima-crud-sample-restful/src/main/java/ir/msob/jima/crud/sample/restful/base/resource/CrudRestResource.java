package ir.msob.jima.crud.sample.restful.base.resource;

import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.api.restful.service.rest.BaseCrudRestResource;
import ir.msob.jima.crud.sample.restful.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.restful.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.restful.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.restful.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.restful.base.security.ProjectUser;
import ir.msob.jima.crud.sample.restful.base.security.ProjectUserService;
import ir.msob.jima.crud.sample.restful.base.service.CrudService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <D>
 * @param <DTO>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public abstract class CrudRestResource<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>
        > implements
        BaseCrudRestResource<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S> {

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
