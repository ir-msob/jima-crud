package ir.msob.jima.crud.core.service.domain.read;

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
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * This interface defines a service for counting all domain entities, potentially based on certain criteria.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for counting entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface BaseCountAllDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>>
        extends ParentReadDomainCrudService<ID, USER, D, DTO, C, R> {

    Logger logger = LoggerFactory.getLogger(BaseCountAllDomainCrudService.class);

    /**
     * Count all domain entities, potentially based on certain criteria.
     *
     * @param user A user associated with the operation.
     * @return A Mono emitting the count of domain entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default @NonNull Long countAll(@NotNull USER user) {
        logger.debug("CountAll, user: {}", user);

        C criteria = newCriteriaClass();

        return doCount(criteria, user);
    }
}
