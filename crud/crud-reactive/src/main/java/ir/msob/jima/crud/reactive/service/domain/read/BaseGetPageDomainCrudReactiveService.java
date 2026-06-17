package ir.msob.jima.crud.reactive.service.domain.read;

import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;

/**
 * This interface defines a service for retrieving a page of DTO entities based on specific criteria and pagination.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for filtering entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface BaseGetPageDomainCrudReactiveService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>> extends ParentReadDomainCrudService<ID, USER, D, DTO, C, R> {

    /**
     * The logger for this service class.
     */
    Logger logger = LoggerFactory.getLogger(BaseGetPageDomainCrudReactiveService.class);

    /**
     * Retrieve a page of DTO entities based on specific criteria and pagination.
     *
     * @param pageable The page information, including page number, size, and sorting.
     * @param user     A user associated with the operation.
     * @return A Mono emitting a Page of DTO entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<@NonNull Page<@NonNull DTO>> getPage(Pageable pageable, USER user) throws DomainNotFoundException, BadRequestException {
        return this.doGetPage(newCriteriaClass(), pageable, user);
    }

    /**
     * Retrieve a page of DTO entities based on specific criteria and pagination.
     *
     * @param criteria The criteria used for filtering entities.
     * @param pageable The page information, including page number, size, and sorting.
     * @param user     A user associated with the operation.
     * @return A Mono emitting a Page of DTO entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<@NonNull Page<@NonNull DTO>> getPage(C criteria, Pageable pageable, USER user) throws DomainNotFoundException, BadRequestException {
        logger.debug("GetPage, criteria: {}, user: {}", criteria, user);
        return this.doGetPage(criteria, pageable, user);
    }
}