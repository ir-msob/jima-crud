package ir.msob.jima.crud.client;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * The BaseReadClient interface provides methods for performing read operations in a client interacting with a service.
 * These operations include counting data items, retrieving single items, retrieving multiple items, and fetching items in pages.
 *
 * @author Yaqub Abdi
 */
public interface BaseReadClient extends BaseClient {

    /**
     * Synchronously count data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be counted.
     * @param user     A user object associated with the request.
     * @return A Mono indicating the count of items that meet the criteria.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Long> count(Class<DTO> dtoClass, C criteria, USER user);

    /**
     * Synchronously count all data items of a specific type.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param user     A user object associated with the request.
     * @return A Mono indicating the count of all items of the specified type.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Long> countAll(Class<DTO> dtoClass, USER user);

    /**
     * Synchronously retrieve a single data item that meets the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param dtoClass The class representing the data transfer object.
     * @param user     A user object associated with the request.
     * @return A Mono containing the retrieved data item.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> getById(Class<DTO> dtoClass, ID id, USER user);


    /**
     * Synchronously retrieve a single data item that meets the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering the item to be retrieved.
     * @param user     A user object associated with the request.
     * @return A Mono containing the retrieved data item.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> getOne(Class<DTO> dtoClass, C criteria, USER user);

    /**
     * Synchronously retrieve a single data item.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param id       The id of item to be retrieved.
     * @param user     A user object associated with the request.
     * @return A Mono containing the retrieved data item.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> getOne(Class<DTO> dtoClass, ID id, USER user) {
        return this.getOne(dtoClass, (C) CriteriaUtil.idCriteria(id), user);
    }

    /**
     * Synchronously retrieve multiple data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be retrieved.
     * @param user     A user object associated with the request.
     * @return A Mono containing a collection of retrieved data items.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> getMany(Class<DTO> dtoClass, C criteria, USER user);

    /**
     * Synchronously retrieve multiple data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param ids      The ids of items to be retrieved.
     * @param user     A user object associated with the request.
     * @return A Mono containing a collection of retrieved data items.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> getMany(Class<DTO> dtoClass, Collection<ID> ids, USER user) {
        return this.getMany(dtoClass, (C) CriteriaUtil.idCriteria(ids), user);
    }

    /**
     * Synchronously retrieve multiple data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be retrieved.
     * @param user     A user object associated with the request.
     * @return A Mono containing a collection of retrieved data items.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Flux<DTO> getStream(Class<DTO> dtoClass, C criteria, USER user);

    /**
     * Synchronously retrieve multiple data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param ids      The ids of items to be retrieved.
     * @param user     A user object associated with the request.
     * @return A Mono containing a collection of retrieved data items.
     */
    default <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Flux<DTO> getStream(Class<DTO> dtoClass, Collection<ID> ids, USER user) {
        return this.getStream(dtoClass, (C) CriteriaUtil.idCriteria(ids), user);
    }

    /**
     * Synchronously retrieve a page of data items that meet the specified criteria.
     *
     * @param <ID>     The type of the entity's ID, which must be comparable and serializable.
     * @param <USER>   The type of the user object, typically representing the user making the request.
     * @param <DTO>    The type of the data transfer object for the entity.
     * @param <C>      The type of criteria used for filtering items.
     * @param dtoClass The class representing the data transfer object.
     * @param criteria The criteria for filtering items to be retrieved.
     * @param user     A user object associated with the request.
     * @return A Mono containing a page of retrieved data items.
     */
    <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Page<DTO>> getPage(Class<DTO> dtoClass, C criteria, USER user);
}