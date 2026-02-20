package ir.msob.jima.crud.service.childdomain.write;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.validation.ValidationException;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This service interface defines the contract for executing batch update operations on multiple entities (DTOs).
 * It extends the {@link ParentWriteChildDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be updated.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseUpdateManyChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>, C extends BaseChildCriteria<ID>, R extends BaseDomainCrudRepository<ID, D, C>> extends ParentWriteChildDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseUpdateManyChildDomainCrudService.class);

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
    default Mono<@NonNull Collection<DTO>> updateMany(ID parentId, Collection<@Valid DTO> dtos, USER user) {
        dtos.forEach(dto -> dto.setParentId(parentId));
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
    default Mono<@NonNull Collection<DTO>> updateMany(ID parentId, Collection<DTO> previousDtos, Collection<@Valid DTO> dtos, USER user) {
        logger.debug("UpdateMany, dto.size {}, user {}", dtos.size(), user);
        dtos.forEach(dto -> dto.setParentId(parentId));
        return this.doUpdateMany(previousDtos, dtos, user);
    }


}