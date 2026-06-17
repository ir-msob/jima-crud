package ir.msob.jima.crud.grpc.reactive.test.resource.domain;

import ir.msob.jima.crud.grpc.reactive.test.TestApplication;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.base.DomainCrudGrpcResourceTest;
import ir.msob.jima.crud.reactive.testing.test.TestDataProvider;
import ir.msob.jima.crud.reactive.testing.test.TestDomainService;
import ir.msob.jima.crud.reactive.testing.test.TestRepository;
import ir.msob.jima.platform.mongo.testing.configuration.MongoContainerConfiguration;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import ir.msob.jima.platform.mongo.testing.test.TestDtoTypeReference;
import ir.msob.jima.platform.test.data.CoreTestData;
import ir.msob.jima.security.keycloak.testing.configuration.KeycloakContainerConfiguration;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringJUnitConfig(classes = {TestApplication.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class})
@Testcontainers
@CommonsLog
@EnableConfigurationProperties
@SpringBootTest
class TestDomainGrpcResourceIT extends DomainCrudGrpcResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestDomainService, TestDataProvider>
        implements TestDtoTypeReference {

    @Autowired
    TestDomainGrpcResourceDomain resource;


    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        CoreTestData.init(new ObjectId().toString(), new ObjectId().toString());
    }


    @SneakyThrows
    @BeforeEach
    void beforeEach() {
        getDataProvider().cleanups();
        TestDataProvider.createNewDto();
        TestDataProvider.createMandatoryNewDto();
        registerStub();
    }

    @AfterEach
    void afterEach() {
        channelShutdownNow();
    }

    @Override
    public Class<TestDomainGrpcResourceDomain> getResourceClass() {
        return TestDomainGrpcResourceDomain.class;
    }

    @Override
    protected TestDomainGrpcResourceDomain getResource() {
        return resource;
    }
}
