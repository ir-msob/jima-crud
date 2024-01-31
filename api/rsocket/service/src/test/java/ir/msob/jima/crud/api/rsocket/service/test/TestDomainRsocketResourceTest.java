package ir.msob.jima.crud.api.rsocket.service.test;


import io.rsocket.transport.netty.client.TcpClientTransport;
import ir.msob.jima.core.api.rsocket.commons.BaseRSocketRequesterBuilder;
import ir.msob.jima.core.beans.configuration.JimaConfigProperties;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.api.rsocket.service.base.CrudRsocketResourceTest;
import ir.msob.jima.crud.ral.mongo.it.test.TestDomainDataProvider;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestService;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(classes = TestMicroserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Testcontainers
@ExtendWith({MongoContainerConfiguration.class})
@CommonsLog
public class TestDomainRsocketResourceTest extends CrudRsocketResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestService, TestDomainDataProvider> {

    @Autowired
    TestDomainRsocketResource testDomainRsocketResource;

    @Autowired
    BaseRSocketRequesterBuilder rSocketOauth2RequesterBuilder;

    RSocketRequester requester;

    @Value("${spring.rsocket.server.port}")
    Integer port;
    String host = "localhost";
    @Autowired
    JimaConfigProperties jimaConfigProperties;

    @DynamicPropertySource
    static void registry(DynamicPropertyRegistry registry) {
        MongoContainerConfiguration.registry(registry);
    }

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
        requester = rSocketOauth2RequesterBuilder.getBuilder()
                .transport(TcpClientTransport.create(host, port));
    }

    @Override
    public RSocketRequester getRSocketRequester() {
        return requester;
    }

    @Override
    public Class<? extends BaseResource<ObjectId, ProjectUser>> getResourceClass() {
        return TestDomainRsocketResource.class;
    }

    @Override
    public String getBaseUri() {
        return TestDomainRsocketResource.BASE_URI;
    }

}
