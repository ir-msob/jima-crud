package ir.msob.jima.crud.ral.jpa.it.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.id.BaseIdService;
import ir.msob.jima.core.it.criteria.ProjectCriteria;
import ir.msob.jima.core.it.domain.ProjectDomain;
import ir.msob.jima.core.it.dto.ProjectDto;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.core.it.security.Roles;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;

import java.util.Collections;
import java.util.TreeSet;

public abstract class DomainCrudDataProvider<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends SqlDomainCrudRepository<D>,
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
