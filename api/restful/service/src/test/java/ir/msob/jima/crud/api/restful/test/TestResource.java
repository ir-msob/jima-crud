package ir.msob.jima.crud.api.restful.test;


import ir.msob.jima.core.commons.model.scope.Resource;
import ir.msob.jima.core.ral.mongo.it.Microservices;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.restful.base.CrudRestResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestResource.BASE_URI)
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = "restful") // FIXME : configurable
public class TestResource extends CrudRestResource<TestDomain, TestDto, TestCriteria, TestRepository, TestService> {
    public static final String BASE_URI = "/api/" + Microservices.VERSION + "/" + TestDomain.DOMAIN_URI;
}
