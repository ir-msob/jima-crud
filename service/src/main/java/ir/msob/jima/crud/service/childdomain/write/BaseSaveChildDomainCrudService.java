package ir.msob.jima.crud.service.childdomain.write;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
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
 * This service interface defines the contract for executing save operations for a single entity (DTO).
 * It extends the {@link ParentWriteChildDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be saved.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseSaveChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>, C extends BaseChildCriteria<ID>, R extends BaseDomainCrudRepository<ID, D, C>> extends ParentWriteChildDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseSaveChildDomainCrudService.class);

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
    default Mono<@NonNull DTO> save(ID parentId, @Valid DTO dto, USER user) throws BadRequestException, DomainNotFoundException {
        logger.debug("Save, user {}", user);
        dto.setParentId(parentId);

        return doSave(dto, user);
    }


}