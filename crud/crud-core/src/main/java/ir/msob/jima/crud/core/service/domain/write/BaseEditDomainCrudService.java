package ir.msob.jima.crud.core.service.domain.write;

import com.github.fge.jsonpatch.JsonPatch;
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
import ir.msob.jima.platform.api.util.CriteriaUtil;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * This service interface defines the contract for executing update operations on a single entity using JSON patches.
 * It extends the {@link ParentWriteDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be updated.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseEditDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseDomain<ID>, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>, R extends BaseRepository<ID, D, C>> extends ParentWriteDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseEditDomainCrudService.class);

    /**
     * Executes an update operation on a single entity based on a JSON patch and an entity ID.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param jsonPatch The JSON patch document to apply to the entity.
     * @param id        The entity ID to identify the entity to be updated.
     * @param user      A user associated with the operation.
     * @return A Mono of a DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Transactional
    @MethodStats
    default DTO edit(ID id, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        return this.doEdit(CriteriaUtil.idCriteria(getCriteriaClass(), id), jsonPatch, user);
    }

    /**
     * Executes an update operation on a single entity based on a JSON patch and specified criteria.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param jsonPatch The JSON patch document to apply to the entity.
     * @param criteria  The criteria used for filtering the entity to be updated.
     * @param user      A user associated with the operation.
     * @return A Mono of a DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Transactional
    @MethodStats
    default DTO edit(C criteria, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        logger.debug("Edit, jsonPatch: {}, criteria: {}, user {}", jsonPatch, criteria, user);

        return this.doEdit(criteria, jsonPatch, user);
    }

}