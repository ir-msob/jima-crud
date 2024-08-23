package ir.msob.jima.crud.sample.restful.domain;

import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.sample.restful.JimaApplication;
import ir.msob.jima.crud.sample.restful.base.CrudRestResourceTest;
import ir.msob.jima.crud.sample.restful.base.security.ProjectUser;
import ir.msob.jima.security.ral.keycloak.test.KeycloakContainerConfiguration;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.assertj.core.api.Assertions;
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
public class SampleDomainRestResourceIT extends CrudRestResourceTest<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService, SampleDomainDataProvider> {

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        CoreTestData.init(new ObjectId(), new ObjectId());
    }

    @SneakyThrows
    @BeforeEach
    public void beforeEach() {
        getDataProvider().cleanups();
        SampleDomainDataProvider.createMandatoryNewDto();
        SampleDomainDataProvider.createNewDto();
    }


    @Override
    public String getBaseUri() {
        return SampleDomainRestResource.BASE_URI;
    }

    @Override
    public Class<? extends BaseResource<ObjectId, ProjectUser>> getResourceClass() {
        return SampleDomainRestResource.class;
    }

    @Override
    public void assertMandatory(SampleDto before, SampleDto after) {
        Assertions.assertThat(after.getDomainMandatoryField()).isEqualTo(before.getDomainMandatoryField());
    }

    @Override
    public void assertAll(SampleDto before, SampleDto after) {
        assertMandatory(before, after);
        Assertions.assertThat(after.getDomainField()).isEqualTo(before.getDomainField());
    }
}
