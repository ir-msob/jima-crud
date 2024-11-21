package ir.msob.jima.crud.api.rsocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.api.rsocket.commons.BaseCoreRsocketResource;
import ir.msob.jima.core.commons.channel.BaseChannelTypeReference;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.commons.BaseCrudResource;
import ir.msob.jima.crud.service.ParentCrudService;

import java.io.Serializable;

/**
 * This interface provides a RSocket API for CRUD operations.
 * It extends multiple interfaces each providing a specific CRUD operation.
 *
 * @param <ID>   the type of the ID of the domain
 * @param <USER> the type of the user
 * @param <D>    the type of the domain
 * @param <DTO>  the type of the DTO
 * @param <C>    the type of the criteria
 * @param <Q>    the type of the query
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 * @author Yaqub Abdi
 */
public interface ParentCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends ParentCrudService<ID, USER, D, DTO, C, R>>
        extends BaseCoreRsocketResource<ID, USER>,
        BaseCrudResource,
        BaseChannelTypeReference<ID, USER, DTO, C> {

    /**
     * This method returns the service that provides the CRUD operations.
     *
     * @return the service that provides the CRUD operations
     */
    S getService();

    /**
     * This method returns the ObjectMapper that can be used for JSON processing.
     *
     * @return the ObjectMapper that can be used for JSON processing
     */
    ObjectMapper getObjectMapper();
}