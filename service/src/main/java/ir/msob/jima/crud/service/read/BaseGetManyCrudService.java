package ir.msob.jima.crud.service.read;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

/**
 * This interface defines a service for retrieving multiple domain entities based on specific criteria or IDs.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for filtering entities.
 * @param <Q>    The type of the query used for filtering entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface BaseGetManyCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>, Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentReadCrudService<ID, USER, D, DTO, C, Q, R> {

    /**
     * The logger for this service class.
     */
    Logger log = LoggerFactory.getLogger(BaseGetManyCrudService.class);

    /**
     * Retrieve multiple DTO entities based on a collection of entity IDs.
     *
     * @param ids  A collection of entity IDs.
     * @param user An optional user associated with the operation.
     * @return A Mono emitting a collection of DTO entities.
     * @throws DomainNotFoundException   If the requested domain is not found.
     * @throws BadRequestException       If the request is not well-formed or violates business rules.
     * @throws InvocationTargetException If an exception occurs during method invocation.
     * @throws NoSuchMethodException     If a requested method is not found.
     * @throws IllegalAccessException    If an illegal access exception occurs.
     * @throws InstantiationException    If an instantiation exception occurs.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<Collection<DTO>> getMany(Collection<ID> ids, Optional<USER> user) throws DomainNotFoundException, BadRequestException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        return this.getMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
    }

    /**
     * Retrieve multiple DTO entities based on specific criteria.
     *
     * @param criteria The criteria used for filtering entities.
     * @param user     An optional user associated with the operation.
     * @return A Mono emitting a collection of DTO entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    @Override
    default Mono<Collection<DTO>> getMany(C criteria, Optional<USER> user) throws DomainNotFoundException, BadRequestException {
        log.debug("GetMany, criteria: {}, user: {}", criteria, user.orElse(null));

        getBeforeAfterComponent().beforeGet(criteria, user, getBeforeAfterDomainOperations());

        return this.preGet(criteria, user)
                .thenMany(this.getManyExecute(criteria, user))
                .collectList()
                .flatMap(domains -> {
                    Collection<DTO> dtos = prepareDtos(domains, user);
                    Collection<ID> ids = prepareIds(domains);
                    return this.postGet(ids, dtos, criteria, user)
                            .doOnSuccess(x -> getBeforeAfterComponent().afterGet(ids, dtos, criteria, user, getBeforeAfterDomainOperations()))
                            .thenReturn(dtos);
                });
    }

    /**
     * Prepare a collection of DTO entities from a collection of domain entities.
     *
     * @param domains A collection of domain entities.
     * @param user    An optional user associated with the operation.
     * @return A collection of DTO entities.
     */
    private Collection<DTO> prepareDtos(Collection<D> domains, Optional<USER> user) {
        return domains
                .stream()
                .map(domain -> toDto(domain, user))
                .toList();
    }

    /**
     * Execute the retrieval of multiple domain entities based on specific criteria.
     *
     * @param criteria The criteria used for filtering entities.
     * @param user     An optional user associated with the operation.
     * @return A Flux emitting a collection of domain entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     */
    default Flux<D> getManyExecute(C criteria, Optional<USER> user) throws DomainNotFoundException {
        Q baseQuery = this.getRepository().generateQuery(criteria);
        baseQuery = this.getRepository().criteria(baseQuery, criteria, user);
        return this.getRepository().getMany(baseQuery);
    }
}