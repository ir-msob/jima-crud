package ir.msob.jima.crud.restful.reactive.test.resource.childdomain.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.reactive.testing.base.ChildDomainCrudDataProvider;
import ir.msob.jima.crud.reactive.testing.base.ChildDomainCrudService;
import ir.msob.jima.crud.reactive.testing.base.MongoChildDomainCrudRepository;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.BaseChildDomainCrudRestResourceTest;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.base.ProjectUserService;
import ir.msob.jima.platform.api.security.BaseTokenService;
import ir.msob.jima.platform.testing.childcriteria.ProjectChildCriteria;
import ir.msob.jima.platform.testing.childdomain.ProjectChildDomain;
import ir.msob.jima.platform.testing.childdto.ProjectChildDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.lang.reflect.Type;
import java.util.Collection;


public abstract class ChildDomainCrudRestResourceTest<
        D extends ProjectChildDomain,
        DTO extends ProjectChildDto,
        C extends ProjectChildCriteria,
        R extends MongoChildDomainCrudRepository<D, C>,
        S extends ChildDomainCrudService<D, DTO, C, R>,
        DP extends ChildDomainCrudDataProvider<D, DTO, C, R, S>>
        implements BaseChildDomainCrudRestResourceTest<String, ProjectUser, D, DTO, C, R, S, DP> {

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
