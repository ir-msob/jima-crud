package ir.msob.jima.crud.kafka.reactive.test.resource.domain;


import com.fasterxml.jackson.core.type.TypeReference;
import ir.msob.jima.crud.kafka.reactive.test.TestApplication;
import ir.msob.jima.crud.kafka.reactive.test.resource.domain.base.DomainCrudKafkaReactiveResourceTest;
import ir.msob.jima.crud.reactive.testing.test.TestDataProvider;
import ir.msob.jima.crud.reactive.testing.test.TestDomainService;
import ir.msob.jima.crud.reactive.testing.test.TestRepository;
import ir.msob.jima.platform.api.properties.TestProperties;
import ir.msob.jima.platform.api.resource.BaseResource;
import ir.msob.jima.platform.api.shared.PageDto;
import ir.msob.jima.platform.kafka.testing.configuration.KafkaContainerConfiguration;
import ir.msob.jima.platform.mongo.testing.configuration.MongoContainerConfiguration;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import ir.msob.jima.platform.test.data.CoreTestData;
import ir.msob.jima.platform.testing.security.ProjectUser;
import ir.msob.jima.security.keycloak.testing.configuration.KeycloakContainerConfiguration;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Type;
import java.util.Collection;


@SpringBootTest(classes = {TestApplication.class, KafkaContainerConfiguration.class, MongoContainerConfiguration.class, KeycloakContainerConfiguration.class})
@ContextConfiguration
@Testcontainers
@CommonsLog
class TestDomainKafkaReactiveResourceIT extends DomainCrudKafkaReactiveResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestDomainService, TestDataProvider> {

    @Autowired
    TestDomainKafkaResourceDomain testDomainRsocketResource;
    @Getter
    @Autowired
    TestProperties testProperties;

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
    }


    @Override
    public Class<? extends BaseResource<String, ProjectUser>> getResourceClass() {
        return TestDomainKafkaResourceDomain.class;
    }

    @Override
    public String getBaseUri() {
        return TestDomainKafkaResourceDomain.BASE_URI;
    }


    @Override
    public TypeReference<PageDto<TestDto>> getPageDtoReferenceType() {
        return new TypeReference<PageDto<TestDto>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
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

    @Override
    public TypeReference<Collection<TestDto>> getDtosReferenceType() {
        return new TypeReference<Collection<TestDto>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }
}
