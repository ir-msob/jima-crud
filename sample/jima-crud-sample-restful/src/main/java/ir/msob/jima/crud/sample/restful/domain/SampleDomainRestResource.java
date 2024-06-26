package ir.msob.jima.crud.sample.restful.domain;


import ir.msob.jima.core.commons.model.scope.Resource;
import ir.msob.jima.crud.sample.restful.base.Microservices;
import ir.msob.jima.crud.sample.restful.base.resource.CrudRestResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ir.msob.jima.core.commons.resource.ResourceType.RESTFUL_RESOURCE_TYPE;

@RestController
@RequestMapping(SampleDomainRestResource.BASE_URI)
@RequiredArgsConstructor
@Resource(value = SampleDomain.DOMAIN_URI, type = RESTFUL_RESOURCE_TYPE)
public class SampleDomainRestResource extends CrudRestResource<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService> {
    public static final String BASE_URI = "/api/" + Microservices.VERSION + "/" + SampleDomain.DOMAIN_URI;
}
