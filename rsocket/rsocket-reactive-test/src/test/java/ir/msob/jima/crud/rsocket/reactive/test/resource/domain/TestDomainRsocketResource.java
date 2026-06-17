package ir.msob.jima.crud.rsocket.reactive.test.resource.domain;

import ir.msob.jima.crud.reactive.testing.test.TestDomainService;
import ir.msob.jima.crud.reactive.testing.test.TestRepository;
import ir.msob.jima.crud.rsocket.reactive.test.resource.domain.base.DomainCrudRsocketResource;
import ir.msob.jima.platform.api.resource.Resource;
import ir.msob.jima.platform.api.shared.ResourceType;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import ir.msob.jima.platform.mongo.testing.test.TestDtoTypeReference;
import ir.msob.jima.platform.testing.Microservices;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
@MessageMapping(TestDomainRsocketResource.BASE_URI)
@RequiredArgsConstructor
@Resource(value = TestDomain.DOMAIN_URI, type = ResourceType.RSOCKET)
public class TestDomainRsocketResource extends DomainCrudRsocketResource<TestDomain, TestDto, TestCriteria, TestRepository, TestDomainService> implements TestDtoTypeReference {

    public static final String BASE_URI = "api." + Microservices.VERSION + "." + TestDomain.DOMAIN_URI;

}
