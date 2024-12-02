package ir.msob.jima.crud.ral.mongo.it.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.id.BaseIdService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.core.ral.mongo.it.criteria.ProjectCriteria;
import ir.msob.jima.core.ral.mongo.it.domain.ProjectDomain;
import ir.msob.jima.core.ral.mongo.it.dto.ProjectDto;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.security.Roles;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import org.bson.types.ObjectId;

import java.util.Collections;
import java.util.TreeSet;

public abstract class CrudDataProvider<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>>
        implements BaseCrudDataProvider<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S> {

    public final ProjectUser SAMPLE_USER;
    private final ObjectMapper objectMapper;
    private final S service;

    public CrudDataProvider(BaseIdService idService, ObjectMapper objectMapper, S service) {
        this.objectMapper = objectMapper;
        this.service = service;
        this.SAMPLE_USER = ProjectUser.builder()
                .id(idService.newId().toString())
                .sessionId(idService.newId().toString())
                .username("user")
                .audience("web")
                .roles(new TreeSet<>(Collections.singleton(Roles.USER)))
                .build();
    }

    @Override
    public ProjectUser getSampleUser() {
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
