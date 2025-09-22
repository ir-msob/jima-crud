package ir.msob.jima.crud.api.restful.service.domain;


import ir.msob.jima.core.commons.resource.Resource;
import ir.msob.jima.core.commons.shared.ResourceType;
import ir.msob.jima.core.ral.mongo.it.Microservices;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.restful.service.domain.base.DomainCrudRestResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestServiceDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(TestDomainRestResource.BASE_URI)
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = ResourceType.RESTFUL)
public class TestDomainRestResource extends DomainCrudRestResource<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain> {
    public static final String BASE_URI = "/api/" + Microservices.VERSION + "/" + TestDomain.DOMAIN_URI;
}
