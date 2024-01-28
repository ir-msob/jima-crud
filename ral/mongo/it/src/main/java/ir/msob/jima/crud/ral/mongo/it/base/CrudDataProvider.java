package ir.msob.jima.crud.ral.mongo.it.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.core.ral.mongo.it.criteria.ProjectCriteria;
import ir.msob.jima.core.ral.mongo.it.domain.ProjectDomain;
import ir.msob.jima.core.ral.mongo.it.dto.ProjectDto;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.security.Roles;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;
import java.util.TreeSet;

public abstract class CrudDataProvider<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>>
        implements BaseCrudDataProvider<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S> {

    public static final Optional<ProjectUser> SAMPLE_USER = Optional.of(
            ProjectUser.builder()
                    .id(new ObjectId())
                    .sessionId(new ObjectId())
                    .username("user")
                    .audience("web")
                    .roles(new TreeSet<>(Collections.singleton(Roles.USER)))
                    .build());
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    S service;

    @Override
    public Optional<ProjectUser> getSampleUser() {
        return SAMPLE_USER;
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
