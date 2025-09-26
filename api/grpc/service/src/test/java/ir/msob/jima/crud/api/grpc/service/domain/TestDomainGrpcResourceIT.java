package ir.msob.jima.crud.api.grpc.service.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.shared.PageResponse;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.api.grpc.commons.ReactorCrudServiceGrpc;
import ir.msob.jima.crud.api.grpc.service.TestApplication;
import ir.msob.jima.crud.api.grpc.service.domain.base.DomainCrudGrpcResourceTest;
import ir.msob.jima.crud.ral.mongo.it.test.TestDataProvider;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestServiceDomain;
import ir.msob.jima.security.ral.keycloak.test.KeycloakContainerConfiguration;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.lang.reflect.Type;

@SpringJUnitConfig(classes = {TestApplication.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class})
@Testcontainers
@CommonsLog
@EnableConfigurationProperties
@SpringBootTest
@Disabled // FIXME
public class TestDomainGrpcResourceIT extends DomainCrudGrpcResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain, TestDataProvider> {

    @Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();
    @Autowired
    TestServiceDomain service;
    @Autowired
    TestDomainGrpcResourceDomain resource;
    @Autowired
    JimaProperties jimaProperties;
    private ReactorCrudServiceGrpc.ReactorCrudServiceStub reactorCrudServiceStub;

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        CoreTestData.init(new ObjectId().toString(), new ObjectId().toString());
    }

    private void registerService() throws IOException {
        String serverName = InProcessServerBuilder.generateName();
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(resource)
                .build()
                .start());
        reactorCrudServiceStub = ReactorCrudServiceGrpc
                .newReactorStub(grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));
    }

    @SneakyThrows
    @BeforeEach
    public void beforeEach() {
        getDataProvider().cleanups();
        getDataProvider().createNewDto();
        getDataProvider().createMandatoryNewDto();
        registerService();
    }

    @Override
    public Class<TestDomainGrpcResourceDomain> getResourceClass() {
        return TestDomainGrpcResourceDomain.class;
    }

    @Override
    public ReactorCrudServiceGrpc.ReactorCrudServiceStub getReactorCrudServiceStub() {
        return reactorCrudServiceStub;
    }

    @Override
    public JimaProperties getJimaProperties() {
        return jimaProperties;
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
    public TypeReference<PageResponse<TestDto>> getPageResponseReferenceType() {
        return new TypeReference<PageResponse<TestDto>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }
}
