package ir.msob.jima.crud.restful.reactive.test.resource.domain.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudDataProvider;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudService;
import ir.msob.jima.crud.reactive.testing.base.MongoDomainCrudReactiveRepository;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.BaseDomainCrudRestReactiveResourceTest;
import ir.msob.jima.platform.api.security.BaseTokenService;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import ir.msob.jima.platform.testing.dto.ProjectDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.lang.reflect.Type;
import java.util.Collection;


public abstract class DomainCrudRestReactiveResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudReactiveRepository<D, C>,
        S extends DomainCrudService<D, DTO, C, R>,
        DP extends DomainCrudDataProvider<D, DTO, C, R, S>>
        implements BaseDomainCrudRestReactiveResourceTest<String, ProjectUser, D, DTO, C, R, S, DP> {

    @Autowired
    public WebTestClient webTestClient;

    @Autowired
    DP dataProvider;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProjectUserService projectUserService;
    @Autowired
    BaseTokenService tokenService;

    @Override
    public WebTestClient getWebTestClient() {
        return webTestClient;
    }

    @Override
    public ProjectUser getSampleUser() {
        return getDataProvider().getSampleUser();
    }

    @Override
    public DP getDataProvider() {
        return dataProvider;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public BaseTokenService getTokenService() {
        return tokenService;
    }

    @Override
    public TypeReference<Collection<String>> getIdsReferenceType() {
        return new TypeReference<Collection<String>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

}
