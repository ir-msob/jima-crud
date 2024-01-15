package ir.msob.jima.crud.service.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.validation.ValidationException;
import ir.msob.jima.core.commons.model.audit.AuditDomainActionType;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * This service interface defines the contract for executing update operations for multiple entities (DTOs).
 * It extends the {@link ParentWriteCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be updated.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <Q>    The type of query used for database operations.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseUpdateManyCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseUpdateManyCrudService.class);

    /**
     * Updates multiple entities based on a collection of DTOs. Validates, prepares, and updates the entities.
     *
     * @param dtos The collection of DTOs to be updated.
     * @param user An optional user associated with the operation.
     * @return A collection of DTOs representing the updated entities.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if an entity to be updated is not found.
     */
    @Override
    @Transactional
    @MethodStats
    default Mono<Collection<DTO>> updateMany(Collection<@Valid DTO> dtos, Optional<USER> user) {
        return getManyByDto(dtos, user)
                .flatMap(previousDtos -> this.updateMany(previousDtos, dtos, user));
    }

    /**
     * Updates multiple entities based on collections of previous DTOs and new DTOs. Validates, prepares, and updates the entities.
     *
     * @param previousDtos The collection of previous DTOs representing existing entities.
     * @param dtos         The collection of new DTOs to be used for updates.
     * @param user         An optional user associated with the operation.
     * @return A collection of DTOs representing the updated entities.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws ValidationException     if validation fails during the update.
     * @throws DomainNotFoundException if an entity to be updated is not found.
     */
    @Override
    @Transactional
    @MethodStats
    default Mono<Collection<DTO>> updateMany(Collection<DTO> previousDtos, Collection<@Valid DTO> dtos, Optional<USER> user) {
        log.debug("UpdateMany, dto.size {}, user {}", dtos.size(), user.orElse(null));

        Collection<D> domains = prepareDomain(dtos, user);
        Collection<ID> ids = prepareIds(domains);
        getBeforeAfterComponent().beforeUpdate(ids, previousDtos, dtos, user);

        return this.updateValidation(ids, dtos, domains, user)
                .then(this.preUpdate(ids, dtos, domains, user))
                .then(addAudit(dtos, AuditDomainActionType.UPDATE, user))
                .thenMany(this.updateManyExecute(dtos, domains, user))
                .collectList()
                .doOnSuccess(updatedDomains -> this.postUpdate(ids, dtos, domains, updatedDomains, user))
                .flatMap(updatedDomains -> getManyByDomain(updatedDomains, user))
                .doOnSuccess(updatedDtos -> afterUpdate(ids, previousDtos, updatedDtos, user));
    }

    /**
     * Executes the actual update operation for multiple entities using a collection of DTOs and domains.
     *
     * @param dtos    The collection of new DTOs to be used for updates.
     * @param domains The collection of domain entities to be updated.
     * @param user    An optional user associated with the operation.
     * @return A Flux of updated domain entities.
     * @throws DomainNotFoundException if an entity to be updated is not found.
     */
    default Flux<D> updateManyExecute(Collection<DTO> dtos, Collection<D> domains, Optional<USER> user) {
        return this.getRepository().updateMany(domains);
    }
}
