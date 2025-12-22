package ir.msob.jima.crud.api.restful.service.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.commons.shared.PageDto;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.api.restful.service.TestApplication;
import ir.msob.jima.crud.api.restful.service.domain.base.DomainCrudRestResourceTest;
import ir.msob.jima.crud.ral.mongo.it.test.TestDataProvider;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestServiceDomain;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Type;

@AutoConfigureWebTestClient
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@CommonsLog
class TestDomainRestResourceIT extends DomainCrudRestResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain, TestDataProvider> {
    @Autowired
    JimaProperties jimaProperties;

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
    public String getBaseUri() {
        return TestDomainRestResource.BASE_URI;
    }

    @Override
    public Class<? extends BaseResource<String, ProjectUser>> getResourceClass() {
        return TestDomainRestResource.class;
    }

    @Override
    public JimaProperties getJimaProperties() {
        return jimaProperties;
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
