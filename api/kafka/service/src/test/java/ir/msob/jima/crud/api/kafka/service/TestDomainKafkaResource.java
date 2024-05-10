package ir.msob.jima.crud.api.kafka.service;


import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.kafka.client.ChannelUtil;
import ir.msob.jima.crud.api.kafka.service.base.CrudKafkaResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TestDomainKafkaResource extends CrudKafkaResource<TestDomain, TestDto, TestCriteria, TestRepository, TestService> {
    public static final String BASE_URI = ChannelUtil.getBaseChannel(TestDto.class);

}
