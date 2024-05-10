package ir.msob.jima.crud.sample.restful.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.security.BaseTokenService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.api.restful.test.BaseCrudRestResourceTest;
import ir.msob.jima.crud.sample.restful.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.restful.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.restful.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.restful.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.restful.base.security.ProjectUser;
import ir.msob.jima.crud.sample.restful.base.security.ProjectUserService;
import ir.msob.jima.crud.sample.restful.base.service.CrudService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;


public abstract class CrudRestResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>,
        DP extends CrudDataProvider<D, DTO, C, R, S>>
        implements BaseCrudRestResourceTest<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S, DP> {

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
    public Optional<ProjectUser> getSampleUser() {
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
}
