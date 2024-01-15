package ir.msob.jima.crud.service.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.validation.ValidationException;
import ir.msob.jima.core.commons.model.audit.AuditDomainActionType;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * This service interface defines the contract for executing update operations for a single entity (DTO).
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
public interface BaseUpdateCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseUpdateCrudService.class);

    /**
     * Updates a single entity based on id and a DTO. Validates, prepares, and updates the entity.
     *
     * @param id   The id of entity.
     * @param dto  The DTO representing the entity to be updated.
     * @param user An optional user associated with the operation.
     * @return The DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Override
    @Transactional
    @MethodStats
    default Mono<DTO> update(ID id, @Valid DTO dto, Optional<USER> user) {
        dto.setDomainId(id);
        return update(dto, user);
    }

    /**
     * Updates a single entity based on a DTO. Validates, prepares, and updates the entity.
     *
     * @param dto  The DTO representing the entity to be updated.
     * @param user An optional user associated with the operation.
     * @return The DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Override
    @Transactional
    @MethodStats
    default Mono<DTO> update(@Valid DTO dto, Optional<USER> user) {
        return getOneByDto(dto, user)
                .flatMap(previousDto -> this.update(previousDto, dto, user));
    }

    /**
     * Updates a single entity based on a previous DTO and a new DTO. Validates, prepares, and updates the entity.
     *
     * @param previousDto The DTO representing the entity before the update.
     * @param dto         The new DTO representing the entity after the update.
     * @param user        An optional user associated with the operation.
     * @return The DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws ValidationException     if validation fails during the update.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Override
    @Transactional
    @MethodStats
    default Mono<DTO> update(DTO previousDto, @Valid DTO dto, Optional<USER> user) throws BadRequestException, ValidationException, DomainNotFoundException {
        log.debug("Update, user {}", user.orElse(null));

        D domain = toDomain(dto, user);
        Collection<ID> ids = Collections.singletonList(dto.getDomainId());
        Collection<DTO> dtos = Collections.singletonList(dto);
        Collection<D> domains = Collections.singletonList(domain);

        Collection<DTO> previousDtos = Collections.singletonList(previousDto);

        getBeforeAfterComponent().beforeUpdate(ids, previousDtos, dtos, user);

        return this.updateValidation(ids, dtos, domains, user)
                .then(this.preUpdate(ids, dtos, domains, user))
                .then(addAudit(dtos, AuditDomainActionType.UPDATE, user))
                .then(this.updateExecute(dto, domain, user))
                .doOnSuccess(updatedDomain -> this.postUpdate(ids, dtos, domains, Collections.singletonList(updatedDomain), user))
                .flatMap(updatedDomain -> getOne(updatedDomain, user))
                .doOnSuccess(updatedDto -> afterUpdate(ids, previousDtos, Collections.singletonList(updatedDto), user));
    }

    /**
     * Executes the actual update operation for a single entity using a DTO and a domain entity.
     *
     * @param dto    The DTO representing the entity after the update.
     * @param domain The domain entity to be updated.
     * @param user   An optional user associated with the operation.
     * @return A Mono representing the updated domain entity.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    default Mono<D> updateExecute(DTO dto, D domain, Optional<USER> user) throws DomainNotFoundException {
        return this.getRepository().updateOne(domain);
    }
}