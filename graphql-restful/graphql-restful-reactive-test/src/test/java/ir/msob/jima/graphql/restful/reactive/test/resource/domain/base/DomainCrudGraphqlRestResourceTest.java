package ir.msob.jima.graphql.restful.reactive.test.resource.domain.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudDataProvider;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudService;
import ir.msob.jima.crud.reactive.testing.base.MongoDomainCrudReactiveRepository;
import ir.msob.jima.graphql.restful.reactive.test.domain.BaseDomainCrudGraphqlRestResourceTest;
import ir.msob.jima.platform.api.id.BaseIdService;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import ir.msob.jima.platform.testing.dto.ProjectDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.lang.reflect.Type;
import java.util.Collection;


public abstract class DomainCrudGraphqlRestResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudReactiveRepository<D, C>,
        S extends DomainCrudService<D, DTO, C, R>,
        DP extends DomainCrudDataProvider<D, DTO, C, R, S>>
        implements BaseDomainCrudGraphqlRestResourceTest<String, ProjectUser, D, DTO, C, R, S, DP> {

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
