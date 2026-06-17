package ir.msob.jima.crud.kafka.reactive.test.resource.domain;

import ir.msob.jima.platform.api.channel.BaseChannelTypeReference;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.kafka.test.listener.BaseCoreKafkaListenerTest;

import java.io.Serializable;

public interface ParentDomainCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>>
        extends BaseCoreKafkaListenerTest<ID, USER, D, DTO, C>
        , BaseChannelTypeReference<ID, USER, DTO, C> {

    String getBaseUri();

    String prepareCallbackTopic();

    String prepareTopic(String operation);


}
