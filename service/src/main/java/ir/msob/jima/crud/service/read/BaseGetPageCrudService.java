package ir.msob.jima.crud.service.read;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * This interface defines a service for retrieving a page of DTO entities based on specific criteria and pagination.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for filtering entities.
 * @param <Q>    The type of the query used for filtering entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface BaseGetPageCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>, Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentReadCrudService<ID, USER, D, DTO, C, Q, R> {

    /**
     * The logger for this service class.
     */
    Logger log = LoggerFactory.getLogger(BaseGetPageCrudService.class);

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
    default Mono<Page<DTO>> getPage(Pageable pageable, USER user) throws DomainNotFoundException, BadRequestException {
        return this.getPage(newCriteriaClass(), pageable, user);
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
    default Mono<Page<DTO>> getPage(C criteria, Pageable pageable, USER user) throws DomainNotFoundException, BadRequestException {
        log.debug("GetPage, criteria: {}, user: {}", criteria, user);

        getBeforeAfterComponent().beforeGet(criteria, user, getBeforeAfterDomainOperations());

        return this.preGet(criteria, user)
                .then(this.getPageExecute(criteria, pageable, user))
                .flatMap(domainPage -> {
                    Page<DTO> dtoPage = preparePage(domainPage, user);
                    Collection<ID> ids = prepareIds(domainPage.getContent());
                    return this.postGet(ids, dtoPage.getContent(), criteria, user)
                            .doOnSuccess(x -> getBeforeAfterComponent().afterGet(ids, dtoPage.getContent(), criteria, user, getBeforeAfterDomainOperations()))
                            .thenReturn(dtoPage);
                });
    }

    /**
     * Prepare a Page of DTO entities from a Page of domain entities and a user.
     *
     * @param domainPage A Page of domain entities.
     * @param user       A user associated with the operation.
     * @return A Page of DTO entities.
     */
    private PageImpl<DTO> preparePage(Page<D> domainPage, USER user) {
        List<DTO> dtos = domainPage
                .stream()
                .map(domain -> toDto(domain, user))
                .toList();

        return new PageImpl<>(dtos, domainPage.getPageable(), domainPage.getTotalElements());
    }

    /**
     * Execute the retrieval of a Page of domain entities based on specific criteria and pagination.
     *
     * @param criteria The criteria used for filtering entities.
     * @param pageable The page information, including page number, size, and sorting.
     * @param user     A user associated with the operation.
     * @return A Mono emitting a Page of domain entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     */
    default Mono<Page<D>> getPageExecute(C criteria, Pageable pageable, USER user) throws DomainNotFoundException {
        Q baseQuery = this.getRepository().generateQuery(criteria, pageable);
        baseQuery = this.getRepository().criteria(baseQuery, criteria, user);
        return this.getRepository().getPage(baseQuery, pageable);
    }
}