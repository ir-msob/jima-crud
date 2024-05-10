package ir.msob.jima.crud.api.kafka.test;

import ir.msob.jima.core.api.kafka.test.BaseCoreKafkaListenerTest;
import ir.msob.jima.core.commons.model.channel.BaseChannelTypeReference;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;

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
