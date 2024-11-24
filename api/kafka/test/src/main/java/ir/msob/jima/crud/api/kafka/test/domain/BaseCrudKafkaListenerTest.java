package ir.msob.jima.crud.api.kafka.test.domain;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.kafka.test.domain.read.*;
import ir.msob.jima.crud.api.kafka.test.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;

import java.io.Serializable;

public interface BaseCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends
        BaseCountAllCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseCountCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetManyCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetByIdCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetOneCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetPageCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteByIdCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteManyCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditManyCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditByIdCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveManyCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateManyCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateByIdCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateCrudKafkaListenerTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}
