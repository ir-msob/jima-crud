package ir.msob.jima.crud.service.domain.read;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * This interface defines a service for retrieving a single DTO entity based on specific criteria or an entity ID.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for filtering entities.
 * @param <Q>    The type of the query used for filtering entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface BaseGetOneDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>, Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>> extends ParentReadDomainCrudService<ID, USER, D, DTO, C, Q, R> {

    Logger log = LoggerFactory.getLogger(BaseGetOneDomainCrudService.class);

    /**
     * Retrieve a single DTO entity based on an entity ID.
     *
     * @param id   The ID of the entity to retrieve.
     * @param user A user associated with the operation.
     * @return A Mono emitting a single DTO entity.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<DTO> getOne(ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return this.getOne(CriteriaUtil.idCriteria(getCriteriaClass(), id), user);
    }

    /**
     * Retrieve a single DTO entity based on specific criteria.
     *
     * @param criteria The criteria used for filtering entities.
     * @param user     A user associated with the operation.
     * @return A Mono emitting a single DTO entity.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    @Override
    default Mono<DTO> getOne(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        log.debug("GetOne, criteria: {}, user: {}", criteria, user);

        getBeforeAfterComponent().beforeGet(criteria, user, getBeforeAfterDomainOperations());

        Q baseQuery = this.getRepository().generateQuery(criteria);
        baseQuery = this.getRepository().criteria(baseQuery, criteria, user);

        return this.preGet(criteria, user)
                .then(this.getRepository().getOne(baseQuery))
                .flatMap(domain -> {
                    DTO dto = toDto(domain, user);
                    Collection<ID> ids = Collections.singletonList(domain.getId());
                    Collection<DTO> dtos = Collections.singletonList(dto);

                    return this.postGet(ids, dtos, criteria, user)
                            .doOnSuccess(x -> getBeforeAfterComponent().afterGet(ids, dtos, criteria, user, getBeforeAfterDomainOperations()))
                            .thenReturn(dto);
                });
    }

}