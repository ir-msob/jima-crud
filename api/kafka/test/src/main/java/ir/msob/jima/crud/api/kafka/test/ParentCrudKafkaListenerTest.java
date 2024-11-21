package ir.msob.jima.crud.api.kafka.test;

import ir.msob.jima.core.api.kafka.test.BaseCoreKafkaListenerTest;
import ir.msob.jima.core.commons.channel.BaseChannelTypeReference;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;

import java.io.Serializable;

public interface ParentCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>>
        extends BaseCoreKafkaListenerTest<ID, USER, D, DTO, C>
        , BaseChannelTypeReference<ID, USER, DTO, C> {

    String getBaseUri();

    String prepareCallbackTopic();

    String prepareTopic(String operation);


}
