package ir.msob.jima.crud.api.rsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.api.rsocket.commons.BaseCoreRsocketResource;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.BaseChannelTypeReference;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.commons.ParentCrudService;

import java.io.Serializable;

/**
 * @param <ID>
 * @param <USER>
 * @param <S>
 * @author Yaqub Abdi
 */
public interface ParentCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends ParentCrudService<ID, USER, D, DTO, C, R>>
        extends BaseCoreRsocketResource<ID, USER>,
        BaseCrudResource,
        BaseChannelTypeReference<ID, USER, DTO, C> {

    S getService();

    ObjectMapper getObjectMapper();

}
