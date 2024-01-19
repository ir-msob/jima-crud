package ir.msob.jima.crud.service.write;

import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
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
 * This service interface defines the contract for executing batch save operations for a collection of entities.
 * It extends the {@link ParentWriteCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be saved.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <Q>    The type of query used for database operations.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseSaveManyCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseSaveManyCrudService.class);

    /**
     * Executes a batch save operation for a collection of entities. Validates, prepares, and saves the entities.
     *
     * @param dtos The collection of DTOs to be saved.
     * @param user An optional user associated with the operation.
     * @return A collection of DTOs representing the saved entities.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entities to be saved are not found.
     */
    @Transactional
    @MethodStats
    default Mono<Collection<DTO>> saveMany(Collection<@Valid DTO> dtos, Optional<USER> user) throws BadRequestException, DomainNotFoundException {
        log.debug("SaveMany, dtos.size: {}, user {}", dtos.size(), user.orElse(null));

        Collection<D> domains = prepareDomain(dtos, user);
        getBeforeAfterComponent().beforeSave(dtos, user, getBeforeAfterDomainServices());

        return this.saveValidation(dtos, user)
                .then(this.preSave(dtos, user))
                .then(addAudit(dtos, AuditDomainActionType.CREATE, user))
                .thenMany(this.saveManyExecute(dtos, domains, user))
                .collectList()
                .doOnSuccess(savedDomains -> this.postSave(prepareIds(savedDomains), dtos, savedDomains, user))
                .flatMap(savedDtos -> getManyByDomain(savedDtos, user))
                .doOnSuccess(savedDtos -> getBeforeAfterComponent().afterSave(prepareIds(savedDtos), dtos, savedDtos, user, getBeforeAfterDomainServices()));
    }

    /**
     * Executes the actual batch save operation, saving a collection of entities.
     *
     * @param dtos    The collection of DTOs to be saved.
     * @param domains The collection of domain entities to be saved.
     * @param user    An optional user associated with the operation.
     * @return A Flux of saved domain entities.
     */
    default Flux<D> saveManyExecute(Collection<DTO> dtos, Collection<D> domains, Optional<USER> user) {
        return this.getRepository().insertMany(domains);
    }
}
