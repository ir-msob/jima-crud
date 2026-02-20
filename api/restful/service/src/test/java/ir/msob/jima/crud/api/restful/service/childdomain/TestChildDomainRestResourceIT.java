package ir.msob.jima.crud.api.restful.service.childdomain;

import com.fasterxml.jackson.core.type.TypeReference;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.resource.BaseResource;
import ir.msob.jima.core.commons.shared.PageDto;
import ir.msob.jima.core.it.security.ProjectUser;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildCriteria;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildDomain;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildDto;
import ir.msob.jima.core.test.CoreTestData;
import ir.msob.jima.crud.api.restful.service.TestApplication;
import ir.msob.jima.crud.api.restful.service.childdomain.base.ChildDomainCrudRestResourceTest;
import ir.msob.jima.crud.api.restful.service.domain.TestDomainRestResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestDataProvider;
import ir.msob.jima.crud.ral.mongo.it.testchild.TestChildDataProvider;
import ir.msob.jima.crud.ral.mongo.it.testchild.TestChildRepository;
import ir.msob.jima.crud.ral.mongo.it.testchild.TestChildServiceDomain;
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
class TestChildDomainRestResourceIT extends ChildDomainCrudRestResourceTest<TestChildDomain, TestChildDto, TestChildCriteria, TestChildRepository, TestChildServiceDomain, TestChildDataProvider> {
    @Autowired
    JimaProperties jimaProperties;

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
        return TestDomainRestResource.BASE_URI;
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
    public JimaProperties getJimaProperties() {
        return jimaProperties;
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
}
