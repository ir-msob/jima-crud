package ir.msob.jima.crud.api.grpc.service.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import ir.msob.jima.core.commons.shared.PageResponse;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.core.ral.mongo.test.configuration.MongoContainerConfiguration;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.api.grpc.service.TestApplication;
import ir.msob.jima.crud.api.grpc.service.domain.base.DomainCrudGrpcResourceTest;
import ir.msob.jima.crud.ral.mongo.it.test.TestDataProvider;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestServiceDomain;
import ir.msob.jima.security.ral.keycloak.test.KeycloakContainerConfiguration;
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

import java.lang.reflect.Type;

@SpringJUnitConfig(classes = {TestApplication.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class})
@Testcontainers
@CommonsLog
@EnableConfigurationProperties
@SpringBootTest
class TestDomainGrpcResourceIT extends DomainCrudGrpcResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain, TestDataProvider> {


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
        getDataProvider().createNewDto();
        getDataProvider().createMandatoryNewDto();
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
