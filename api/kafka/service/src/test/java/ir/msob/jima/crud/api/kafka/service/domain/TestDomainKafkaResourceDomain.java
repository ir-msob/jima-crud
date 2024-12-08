package ir.msob.jima.crud.api.kafka.service.domain;


import ir.msob.jima.core.ral.mongo.it.test.TestCriteria;
import ir.msob.jima.core.ral.mongo.it.test.TestDomain;
import ir.msob.jima.core.ral.mongo.it.test.TestDto;
import ir.msob.jima.crud.api.kafka.client.ChannelUtil;
import ir.msob.jima.crud.api.kafka.service.domain.base.DomainCrudKafkaResource;
import ir.msob.jima.crud.ral.mongo.it.test.TestRepository;
import ir.msob.jima.crud.ral.mongo.it.test.TestServiceDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TestDomainKafkaResourceDomain extends DomainCrudKafkaResource<TestDomain, TestDto, TestCriteria, TestRepository, TestServiceDomain> {
    public static final String BASE_URI = ChannelUtil.getBaseChannel(TestDto.class);

}
