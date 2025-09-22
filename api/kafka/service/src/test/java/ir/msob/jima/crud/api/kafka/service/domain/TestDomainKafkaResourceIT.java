package ir.msob.jima.crud.api.kafka.service.domain;


import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.ral.kafka.test.KafkaContainerConfiguration;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.api.kafka.service.TestMicroserviceApplication;
import ir.msob.jima.crud.api.kafka.service.domain.base.DomainCrudKafkaResourceTest;
import ir.msob.jima.crud.ral.mongo.it.test.TestDataProvider;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestServiceDomain;
import ir.msob.jima.security.ral.keycloak.test.KeycloakContainerConfiguration;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(classes = {TestMicroserviceApplication.class, KafkaContainerConfiguration.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class})
@ContextConfiguration
@Testcontainers
@CommonsLog
public class TestDomainKafkaResourceIT extends DomainCrudKafkaResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain, TestDataProvider> {

    @Autowired
    TestDomainKafkaResourceDomain testDomainRsocketResource;
    @Autowired
    JimaProperties jimaProperties;

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        CoreTestData.init(new ObjectId(), new ObjectId());

    }

    @SneakyThrows
    @BeforeEach
    public void beforeEach() {
        getDataProvider().cleanups();
        TestDataProvider.createNewDto();
        TestDataProvider.createMandatoryNewDto();
    }


    @Override
    public Class<? extends BaseResource<ObjectId, ProjectUser>> getResourceClass() {
        return TestDomainKafkaResourceDomain.class;
    }

    @Override
    public String getBaseUri() {
        return TestDomainKafkaResourceDomain.BASE_URI;
    }

    @Override
    public JimaProperties getJimaProperties() {
        return jimaProperties;
    }
}
