package ir.msob.jima.crud.core.service.domain.write;

import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.security.BaseUser;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

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
public interface BaseSaveDomainCrudService<ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>>
        extends ParentWriteDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseSaveDomainCrudService.class);

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
    default @NonNull DTO save(@Valid DTO dto, USER user) throws BadRequestException, DomainNotFoundException {
        logger.debug("Save, user {}", user);

        return doSave(dto, user);
    }

}