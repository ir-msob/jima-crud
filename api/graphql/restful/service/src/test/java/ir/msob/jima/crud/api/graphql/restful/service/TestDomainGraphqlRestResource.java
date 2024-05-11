package ir.msob.jima.crud.api.graphql.restful.service;


import ir.msob.jima.core.commons.model.scope.Resource;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.graphql.restful.service.base.CrudGraphqlRestResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static ir.msob.jima.core.commons.resource.ResourceType.RESTFUL_RESOURCE_TYPE;

@Controller
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = RESTFUL_RESOURCE_TYPE)
public class TestDomainGraphqlRestResource extends CrudGraphqlRestResource<TestDomain, TestDto, TestCriteria, TestRepository, TestService> {
}
