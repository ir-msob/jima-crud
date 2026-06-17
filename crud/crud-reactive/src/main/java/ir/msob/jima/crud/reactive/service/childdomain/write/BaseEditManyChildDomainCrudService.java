package ir.msob.jima.crud.reactive.service.childdomain.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

/**
 * This service interface defines the contract for executing batch update operations on multiple entities based on JSON patch documents.
 * It extends the {@link ParentWriteChildDomainCrudService} and is designed for use with CRUD operations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user associated with the operation.
 * @param <D>    The type of the entity (domain) to be updated.
 * @param <DTO>  The type of data transfer object that represents the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 */
public interface BaseEditManyChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser, D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>, C extends BaseChildCriteria<ID>, R extends BaseReactiveRepository<ID, D, C>> extends ParentWriteChildDomainCrudService<ID, USER, D, DTO, C, R> {
    Logger logger = LoggerFactory.getLogger(BaseEditManyChildDomainCrudService.class);

    /**
     * Executes a batch update operation on multiple entities based on a JSON patch and a collection of entity IDs.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param jsonPatch The JSON patch document to apply to entities.
     * @param ids       The collection of entity IDs to identify the entities to be updated.
     * @param user      A user associated with the operation.
     * @return A collection of DTOs representing the updated entities.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entities to be updated are not found.
     */
    @Transactional
    @MethodStats
    default Mono<@NonNull Collection<DTO>> editMany(ID parentId, Collection<ID> ids, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        return this.doEditMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), jsonPatch, user);
    }

    /**
     * Executes a batch update operation on multiple entities based on a JSON patch and specified criteria.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param jsonPatch The JSON patch document to apply to entities.
     * @param criteria  The criteria used for filtering entities to be updated.
     * @param user      A user associated with the operation.
     * @return A collection of DTOs representing the updated entities.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entities to be updated are not found.
     */
    @Transactional
    @MethodStats
    default Mono<@NonNull Collection<DTO>> editMany(ID parentId, C criteria, JsonPatch jsonPatch, USER user) throws BadRequestException, DomainNotFoundException {
        logger.debug("EditMany, jsonPatch: {}, criteria: {}, user {}", jsonPatch, criteria, user);

        return this.doEditMany(criteria, jsonPatch, user);
    }


}