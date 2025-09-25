package ir.msob.jima.crud.api.restful.service.domain;

import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.commons.shared.PageResponse;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
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
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Type;
import java.util.Collection;

@AutoConfigureWebTestClient
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@CommonsLog
public class TestDomainRestResourceIT extends DomainCrudRestResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain, TestDataProvider> {
    @Autowired
    JimaProperties jimaProperties;

    @SneakyThrows
    @BeforeAll
    public static void beforeAll() {
        CoreTestData.init(new ObjectId().toString(), new ObjectId().toString());
    }


    @SneakyThrows
    @BeforeEach
    public void beforeEach() {
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
    public ParameterizedTypeReference<Collection<TestDto>> getDtosTypeReferenceType() {
        return new ParameterizedTypeReference<Collection<TestDto>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public ParameterizedTypeReference<TestDto> getDtoReferenceType() {
        return new ParameterizedTypeReference<TestDto>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public ParameterizedTypeReference<TestCriteria> getCriteriaReferenceType() {
        return new ParameterizedTypeReference<TestCriteria>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public ParameterizedTypeReference<PageResponse<TestDto>> getPageReferenceType() {
        return new ParameterizedTypeReference<PageResponse<TestDto>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }


}
