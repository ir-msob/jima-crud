package ir.msob.jima.crud.testing.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.test.dataprovider.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.platform.api.id.BaseIdService;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import ir.msob.jima.platform.testing.dto.ProjectDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import ir.msob.jima.platform.testing.security.Roles;

import java.util.Collections;
import java.util.TreeSet;

public abstract class DomainCrudDataProvider<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudRepository<D, C>,
        S extends DomainCrudService<D, DTO, C, R>>
        implements BaseDomainCrudDataProvider<String, ProjectUser, D, DTO, C, R, S> {

    private final ProjectUser sampleUser;
    private final ObjectMapper objectMapper;
    private final S service;

    protected DomainCrudDataProvider(BaseIdService idService, ObjectMapper objectMapper, S service) {
        this.objectMapper = objectMapper;
        this.service = service;
        this.sampleUser = ProjectUser.builder()
                .id(idService.newId().toString())
                .sessionId(idService.newId().toString())
                .username("user")
                .audience("web")
                .roles(new TreeSet<>(Collections.singleton(Roles.USER)))
                .build();
    }

    @Override
    public ProjectUser getSampleUser() {
        return sampleUser;
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
