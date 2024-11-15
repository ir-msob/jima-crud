package ir.msob.jima.crud.sample.restful.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.sample.restful.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.restful.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.restful.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.restful.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.restful.base.security.ProjectUser;
import ir.msob.jima.crud.sample.restful.base.security.ProjectUserService;
import ir.msob.jima.crud.sample.restful.base.service.CrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class CrudDataProvider<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>>
        implements BaseCrudDataProvider<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S> {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    S service;

    @Override
    public ProjectUser getSampleUser() {
        return ProjectUserService.SYSTEM_USER_OPTIONAL;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public S getService() {
        return service;
    }
}
