package ir.msob.jima.crud.api.restful.service.childdomain;


import ir.msob.jima.core.commons.resource.Resource;
import ir.msob.jima.core.commons.shared.ResourceType;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildCriteria;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildDomain;
import ir.msob.jima.core.ral.mongo.it.testchild.TestChildDto;
import ir.msob.jima.crud.api.restful.service.childdomain.base.ChildDomainCrudRestResource;
import ir.msob.jima.crud.api.restful.service.domain.TestDomainRestResource;
import ir.msob.jima.crud.ral.mongo.it.testchild.TestChildRepository;
import ir.msob.jima.crud.ral.mongo.it.testchild.TestChildServiceDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(TestChildDomainRestResource.BASE_URI)
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = ResourceType.RESTFUL)
public class TestChildDomainRestResource extends ChildDomainCrudRestResource<TestChildDomain, TestChildDto, TestChildCriteria, TestChildRepository, TestChildServiceDomain> {
    public static final String BASE_URI = TestDomainRestResource.BASE_URI + "/{parentDomainId}/test-child";

}
