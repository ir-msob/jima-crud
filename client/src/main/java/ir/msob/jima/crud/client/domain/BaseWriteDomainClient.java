package ir.msob.jima.crud.client.domain;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.client.BaseClient;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * The BaseWriteDomainClient interface provides methods for performing write operations in a client interacting with a service.
 * These operations include data deletion, modification, and creation.
 *
 * @author Yaqub Abdi
 */
public interface BaseWriteDomainClient extends BaseClient {

    /**
     * Synchronously delete data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param user     A user object associated with the request.
     * @return A Mono containing the ID of the deleted data item.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<ID> deleteById(Class<DTO> dtoClass, ID id, USER user);


    /**
     * Synchronously delete data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be deleted.
     * @param user     A user object associated with the request.
     * @return A Mono containing the ID of the deleted data item.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<ID> delete(Class<DTO> dtoClass, C criteria, USER user);


    /**
     * Synchronously delete multiple data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be deleted.
     * @param user     A user object associated with the request.
     * @return A Mono containing a collection of IDs for the deleted data items.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteMany(Class<DTO> dtoClass, C criteria, USER user);

    /**
     * Synchronously delete multiple data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param ids      The ids of items to be deleted.
     * @param user     A user object associated with the request.
     * @return A Mono containing a collection of IDs for the deleted data items.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteMany(Class<DTO> dtoClass, Collection<ID> ids, USER user) {
        return this.deleteMany(dtoClass, (C) CriteriaUtil.idCriteria(ids), user);
    }


    /**
     * Synchronously delete all data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be deleted.
     * @param user     A user object associated with the request.
     * @return A Mono containing a collection of IDs for the deleted data items.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteAll(Class<DTO> dtoClass, C criteria, USER user);

    /**
     * Synchronously edit a data item using a JSON Patch.
     *
     * @param <ID>      The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>    The type of the user object, typically representing the user making the request.
     * @param <DTO>     The type of the data transfer object for the entity.
     * @param dtoClass  The class representing the data transfer object.
     * @param jsonPatch The JSON Patch describing the modifications to be applied.
     * @param user      A user object associated with the request.
     * @return A Mono containing the edited data item.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> editById(Class<DTO> dtoClass, ID id, JsonPatch jsonPatch, USER user);


    /**
     * Synchronously edit a data item using a JSON Patch.
     *
     * @param <ID>      The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>    The type of the user object, typically representing the user making the request.
     * @param <DTO>     The type of the data transfer object for the entity.
     * @param <C>       The type of criteria used for filtering items.
     * @param dtoClass  The class representing the data transfer object.
     * @param jsonPatch The JSON Patch describing the modifications to be applied.
     * @param criteria  The criteria for filtering the item to be edited.
     * @param user      A user object associated with the request.
     * @return A Mono containing the edited data item.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> edit(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, USER user);


    /**
     * Synchronously edit multiple data items using a JSON Patch.
     *
     * @param <ID>      The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>    The type of the user object, typically representing the user making the request.
     * @param <DTO>     The type of the data transfer object for the entity.
     * @param <C>       The type of criteria used for filtering items.
     * @param dtoClass  The class representing the data transfer object.
     * @param jsonPatch The JSON Patch describing the modifications to be applied.
     * @param criteria  The criteria for filtering items to be edited.
     * @param user      A user object associated with the request.
     * @return A Mono containing a collection of edited data items.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, USER user);

    /**
     * Synchronously edit multiple data items using a JSON Patch.
     *
     * @param <ID>      The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>    The type of the user object, typically representing the user making the request.
     * @param <DTO>     The type of the data transfer object for the entity.
     * @param <C>       The type of criteria used for filtering items.
     * @param dtoClass  The class representing the data transfer object.
     * @param jsonPatch The JSON Patch describing the modifications to be applied.
     * @param ids       The ids of items to be edited.
     * @param user      A user object associated with the request.
     * @return A Mono containing a collection of edited data items.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, Collection<ID> ids, USER user) {
        return this.editMany(dtoClass, jsonPatch, (C) CriteriaUtil.idCriteria(ids), user);
    }

    /**
     * Synchronously create or save a single data item.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param dto      The data transfer object to be created or saved.
     * @param user     A user object associated with the request.
     * @return A Mono indicating the ID of the created or saved data item.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> save(Class<DTO> dtoClass, DTO dto, USER user);

    /**
     * Synchronously create or save multiple data items.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param dtos     A collection of data transfer objects to be created or saved.
     * @param user     A user object associated with the request.
     * @return A Mono indicating a collection of created or saved data items.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Collection<DTO>> saveMany(Class<DTO> dtoClass, Collection<DTO> dtos, USER user);

    /**
     * Synchronously update a single data item.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param dto      The data transfer object to be updated.
     * @param user     A user object associated with the request.
     * @return A Mono indicating the updated data item.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> updateById(Class<DTO> dtoClass, ID id, DTO dto, USER user);


    /**
     * Synchronously update a single data item.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param dto      The data transfer object to be updated.
     * @param user     A user object associated with the request.
     * @return A Mono indicating the updated data item.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> update(Class<DTO> dtoClass, DTO dto, USER user);


    /**
     * Synchronously update multiple data items.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param dtos     A collection of data transfer objects to be updated.
     * @param user     A user object associated with the request.
     * @return A Mono indicating a collection of updated data items.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Collection<DTO>> updateMany(Class<DTO> dtoClass, Collection<DTO> dtos, USER user);
}