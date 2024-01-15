package ir.msob.jima.crud.api.rsocket.test;

import ir.msob.jima.core.api.rsocket.test.BaseCoreRsocketResourceTest;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;

import java.io.Serializable;

public interface ParentCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>>
        extends BaseCoreRsocketResourceTest<ID, USER, D, DTO, C> {

    String getBaseUri();

    default String getUri(String action) {
        return String.format("%s.%s", getBaseUri(), action);
    }
}
