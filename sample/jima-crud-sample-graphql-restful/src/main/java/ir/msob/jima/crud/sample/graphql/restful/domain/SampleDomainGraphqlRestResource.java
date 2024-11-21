package ir.msob.jima.crud.sample.graphql.restful.domain;


import ir.msob.jima.core.commons.scope.Resource;
import ir.msob.jima.crud.sample.graphql.restful.base.resource.CrudGraphqlRestResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static ir.msob.jima.core.commons.resource.ResourceType.RESTFUL_RESOURCE_TYPE;

@Controller
@RequiredArgsConstructor
@Resource(value = SampleDomain.DOMAIN_URI, type = RESTFUL_RESOURCE_TYPE)
public class SampleDomainGraphqlRestResource extends CrudGraphqlRestResource<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService> {
}
