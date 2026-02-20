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

/**
 * This service interface defines the contract for executing update operations for a single entity (DTO).
 * It extends the {@link ParentWriteChildDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be updated.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseUpdateChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>, C extends BaseChildCriteria<ID>, R extends BaseDomainCrudRepository<ID, D, C>> extends ParentWriteChildDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseUpdateChildDomainCrudService.class);

    /**
     * Updates a single entity based on id and a DTO. Validates, prepares, and updates the entity.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param id   The id of entity.
     * @param dto  The DTO representing the entity to be updated.
     * @param user A user associated with the operation.
     * @return The DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Transactional
    @MethodStats
    default Mono<@NonNull DTO> update(ID parentId, ID id, @Valid DTO dto, USER user) {
        dto.setParentId(parentId);
        dto.setId(id);
        return doUpdate(dto, user);
    }

    /**
     * Updates a single entity based on a DTO. Validates, prepares, and updates the entity.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param dto  The DTO representing the entity to be updated.
     * @param user A user associated with the operation.
     * @return The DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Transactional
    @MethodStats
    default Mono<@NonNull DTO> update(ID parentId, @Valid DTO dto, USER user) {
        dto.setParentId(parentId);
        return doUpdate(dto, user);
    }


    /**
     * Updates a single entity based on a previous DTO and a new DTO. Validates, prepares, and updates the entity.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param previousDto The DTO representing the entity before the update.
     * @param dto         The new DTO representing the entity after the update.
     * @param user        A user associated with the operation.
     * @return The DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws ValidationException     if validation fails during the update.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Transactional
    @MethodStats
    default Mono<@NonNull DTO> update(ID parentId, DTO previousDto, @Valid DTO dto, USER user) throws BadRequestException, ValidationException, DomainNotFoundException {
        dto.setParentId(parentId);
        return doUpdate(previousDto, dto, user);
    }


}