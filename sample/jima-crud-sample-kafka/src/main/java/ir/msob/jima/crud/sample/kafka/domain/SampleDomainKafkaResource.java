package ir.msob.jima.crud.sample.kafka.domain;


import ir.msob.jima.core.commons.scope.Resource;
import ir.msob.jima.core.commons.operation.ConditionalOnOperation;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.crud.api.kafka.client.ChannelUtil;
import ir.msob.jima.crud.sample.kafka.base.resource.CrudKafkaResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static ir.msob.jima.core.commons.resource.ResourceType.KAFKA_RESOURCE_TYPE;

@ConditionalOnOperation({Operations.SAVE, Operations.UPDATE_BY_ID, Operations.DELETE_BY_ID})
@Component
@RequiredArgsConstructor
@Resource(value = SampleDomain.DOMAIN_URI, type = KAFKA_RESOURCE_TYPE)
public class SampleDomainKafkaResource extends CrudKafkaResource<SampleDomain, SampleDto, SampleCriteria, SampleRepository, SampleService> {
    public static final String BASE_URI = ChannelUtil.getBaseChannel(SampleDto.class);

}
