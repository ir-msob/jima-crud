package ir.msob.jima.crud.service.write;

import com.github.fge.jsonpatch.JsonPatch;
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
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Optional;

/**
 * This service interface defines the contract for executing update operations on a single entity using JSON patches.
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
public interface BaseEditCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseEditCrudService.class);

    /**
     * Executes an update operation on a single entity based on a JSON patch and an entity ID.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param jsonPatch The JSON patch document to apply to the entity.
     * @param id        The entity ID to identify the entity to be updated.
     * @param user      An optional user associated with the operation.
     * @return A Mono of a DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Transactional
    @MethodStats
    default Mono<DTO> edit(ID id, JsonPatch jsonPatch, Optional<USER> user) throws BadRequestException, DomainNotFoundException {
        return this.edit(CriteriaUtil.idCriteria(getCriteriaClass(), id), jsonPatch, user);
    }

    /**
     * Executes an update operation on a single entity based on a JSON patch and specified criteria.
     * This method is transactional and is also annotated with @MethodStats for performance monitoring.
     *
     * @param jsonPatch The JSON patch document to apply to the entity.
     * @param criteria  The criteria used for filtering the entity to be updated.
     * @param user      An optional user associated with the operation.
     * @return A Mono of a DTO representing the updated entity.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entity to be updated is not found.
     */
    @Transactional
    @MethodStats
    default Mono<DTO> edit(C criteria, JsonPatch jsonPatch, Optional<USER> user) throws BadRequestException, DomainNotFoundException {
        log.debug("Edit, jsonPatch: {}, criteria: {}, user {}", jsonPatch, criteria, user.orElse(null));

        return getOne(criteria, user).flatMap(dto -> {
            DTO previousDto = SerializationUtils.clone(dto);
            return update(previousDto, applyJsonPatch(jsonPatch, dto, getObjectMapper()), user);
        });
    }
}