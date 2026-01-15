package ir.msob.jima.crud.api.kafka.test.domain;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.kafka.test.domain.read.*;
import ir.msob.jima.crud.api.kafka.test.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;

import java.io.Serializable;

public interface BaseDomainCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateDomainCrudKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP> {
}
