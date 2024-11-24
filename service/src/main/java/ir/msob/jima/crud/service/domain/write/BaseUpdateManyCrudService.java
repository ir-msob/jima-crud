package ir.msob.jima.crud.service.domain.write;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.validation.ValidationException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This service interface defines the contract for executing batch update operations on multiple entities (DTOs).
 * It extends the {@link ParentWriteCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be updated.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <Q>    The type of query used for database operations.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseUpdateManyCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseUpdateManyCrudService.class);

    /**
     * Updates multiple entities based on a collection of DTOs. Each DTO is validated, prepared, and updated individually.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param dtos The collection of DTOs to be updated.
     * @param user A user associated with the operation.
     * @return A Mono of a collection of DTOs representing the updated entities.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if an entity to be updated is not found.
     */
    @Override
    @Transactional
    @MethodStats
    default Mono<Collection<DTO>> updateMany(Collection<@Valid DTO> dtos, USER user) {
        return getManyByDto(dtos, user)
                .flatMap(previousDtos -> this.updateMany(previousDtos, dtos, user));
    }

    /**
     * Updates multiple entities based on collections of previous DTOs and new DTOs. Each DTO is validated, prepared, and updated individually.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param previousDtos The collection of previous DTOs representing existing entities.
     * @param dtos         The collection of new DTOs to be used for updates.
     * @param user         A user associated with the operation.
     * @return A Mono of a collection of DTOs representing the updated entities.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws ValidationException     if validation fails during the update.
     * @throws DomainNotFoundException if an entity to be updated is not found.
     */
    @Override
    @Transactional
    @MethodStats
    default Mono<Collection<DTO>> updateMany(Collection<DTO> previousDtos, Collection<@Valid DTO> dtos, USER user) {
        log.debug("UpdateMany, dto.size {}, user {}", dtos.size(), user);

        return Flux.fromIterable(dtos)
                .flatMap(dto -> {
                    DTO previousDto = previousDtos.stream()
                            .filter(pd -> pd.getDomainId().equals(dto.getDomainId()))
                            .findFirst()
                            .orElseThrow();
                    return update(previousDto, dto, user);
                })
                .collectList()
                .map(ArrayList::new);
    }

}