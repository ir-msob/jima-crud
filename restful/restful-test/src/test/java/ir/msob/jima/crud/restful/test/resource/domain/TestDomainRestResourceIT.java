package ir.msob.jima.crud.restful.test.resource.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import ir.msob.jima.crud.restful.test.TestApplication;
import ir.msob.jima.crud.restful.test.resource.domain.base.DomainCrudRestResourceTest;
import ir.msob.jima.crud.testing.test.TestDataProvider;
import ir.msob.jima.crud.testing.test.TestDomainService;
import ir.msob.jima.crud.testing.test.TestRepository;
import ir.msob.jima.platform.api.resource.BaseResource;
import ir.msob.jima.platform.api.shared.PageDto;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import ir.msob.jima.platform.test.data.CoreTestData;
import ir.msob.jima.platform.testing.security.ProjectUser;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Type;
import java.util.Collection;

@AutoConfigureTestRestTemplate
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@CommonsLog
class TestDomainRestResourceIT extends DomainCrudRestResourceTest<TestDomain, TestDto, TestCriteria, TestRepository, TestDomainService, TestDataProvider> {


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
