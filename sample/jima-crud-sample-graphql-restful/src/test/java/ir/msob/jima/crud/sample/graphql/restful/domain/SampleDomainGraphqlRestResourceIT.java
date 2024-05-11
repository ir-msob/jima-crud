package ir.msob.jima.crud.sample.graphql.restful.domain;

import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.sample.graphql.restful.JimaApplication;
import ir.msob.jima.crud.sample.graphql.restful.base.CrudGraphqlRestResourceTest;
import ir.msob.jima.crud.sample.graphql.restful.base.security.ProjectUser;
import ir.msob.jima.security.ral.keycloak.test.KeycloakContainerConfiguration;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureWebTestClient
@SpringBootTest(classes = {JimaApplication.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class}
        , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Testcontainers
@CommonsLog
public class SampleDomainGraphqlRestResourceIT extends CrudGraphqlRestResourceTest<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService, SampleDomainDataProvider> {

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        CoreTestData.init(new ObjectId(), new ObjectId());
    }

    @SneakyThrows
    @BeforeEach
    public void beforeEach() {
        getDataProvider().cleanups();
        getDataProvider().createNewDto();
        getDataProvider().createMandatoryNewDto();
    }


    @Override
    public Class<? extends BaseResource<ObjectId, ProjectUser>> getResourceClass() {
        return SampleDomainGraphqlRestResource.class;
    }

}
