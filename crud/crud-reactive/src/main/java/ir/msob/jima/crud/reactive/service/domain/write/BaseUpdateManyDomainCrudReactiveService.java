package ir.msob.jima.crud.reactive.service.domain.write;

import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.exception.validation.ValidationException;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This service interface defines the contract for executing batch update operations on multiple entities (DTOs).
 * It extends the {@link ParentWriteDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be updated.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseUpdateManyDomainCrudReactiveService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>, R extends BaseReactiveRepository<ID, D, C>> extends ParentWriteDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseUpdateManyDomainCrudReactiveService.class);

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
    @Transactional
    @MethodStats
    default Mono<@NonNull Collection<DTO>> updateMany(Collection<@Valid DTO> dtos, USER user) {
        return getManyByDto(dtos, user)
                .flatMap(previousDtos -> this.doUpdateMany(previousDtos, dtos, user));
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
    @Transactional
    @MethodStats
    default Mono<@NonNull Collection<DTO>> updateMany(Collection<DTO> previousDtos, Collection<@Valid DTO> dtos, USER user) {
        logger.debug("UpdateMany, dto.size {}, user {}", dtos.size(), user);

        return this.doUpdateMany(previousDtos, dtos, user);
    }

}