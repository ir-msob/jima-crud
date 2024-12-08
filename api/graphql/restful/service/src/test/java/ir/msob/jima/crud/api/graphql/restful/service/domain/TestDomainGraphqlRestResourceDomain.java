package ir.msob.jima.crud.api.graphql.restful.service.domain;


import ir.msob.jima.core.commons.scope.Resource;
import ir.msob.jima.core.commons.shared.ResourceType;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.graphql.restful.service.domain.base.DomainCrudGraphqlRestResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestServiceDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = ResourceType.RESTFUL)
public class TestDomainGraphqlRestResourceDomain extends DomainCrudGraphqlRestResource<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain> {
}
