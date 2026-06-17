package ir.msob.jima.crud.kafka.reactive.test.resource.domain;

import ir.msob.jima.crud.kafka.reactive.test.resource.domain.read.*;
import ir.msob.jima.crud.kafka.reactive.test.resource.domain.write.*;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

public interface BaseDomainCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
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
