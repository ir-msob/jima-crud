package ir.msob.jima.crud.client;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This interface is intended for clients that interact with a service to perform asynchronous write operations.
 * It provides methods for creating, updating, and deleting data, and allows for asynchronous communication with the service.
 */
public interface BaseWriteAsyncClient extends BaseClient {

    /**
     * Asynchronously delete data item that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be deleted.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> delete(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    /**
     * Asynchronously delete item by id.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param id       The id item to be deleted.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> delete(Class<DTO> dtoClass, ID id, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        return this.delete(dtoClass, (C) CriteriaUtil.idCriteria(id), metadata, callback, user);
    }


    /**
     * Asynchronously delete multiple data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be deleted.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> deleteMany(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    /**
     * Asynchronously delete multiple items by ids.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param ids      The ids of items to be deleted.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> deleteMany(Class<DTO> dtoClass, Collection<ID> ids, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        return this.deleteMany(dtoClass, (C) CriteriaUtil.idCriteria(ids), metadata, callback, user);
    }


    /**
     * Asynchronously delete all data items of a specific type.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be deleted.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> deleteAll(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    /**
     * Asynchronously update data item based on a JSON patch and specified criteria.
     *
     * @param <ID>      The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>    The type of the user object, typically representing the user making the request.
     * @param <DTO>     The type of the data transfer object for the entity.
     * @param <C>       The type of criteria used for filtering items.
     * @param dtoClass  The class representing the data transfer object.
     * @param jsonPatch The JSON patch representing the changes to be applied.
     * @param criteria  The criteria for filtering items to be updated.
     * @param metadata  Additional metadata for the request.
     * @param callback  The callback for handling the response.
     * @param user      An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> edit(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    /**
     * Asynchronously update data item based on a JSON patch and id.
     *
     * @param <ID>      The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>    The type of the user object, typically representing the user making the request.
     * @param <DTO>     The type of the data transfer object for the entity.
     * @param <C>       The type of criteria used for filtering items.
     * @param dtoClass  The class representing the data transfer object.
     * @param jsonPatch The JSON patch representing the changes to be applied.
     * @param id        The id item to be updated.
     * @param metadata  Additional metadata for the request.
     * @param callback  The callback for handling the response.
     * @param user      An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> edit(Class<DTO> dtoClass, JsonPatch jsonPatch, ID id, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        return this.edit(dtoClass, jsonPatch, (C) CriteriaUtil.idCriteria(id), metadata, callback, user);

    }

    /**
     * Asynchronously update multiple data items based on a JSON patch and specified criteria.
     *
     * @param <ID>      The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>    The type of the user object, typically representing the user making the request.
     * @param <DTO>     The type of the data transfer object for the entity.
     * @param <C>       The type of criteria used for filtering items.
     * @param dtoClass  The class representing the data transfer object.
     * @param jsonPatch The JSON patch representing the changes to be applied.
     * @param criteria  The criteria for filtering items to be updated.
     * @param metadata  Additional metadata for the request.
     * @param callback  The callback for handling the response.
     * @param user      An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    /**
     * Asynchronously update multiple data items based on a JSON patch and ids.
     *
     * @param <ID>      The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>    The type of the user object, typically representing the user making the request.
     * @param <DTO>     The type of the data transfer object for the entity.
     * @param <C>       The type of criteria used for filtering items.
     * @param dtoClass  The class representing the data transfer object.
     * @param jsonPatch The JSON patch representing the changes to be applied.
     * @param ids       The ids of items to be updated.
     * @param metadata  Additional metadata for the request.
     * @param callback  The callback for handling the response.
     * @param user      An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, Collection<ID> ids, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        return this.editMany(dtoClass, jsonPatch, (C) CriteriaUtil.idCriteria(ids), metadata, callback, user);
    }

    /**
     * Asynchronously create or save a single data item.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param dto      The data transfer object to be created or saved.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> save(Class<DTO> dtoClass, DTO dto, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    /**
     * Asynchronously create or save multiple data items.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param dtos     A collection of data transfer objects to be created or saved.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> saveMany(Class<DTO> dtoClass, List<DTO> dtos, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    /**
     * Asynchronously update a single data item.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param dto      The data transfer object to be updated.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> update(Class<DTO> dtoClass, DTO dto, Map<String, Serializable> metadata, String callback, Optional<USER> user);

    /**
     * Asynchronously update a single data item.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param id       The id of item.
     * @param dto      The data transfer object to be updated.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> update(Class<DTO> dtoClass, ID id, DTO dto, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        dto.setDomainId(id);
        return update(dtoClass, dto, metadata, callback, user);
    }

    /**
     * Asynchronously update multiple data items.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param dtos     A collection of data transfer objects to be updated.
     * @param metadata Additional metadata for the request.
     * @param callback The callback for handling the response.
     * @param user     An optional user object associated with the request.
     * @return A Mono indicating completion of the operation.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> updateMany(Class<DTO> dtoClass, List<DTO> dtos, Map<String, Serializable> metadata, String callback, Optional<USER> user);
}
