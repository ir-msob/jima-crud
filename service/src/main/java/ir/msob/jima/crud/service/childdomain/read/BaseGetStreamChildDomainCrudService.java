package ir.msob.jima.crud.service.childdomain.read;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.filter.Filter;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * This interface defines a service for retrieving multiple domain entities based on specific criteria or IDs.
 *
 * @param <ID>   The type of entity IDs.
 * @param <USER> The type of the user associated with the operations.
 * @param <D>    The type of the domain entities.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities.
 * @param <C>    The type of the criteria used for filtering entities.
 * @param <R>    The type of the CRUD repository used for data access.
 */
public interface BaseGetStreamChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>> extends ParentReadChildDomainCrudService<ID, USER, D, DTO, C, R> {

    /**
     * The logger for this service class.
     */
    Logger logger = LoggerFactory.getLogger(BaseGetStreamChildDomainCrudService.class);

    /**
     * Retrieve multiple DTO entities based on a collection of entity IDs.
     *
     * @param ids  A collection of entity IDs.
     * @param user A user associated with the operation.
     * @return A Flux emitting a collection of DTO entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Flux<@NonNull DTO> getStream(ID parentId, Collection<ID> ids, USER user) throws DomainNotFoundException, BadRequestException {
        return this.doGetStream(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
    }

    /**
     * Retrieve multiple DTO entities based on a collection of entity IDs.
     *
     * <p>This method fetches entities matching the provided IDs and returns them
     * as a {@link Map} where the key is the entity ID and the value is the corresponding DTO.
     * The retrieval is performed reactively and returns a {@link Mono} emitting the resulting map.</p>
     *
     * @param ids  a collection of entity IDs to fetch; must not be null
     * @param user the user performing the operation; used for context or authorization
     * @return a {@link Mono} emitting a {@link Map} of entity IDs to their corresponding DTOs
     * @throws DomainNotFoundException if any of the requested domains are not found
     * @throws BadRequestException     if the request is invalid or violates business rules
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<@NonNull Map<ID, DTO>> getMap(ID parentId, Collection<ID> ids, USER user)
            throws DomainNotFoundException, BadRequestException {
        C criteria = CriteriaUtil.idCriteria(getCriteriaClass(), ids);
        criteria.setParentId(Filter.eq(parentId));
        return this.doGetStream(criteria, user)
                .collectMap(DTO::getId);
    }

    /**
     * Retrieve multiple DTO entities based on specific criteria.
     *
     * <p>This method fetches entities matching the provided criteria and returns them
     * as a {@link Map} where the key is the entity ID and the value is the corresponding DTO.
     * The retrieval is performed reactively and returns a {@link Mono} emitting the resulting map.</p>
     *
     * @param criteria the criteria used to filter entities; must not be null
     * @param user     the user performing the operation; used for context or authorization
     * @return a {@link Mono} emitting a {@link Map} of entity IDs to their corresponding DTOs
     * @throws DomainNotFoundException if no entities match the given criteria
     * @throws BadRequestException     if the request is invalid or violates business rules
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<@NonNull Map<ID, DTO>> getMap(ID parentId, C criteria, USER user)
            throws DomainNotFoundException, BadRequestException {
        criteria.setParentId(Filter.eq(parentId));
        return this.doGetStream(criteria, user)
                .collectMap(DTO::getId);
    }


    /**
     * Retrieve multiple DTO entities based on specific criteria.
     *
     * @param criteria The criteria used for filtering entities.
     * @param user     A user associated with the operation.
     * @return A Flux emitting a collection of DTO entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Flux<@NonNull DTO> getStream(ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        logger.debug("GetStream, criteria: {}, user: {}", criteria, user);

        criteria.setParentId(Filter.eq(parentId));
        return this.doGetStream(criteria, user);
    }


}