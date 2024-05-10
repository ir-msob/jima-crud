package ir.msob.jima.crud.api.rsocket.service;


import ir.msob.jima.core.ral.mongo.it.Microservices;
import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.rsocket.service.base.CrudRsocketResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
@MessageMapping(TestDomainRsocketResource.BASE_URI)
@RequiredArgsConstructor
public class TestDomainRsocketResource extends CrudRsocketResource<TestDomain, TestDto, TestCriteria, TestRepository, TestService> {

    public static final String BASE_URI = "api." + Microservices.VERSION + "." + TestDomain.DOMAIN_URI;


}
