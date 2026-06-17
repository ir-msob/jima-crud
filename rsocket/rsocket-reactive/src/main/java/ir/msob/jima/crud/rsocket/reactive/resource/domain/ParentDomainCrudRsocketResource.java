package ir.msob.jima.crud.rsocket.reactive.resource.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.api.resource.BaseCrudResource;
import ir.msob.jima.crud.reactive.service.domain.ParentDomainCrudReactiveService;
import ir.msob.jima.platform.api.channel.BaseChannelTypeReference;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.rsocket.api.BaseCoreRsocketResource;

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
 * @param <R>    the type of the repository
 * @param <S>    the type of the service
 * @author Yaqub Abdi
 */
public interface ParentDomainCrudRsocketResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends ParentDomainCrudReactiveService<ID, USER, D, DTO, C, R>>
        extends BaseCoreRsocketResource<ID, USER>,
        BaseCrudResource<ID, USER>,
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