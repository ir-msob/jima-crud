package ir.msob.jima.crud.client.domain;

import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.client.BaseClient;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * This interface is intended for clients that interact with a service for asynchronous read operations.
 * It provides methods for retrieving data and asynchronous communication with the service.
 */
public interface BaseReadDomainAsyncClient extends BaseClient {

    /**
     * Asynchronously count the number of items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     A user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> count(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user);

    /**
     * Asynchronously count all items of a specific type.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     A user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Void> countAll(Class<DTO> dtoClass, Map<String, Serializable> metadata, String callback, USER user);

    /**
     * Asynchronously fetch a single item that meets the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     A user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getOne(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user);

    /**
     * Asynchronously fetch a single item by id.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param id       The of item.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     A user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Void> getById(Class<DTO> dtoClass, ID id, Map<String, Serializable> metadata, String callback, USER user);

    /**
     * Asynchronously fetch a single item using a channel message for more complex requests.
     *
     * @param <ID>           The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>         The type of the user object, typically representing the user making the request.
     * @param <DTO>          The type of the data transfer object for the entity.
     * @param <C>            The type of criteria used for filtering items.
     * @param dtoClass       The class representing the data transfer object.
     * @param channelMessage The channel message containing criteria and user information.
     * @param user           A user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getOne(Class<DTO> dtoClass, ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage, USER user);

    /**
     * Asynchronously fetch multiple items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     A user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getMany(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user);

    /**
     * Asynchronously fetch multiple items by ids.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param ids      The ids of items.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     A user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getMany(Class<DTO> dtoClass, Collection<ID> ids, Map<String, Serializable> metadata, String callback, USER user) {
        return this.getMany(dtoClass, (C) CriteriaUtil.idCriteria(ids), metadata, callback, user);
    }


    /**
     * Asynchronously fetch multiple items using a channel message for more complex requests.
     *
     * @param <ID>           The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>         The type of the user object, typically representing the user making the request.
     * @param <DTO>          The type of the data transfer object for the entity.
     * @param <C>            The type of criteria used for filtering items.
     * @param dtoClass       The class representing the data transfer object.
     * @param channelMessage The channel message containing criteria and user information.
     * @param user           A user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getMany(Class<DTO> dtoClass, ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage, USER user);

    /**
     * Asynchronously fetch a page of items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     A user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getPage(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user);
}
