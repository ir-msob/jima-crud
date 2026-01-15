package ir.msob.jima.crud.service.domain.write;

import ir.msob.jima.core.commons.childdomain.auditdomain.AuditDomainActionType;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * This service interface defines the contract for executing save operations for a single entity (DTO).
 * It extends the {@link ParentWriteDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be saved.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseSaveDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, R extends BaseDomainCrudRepository<ID, D, C>> extends ParentWriteDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger log = LoggerFactory.getLogger(BaseSaveDomainCrudService.class);

    /**
     * Executes a save operation for a single entity (DTO). Validates, prepares, and saves the entity.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param dto  The DTO to be saved.
     * @param user A user associated with the operation.
     * @return A DTO representing the saved entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entity to be saved is not found.
     */
    @Transactional
    @MethodStats
    @Override
    default Mono<DTO> save(@Valid DTO dto, USER user) throws BadRequestException, DomainNotFoundException {
        log.debug("Save, user {}", user);

        D domain = toDomain(dto, user);
        getBeforeAfterComponent().beforeSave(dto, user, getBeforeAfterDomainOperations());
        return this.preSave(dto, user)
                .doOnSuccess(unused -> addAudit(dto, AuditDomainActionType.CREATE, user))
                .then(this.getRepository().insertOne(domain))
                .doOnSuccess(savedDomain -> this.postSave(dto, savedDomain, user))
                .flatMap(savedDomain -> getOneById(savedDomain.getId(), user))
                .doOnSuccess(savedDto -> getBeforeAfterComponent().afterSave(dto, savedDto, user, getBeforeAfterDomainOperations()));
    }
}