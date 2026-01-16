package ir.msob.jima.crud.service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.commons.repository.BaseRepository;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.service.BaseService;
import ir.msob.jima.crud.service.domain.operation.BeforeAfterOperationComponent;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface defines common CRUD operations for a domain entity.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user context.
 * @param <D>    The type of domain entity.
 * @param <DTO>  The type of DTO (Data Transfer Object) associated with the domain entity.
 * @param <C>    The type of criteria used for querying domain entities.
 * @param <R>    The type of repository for the domain entity.
 * @author Yaqub Abdi
 */
public interface ParentDomainCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseRepository<ID, D, C>>
        extends BaseService<ID, USER, D, DTO, C, R> {

    /**
     * Get one DTO entity based on criteria.
     *
     * @param criteria The criteria for finding the DTO entity.
     * @param user     A user context.
     * @return A Mono that emits the found DTO entity.
     */
    default Mono<DTO> getOne(C criteria, USER user) {
        return Mono.empty();
    }

    /**
     * Get a collection of DTO entities based on criteria.
     *
     * @param criteria The criteria for finding the DTO entities.
     * @param user     A user context.
     * @return A Mono that emits a collection of DTO entities.
     */
    default Mono<Collection<DTO>> getMany(C criteria, USER user) {
        return Mono.empty();
    }

    /**
     * Get a stream of DTO entities based oncriteria.
     *
     * @param criteria The criteria for finding the DTO entities.
     * @param user     A user context.
     * @return A Mono that emits a collection of DTO entities.
     */
    default Flux<DTO> getStream(C criteria, USER user) {
        return Flux.empty();
    }

    /**
     * Update a DTO entity.
     *
     * @param dto  The DTO entity to be updated.
     * @param user A user context.
     * @return A Mono that emits the updated DTO entity.
     */
    default Mono<DTO> update(@Valid DTO dto, USER user) {
        return Mono.empty();
    }

    /**
     * Update a DTO entity.
     *
     * @param id   The id of entity.
     * @param dto  The DTO entity to be updated.
     * @param user A user context.
     * @return A Mono that emits the updated DTO entity.
     */
    default Mono<DTO> update(ID id, @Valid DTO dto, USER user) {
        return Mono.empty();
    }

    /**
     * Update a DTO entity using both old and new DTOs.
     *
     * @param oldDto The old DTO entity.
     * @param dto    The new DTO entity to be updated.
     * @param user   A user context.
     * @return A Mono that emits the updated DTO entity.
     */
    default Mono<DTO> update(DTO oldDto, @Valid DTO dto, USER user) {
        return Mono.empty();
    }

    /**
     * Update multiple DTO entities.
     *
     * @param dtos The collection of DTO entities to be updated.
     * @param user A user context.
     * @return A Mono that emits a collection of updated DTO entities.
     */
    default Mono<Collection<DTO>> updateMany(Collection<@Valid DTO> dtos, USER user) {
        return Mono.empty();
    }

    /**
     * Update multiple DTO entities using both old and new DTOs.
     *
     * @param oldDtos The collection of old DTO entities.
     * @param dtos    The collection of new DTO entities to be updated.
     * @param user    A user context.
     * @return A Mono that emits a collection of updated DTO entities.
     */
    default Mono<Collection<DTO>> updateMany(Collection<DTO> oldDtos, Collection<@Valid DTO> dtos, USER user) {
        return Mono.empty();
    }

    /**
     * Get the before/after component for the service.
     *
     * @return The before/after component.
     */
    BeforeAfterOperationComponent getBeforeAfterOperationComponent();

    /**
     * Get the collection of before/after domain operations.
     *
     * @return The collection of before/after domain operations.
     */
    Collection<BaseBeforeAfterDomainOperation<ID, USER, DTO, C>> getBeforeAfterDomainOperations();

    /**
     * Get the ObjectMapper for handling JSON data.
     *
     * @return The ObjectMapper instance.
     */
    ObjectMapper getObjectMapper();

    /**
     * Prepare a collection of entity IDs from a collection of domain entities.
     *
     * @param domains The collection of domain entities.
     * @return A collection of entity IDs.
     */
    default Collection<ID> prepareIds(Collection<? extends BaseDomain<ID>> domains) {
        return domains
                .stream()
                .map(BaseDomain::getId)
                .toList();
    }
}
