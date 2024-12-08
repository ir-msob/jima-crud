package ir.msob.jima.crud.service.domain.write;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.validation.ValidationException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.related.auditdomain.AuditDomainActionType;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * This service interface defines the contract for executing update operations for a single entity (DTO).
 * It extends the {@link ParentWriteDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be updated.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <Q>    The type of query used for database operations.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseUpdateDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseDomainCrudRepository<ID, USER, D, C, Q>> extends ParentWriteDomainCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseUpdateDomainCrudService.class);

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
    @Override
    @Transactional
    @MethodStats
    default Mono<DTO> update(ID id, @Valid DTO dto, USER user) {
        dto.setId(id);
        return update(dto, user);
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
    @Override
    @Transactional
    @MethodStats
    default Mono<DTO> update(@Valid DTO dto, USER user) {
        return getOneByID(dto.getId(), user)
                .flatMap(previousDto -> this.update(previousDto, dto, user));
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
    @Override
    @Transactional
    @MethodStats
    default Mono<DTO> update(DTO previousDto, @Valid DTO dto, USER user) throws BadRequestException, ValidationException, DomainNotFoundException {
        log.debug("Update, user {}", user);

        D domain = toDomain(dto, user);

        getBeforeAfterComponent().beforeUpdate(previousDto, dto, user, getBeforeAfterDomainOperations());

        return this.preUpdate(dto, user)
                .doOnSuccess(unused -> addAudit(dto, AuditDomainActionType.UPDATE, user))
                .then(this.getRepository().updateOne(domain))
                .doOnSuccess(updatedDomain -> this.postUpdate(dto, updatedDomain, user))
                .flatMap(updatedDomain -> getOneByID(updatedDomain.getId(), user))
                .doOnSuccess(updatedDto ->
                        getBeforeAfterComponent().afterUpdate(previousDto, updatedDto, user, getBeforeAfterDomainOperations()));
    }

}