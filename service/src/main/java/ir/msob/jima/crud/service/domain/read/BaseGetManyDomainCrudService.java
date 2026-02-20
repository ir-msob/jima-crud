package ir.msob.jima.crud.service.domain.read;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

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
public interface BaseGetManyDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>> extends ParentReadDomainCrudService<ID, USER, D, DTO, C, R> {

    /**
     * The logger for this service class.
     */
    Logger logger = LoggerFactory.getLogger(BaseGetManyDomainCrudService.class);

    /**
     * Retrieve multiple DTO entities based on a collection of entity IDs.
     *
     * @param ids  A collection of entity IDs.
     * @param user A user associated with the operation.
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
    default Mono<@NonNull Collection<DTO>> getMany(Collection<ID> ids, USER user) throws DomainNotFoundException, BadRequestException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        return this.doGetMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
    }

    /**
     * Retrieve multiple DTO entities based on specific criteria.
     *
     * @param criteria The criteria used for filtering entities.
     * @param user     A user associated with the operation.
     * @return A Mono emitting a collection of DTO entities.
     * @throws DomainNotFoundException If the requested domain is not found.
     * @throws BadRequestException     If the request is not well-formed or violates business rules.
     */
    @Transactional(readOnly = true)
    @MethodStats
    default Mono<@NonNull Collection<DTO>> getMany(C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        logger.debug("GetMany, criteria: {}, user: {}", criteria, user);

        return this.doGetMany(criteria, user);
    }

}