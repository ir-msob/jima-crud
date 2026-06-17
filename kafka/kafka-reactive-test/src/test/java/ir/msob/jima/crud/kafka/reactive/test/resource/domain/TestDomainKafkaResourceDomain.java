package ir.msob.jima.crud.kafka.reactive.test.resource.domain;


import ir.msob.jima.crud.kafka.reactive.test.resource.domain.base.DomainCrudKafkaResource;
import ir.msob.jima.crud.reactive.testing.test.TestDomainService;
import ir.msob.jima.crud.reactive.testing.test.TestRepository;
import ir.msob.jima.platform.api.channel.ChannelUtil;
import ir.msob.jima.platform.mongo.testing.test.TestCriteria;
import ir.msob.jima.platform.mongo.testing.test.TestDomain;
import ir.msob.jima.platform.mongo.testing.test.TestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TestDomainKafkaResourceDomain extends DomainCrudKafkaResource<TestDomain, TestDto, TestCriteria, TestRepository, TestDomainService> {
    public static final String BASE_URI = ChannelUtil.getBaseChannel(TestDto.class);

}
