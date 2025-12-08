package ir.msob.jima.crud.api.rsocket.service.domain;


import com.fasterxml.jackson.core.type.TypeReference;
import io.rsocket.transport.netty.client.TcpClientTransport;
import ir.msob.jima.core.api.rsocket.commons.BaseRSocketRequesterBuilder;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.commons.shared.PageResponse;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.api.rsocket.service.TestApplication;
import ir.msob.jima.crud.api.rsocket.service.domain.base.DomainCrudRsocketResourceTest;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Type;


@SpringBootTest(classes = {TestApplication.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Testcontainers
@CommonsLog
class TestDomainRsocketResourceIT extends DomainCrudRsocketResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain, TestDataProvider> {

    @Autowired
    TestDomainRsocketResource testDomainRsocketResource;
    @Autowired
    JimaProperties jimaProperties;
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
    public Class<? extends BaseResource<String, ProjectUser>> getResourceClass() {
        return TestDomainRsocketResource.class;
    }

    @Override
    public String getBaseUri() {
        return TestDomainRsocketResource.BASE_URI;
    }

    @Override
    public JimaProperties getJimaProperties() {
        return jimaProperties;
    }

    @Override
    public TypeReference<PageResponse<TestDto>> getPageResponseReferenceType() {
        return new TypeReference<PageResponse<TestDto>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<TestCriteria> getCriteriaReferenceType() {
        return new TypeReference<TestCriteria>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<TestDto> getDtoReferenceType() {
        return new TypeReference<TestDto>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }
}
