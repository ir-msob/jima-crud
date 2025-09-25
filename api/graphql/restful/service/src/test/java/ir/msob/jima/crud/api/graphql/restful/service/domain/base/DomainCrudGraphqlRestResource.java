package ir.msob.jima.crud.api.graphql.restful.service.domain.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.core.ral.mongo.it.criteria.ProjectCriteria;
import ir.msob.jima.core.ral.mongo.it.domain.ProjectDomain;
import ir.msob.jima.core.ral.mongo.it.dto.ProjectDto;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.crud.api.graphql.restful.service.domain.BaseDomainCrudGraphqlRestResource;
import ir.msob.jima.crud.ral.mongo.it.base.DomainCrudService;
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
public abstract class DomainCrudGraphqlRestResource<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudRepository<D, C>,
        S extends DomainCrudService<D, DTO, C, R>
        > implements
        BaseDomainCrudGraphqlRestResource<String, ProjectUser, D, DTO, C, QueryBuilder, R, S> {

    @Autowired
    ProjectUserService projectUserService;
    @Autowired
    S service;
    @Autowired
    ObjectMapper objectMapper;

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
