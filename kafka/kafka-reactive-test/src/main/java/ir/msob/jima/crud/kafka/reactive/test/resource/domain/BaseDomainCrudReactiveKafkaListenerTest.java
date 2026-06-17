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

public interface BaseDomainCrudReactiveKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateDomainCrudReactiveKafkaListenerTest<ID, USER, D, DTO, C, R, S, DP> {
}
