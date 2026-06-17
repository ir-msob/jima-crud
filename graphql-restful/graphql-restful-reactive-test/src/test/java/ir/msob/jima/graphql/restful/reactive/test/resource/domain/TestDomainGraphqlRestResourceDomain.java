package ir.msob.jima.graphql.restful.reactive.test.resource.domain;


import ir.msob.jima.crud.reactive.testing.test.TestDomainService;
import ir.msob.jima.crud.reactive.testing.test.TestRepository;
import ir.msob.jima.graphql.restful.reactive.test.resource.domain.base.DomainCrudGraphqlRestResource;
import ir.msob.jima.platform.api.resource.Resource;
import ir.msob.jima.platform.api.shared.ResourceType;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = ResourceType.RESTFUL)
public class TestDomainGraphqlRestResourceDomain extends DomainCrudGraphqlRestResource<TestDomain, TestDto, TestCriteria, TestRepository, TestDomainService> {
}
