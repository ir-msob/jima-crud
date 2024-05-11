package ir.msob.jima.crud.sample.graphql.restful.base.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.api.graphql.restful.service.resource.BaseCrudGraphqlRestResource;
import ir.msob.jima.crud.sample.graphql.restful.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.graphql.restful.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.graphql.restful.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.graphql.restful.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.graphql.restful.base.security.ProjectUser;
import ir.msob.jima.crud.sample.graphql.restful.base.security.ProjectUserService;
import ir.msob.jima.crud.sample.graphql.restful.base.service.CrudService;
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
public abstract class CrudGraphqlRestResource<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>
        > implements
        BaseCrudGraphqlRestResource<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S> {

    @Autowired
    ProjectUserService projectUserService;
    @Autowired
    ObjectMapper objectMapper;
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

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
