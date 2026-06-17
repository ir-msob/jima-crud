package ir.msob.jima.crud.restful.reactive.test.resource.childdomain;

import com.fasterxml.jackson.core.type.TypeReference;
import ir.msob.jima.crud.reactive.testing.test.TestDataProvider;
import ir.msob.jima.crud.reactive.testing.testchild.TestChildDataProvider;
import ir.msob.jima.crud.reactive.testing.testchild.TestChildRepository;
import ir.msob.jima.crud.reactive.testing.testchild.TestChildServiceDomain;
import ir.msob.jima.crud.restful.reactive.test.TestApplication;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.base.ChildDomainCrudRestResourceTest;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.TestDomainRestReactiveResource;
import ir.msob.jima.platform.api.resource.BaseResource;
import ir.msob.jima.platform.api.shared.PageDto;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildCriteria;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildDomain;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildDto;
import ir.msob.jima.platform.test.data.CoreTestData;
import ir.msob.jima.platform.testing.security.ProjectUser;
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
import java.util.Collection;

@AutoConfigureWebTestClient
@SpringBootTest(classes = {TestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@CommonsLog
class TestChildDomainRestResourceIT extends ChildDomainCrudRestResourceTest<TestChildDomain, TestChildDto, TestChildCriteria, TestChildRepository, TestChildServiceDomain, TestChildDataProvider> {

    @Autowired
    TestDataProvider testDataProvider;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        CoreTestData.init(new ObjectId().toString(), new ObjectId().toString());
    }


    @SneakyThrows
    @BeforeEach
    void beforeEach() {
        testDataProvider.cleanups();
        TestDataProvider.createNewDto();
        TestDataProvider.createMandatoryNewDto();

        getDataProvider().cleanups();
        TestChildDataProvider.createNewDto();
        TestChildDataProvider.createMandatoryNewDto();

        TestDomain testDomain = testDataProvider.saveNew();
        getDataProvider().getNewDto().setParentId(testDomain.getId());
        getDataProvider().getMandatoryNewDto().setParentId(testDomain.getId());
    }


    @Override
    public String getDomainUri() {
        return TestDomainRestReactiveResource.BASE_URI;
    }

    @Override
    public String getChildDomainUri() {
        return "test-child";
    }

    @Override
    public Class<? extends BaseResource<String, ProjectUser>> getResourceClass() {
        return TestChildDomainRestResource.class;
    }

    @Override
    public TypeReference<PageDto<TestChildDto>> getPageDtoReferenceType() {
        return new TypeReference<PageDto<TestChildDto>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<TestChildCriteria> getCriteriaReferenceType() {
        return new TypeReference<TestChildCriteria>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<TestChildDto> getDtoReferenceType() {
        return new TypeReference<TestChildDto>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<Collection<TestChildDto>> getDtosReferenceType() {
        return new TypeReference<Collection<TestChildDto>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }
}
