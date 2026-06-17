package ir.msob.jima.crud.rsocket.reactive.test.resource.domain;

import io.rsocket.transport.netty.client.TcpClientTransport;
import ir.msob.jima.crud.reactive.testing.test.TestDataProvider;
import ir.msob.jima.crud.reactive.testing.test.TestDomainService;
import ir.msob.jima.crud.reactive.testing.test.TestRepository;
import ir.msob.jima.crud.rsocket.reactive.test.TestApplication;
import ir.msob.jima.crud.rsocket.reactive.test.resource.domain.base.DomainCrudRsocketResourceTest;
import ir.msob.jima.platform.api.resource.BaseResource;
import ir.msob.jima.platform.mongo.testing.configuration.MongoContainerConfiguration;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import ir.msob.jima.platform.mongo.testing.test.TestDtoTypeReference;
import ir.msob.jima.platform.rsocket.api.BaseRSocketRequesterBuilder;
import ir.msob.jima.platform.test.data.CoreTestData;
import ir.msob.jima.platform.testing.security.ProjectUser;
import ir.msob.jima.security.keycloak.testing.configuration.KeycloakContainerConfiguration;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(classes = {TestApplication.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Testcontainers
@CommonsLog
class TestDomainRsocketResourceIT extends DomainCrudRsocketResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestDomainService, TestDataProvider>
        implements TestDtoTypeReference {

    @Autowired
    BaseRSocketRequesterBuilder rSocketOauth2RequesterBuilder;

    RSocketRequester requester;

    @Value("${spring.rsocket.server.port}")
    Integer port;
    String host = "localhost";

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
        requester = rSocketOauth2RequesterBuilder.getBuilder()
                .transport(TcpClientTransport.create(host, port));
    }

    @Override
    public RSocketRequester getRSocketRequester() {
        return requester;
    }

    @Override
    public Class<? extends BaseResource<String, ProjectUser>> getResourceClass() {
        return TestDomainRsocketResource.class;
    }

    @Override
    public String getBaseUri() {
        return TestDomainRsocketResource.BASE_URI;
    }


}
