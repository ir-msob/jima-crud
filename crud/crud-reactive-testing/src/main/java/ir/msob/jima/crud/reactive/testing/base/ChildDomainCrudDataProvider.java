package ir.msob.jima.crud.reactive.testing.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.reactive.test.dataprovider.childdomain.BaseChildDomainCrudDataProvider;
import ir.msob.jima.platform.api.id.BaseIdService;
import ir.msob.jima.platform.testing.childcriteria.ProjectChildCriteria;
import ir.msob.jima.platform.testing.childdomain.ProjectChildDomain;
import ir.msob.jima.platform.testing.childdto.ProjectChildDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import ir.msob.jima.platform.testing.security.Roles;

import java.util.Collections;
import java.util.TreeSet;

public abstract class ChildDomainCrudDataProvider<
        D extends ProjectChildDomain,
        DTO extends ProjectChildDto,
        C extends ProjectChildCriteria,
        R extends MongoChildDomainCrudRepository<D, C>,
        S extends ChildDomainCrudService<D, DTO, C, R>>
        implements BaseChildDomainCrudDataProvider<String, ProjectUser, D, DTO, C, R, S> {

    private final ProjectUser sampleUser;
    private final ObjectMapper objectMapper;
    private final S service;

    protected ChildDomainCrudDataProvider(BaseIdService idService, ObjectMapper objectMapper, S service) {
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
