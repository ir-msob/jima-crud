package ir.msob.jima.crud.api.restful.service.childdomain.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.security.BaseTokenService;
import ir.msob.jima.core.it.childcriteria.ProjectChildCriteria;
import ir.msob.jima.core.it.childdomain.ProjectChildDomain;
import ir.msob.jima.core.it.childdto.ProjectChildDto;
import ir.msob.jima.core.it.criteria.ProjectCriteria;
import ir.msob.jima.core.it.domain.ProjectDomain;
import ir.msob.jima.core.it.dto.ProjectDto;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.crud.api.restful.service.domain.base.ProjectUserService;
import ir.msob.jima.crud.api.restful.test.childdomain.BaseChildDomainCrudRestResourceTest;
import ir.msob.jima.crud.api.restful.test.domain.BaseDomainCrudRestResourceTest;
import ir.msob.jima.crud.ral.mongo.it.base.*;
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
