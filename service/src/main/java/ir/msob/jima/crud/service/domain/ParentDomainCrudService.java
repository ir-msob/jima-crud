package ir.msob.jima.crud.service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.commons.repository.BaseRepository;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.service.BaseService;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
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
        R extends BaseRepository<ID, USER, D>>
        extends BaseService<ID, USER, D, R> {
    /**
     * Get the repository instance associated with the service.
     *
     * @return The repository instance.
     */
    R getRepository();

    /**
     * Get the class of DTO associated with the service.
     *
     * @return The class representing the DTO.
     */
    default Class<DTO> getDtoClass() {
        return (Class<DTO>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentDomainCrudService.class, 3);
    }

    /**
     * Get the class of criteria associated with the service.
     *
     * @return The class representing the criteria.
     */
    default Class<C> getCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentDomainCrudService.class, 4);
    }

    /**
     * Create a new instance of the criteria class.
     *
     * @return A new instance of the criteria class.
     */
    @SneakyThrows
    default C newCriteriaClass() {
        return getCriteriaClass().getDeclaredConstructor().newInstance();
    }

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
     * Convert a domain entity to a DTO entity.
     *
     * @param domain The domain entity to convert.
     * @param user   A user context.
     * @return The converted DTO entity.
     */
    DTO toDto(D domain, USER user);

    /**
     * Convert a DTO entity to a domain entity.
     *
     * @param dto  The DTO entity to convert.
     * @param user A user context.
     * @return The converted domain entity.
     */
    D toDomain(DTO dto, USER user);

    /**
     * Get the before/after component for the service.
     *
     * @return The before/after component.
     */
    BeforeAfterComponent getBeforeAfterComponent();

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
