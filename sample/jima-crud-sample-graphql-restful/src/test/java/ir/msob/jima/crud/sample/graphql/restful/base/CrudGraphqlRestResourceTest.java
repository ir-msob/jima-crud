package ir.msob.jima.crud.sample.graphql.restful.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.service.BaseIdService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.api.graphql.restful.test.BaseCrudGraphqlRestResourceTest;
import ir.msob.jima.crud.sample.graphql.restful.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.graphql.restful.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.graphql.restful.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.graphql.restful.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.graphql.restful.base.security.ProjectUser;
import ir.msob.jima.crud.sample.graphql.restful.base.service.CrudService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.Optional;


public abstract class CrudGraphqlRestResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>,
        DP extends CrudDataProvider<D, DTO, C, R, S>>
        implements BaseCrudGraphqlRestResourceTest<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S, DP> {

    @Autowired
    GraphQlTester graphQlTester;
    @Autowired
    DP dataProvider;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    BaseIdService idService;

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
    public BaseIdService getIdService() {
        return idService;
    }

    @Override
    public GraphQlTester getGraphQlTester() {
        return graphQlTester;
    }
}
