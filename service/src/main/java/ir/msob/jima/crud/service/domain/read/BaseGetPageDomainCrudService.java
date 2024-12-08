package ir.msob.jima.crud.service.domain.read;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
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
public interface BaseGetPageDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>, Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>> extends ParentReadDomainCrudService<ID, USER, D, DTO, C, Q, R> {

    /**
     * The logger for this service class.
     */
    Logger log = LoggerFactory.getLogger(BaseGetPageDomainCrudService.class);

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
        Q baseQuery = this.getRepository().generateQuery(criteria, pageable);
        baseQuery = this.getRepository().criteria(baseQuery, criteria, user);

        return this.preGet(criteria, user)
                .then(this.getRepository().getPage(baseQuery, pageable))
                .map(domainPage -> {
                    List<DTO> dtos = domainPage
                            .stream()
                            .map(domain -> toDto(domain, user))
                            .toList();

                    return new PageImpl<>(dtos, domainPage.getPageable(), domainPage.getTotalElements());
                })
                .flatMap(dtoPage -> {
                    Collection<ID> ids = prepareIds(dtoPage.getContent());
                    return this.postGet(ids, dtoPage.getContent(), criteria, user)
                            .doOnSuccess(x -> getBeforeAfterComponent().afterGet(ids, dtoPage.getContent(), criteria, user, getBeforeAfterDomainOperations()))
                            .thenReturn(dtoPage);
                });
    }

}