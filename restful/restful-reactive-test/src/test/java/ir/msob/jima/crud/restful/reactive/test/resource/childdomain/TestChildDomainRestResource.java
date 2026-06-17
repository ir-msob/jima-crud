package ir.msob.jima.crud.restful.reactive.test.resource.childdomain;

import ir.msob.jima.crud.reactive.testing.testchild.TestChildRepository;
import ir.msob.jima.crud.reactive.testing.testchild.TestChildServiceDomain;
import ir.msob.jima.crud.restful.reactive.test.resource.childdomain.base.ChildDomainCrudRestResource;
import ir.msob.jima.crud.restful.reactive.test.resource.domain.TestDomainRestReactiveResource;
import ir.msob.jima.platform.api.resource.Resource;
import ir.msob.jima.platform.api.shared.ResourceType;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildCriteria;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildDomain;
import ir.msob.jima.platform.mongo.testing.testchild.TestChildDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(TestChildDomainRestResource.BASE_URI)
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = ResourceType.RESTFUL)
public class TestChildDomainRestResource extends ChildDomainCrudRestResource<TestChildDomain, TestChildDto, TestChildCriteria, TestChildRepository, TestChildServiceDomain> {
    public static final String BASE_URI = TestDomainRestReactiveResource.BASE_URI + "/{parentDomainId}/test-child";

}
