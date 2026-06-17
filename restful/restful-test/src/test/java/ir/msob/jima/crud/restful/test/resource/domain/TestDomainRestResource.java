package ir.msob.jima.crud.restful.test.resource.domain;

import ir.msob.jima.crud.restful.test.resource.domain.base.DomainCrudRestResource;
import ir.msob.jima.crud.testing.test.TestDomainService;
import ir.msob.jima.crud.testing.test.TestRepository;
import ir.msob.jima.platform.api.resource.Resource;
import ir.msob.jima.platform.api.shared.ResourceType;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import ir.msob.jima.platform.testing.Microservices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestDomainRestResource.BASE_URI)
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = ResourceType.RESTFUL)
public class TestDomainRestResource extends DomainCrudRestResource<TestDomain, TestDto, TestCriteria, TestRepository, TestDomainService> {
    public static final String BASE_URI = "/api/" + Microservices.VERSION + "/" + TestDomain.DOMAIN_URI;
}
