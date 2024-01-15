package ir.msob.jima.crud.service.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.validation.ValidationException;
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
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Optional;

/**
 * This service interface defines the contract for executing batch update operations on multiple entities based on JSON patch documents.
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
public interface BaseEditManyCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, D extends BaseDomain<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>, Q extends BaseQuery, R extends BaseCrudRepository<ID, USER, D, C, Q>> extends ParentWriteCrudService<ID, USER, D, DTO, C, Q, R> {
    Logger log = LoggerFactory.getLogger(BaseEditManyCrudService.class);

    /**
     * Executes a batch update operation on multiple entities based on a JSON patch and a collection of entity IDs.
     *
     * @param jsonPatch The JSON patch document to apply to entities.
     * @param ids       The collection of entity IDs to identify the entities to be updated.
     * @param user      An optional user associated with the operation.
     * @return A collection of DTOs representing the updated entities.
     * @throws BadRequestException       if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException   if the entities to be updated are not found.
     * @throws InvocationTargetException if an error occurs during invocation.
     * @throws NoSuchMethodException     if a required method is not found.
     * @throws IllegalAccessException    if an illegal access operation occurs.
     * @throws InstantiationException    if an instance of a class cannot be created.
     */
    @Transactional
    @MethodStats
    default Mono<Collection<DTO>> editMany(Collection<ID> ids, JsonPatch jsonPatch, Optional<USER> user) throws BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        return this.editMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), jsonPatch, user);
    }

    /**
     * Executes a batch update operation on multiple entities based on a JSON patch and specified criteria.
     *
     * @param jsonPatch The JSON patch document to apply to entities.
     * @param criteria  The criteria used for filtering entities to be updated.
     * @param user      An optional user associated with the operation.
     * @return A collection of DTOs representing the updated entities.
     * @throws BadRequestException     if the operation encounters a bad request scenario.
     * @throws DomainNotFoundException if the entities to be updated are not found.
     */
    @Transactional
    @MethodStats
    default Mono<Collection<DTO>> editMany(C criteria, JsonPatch jsonPatch, Optional<USER> user) throws BadRequestException, DomainNotFoundException {
        log.debug("EditMany, jsonPatch: {}, criteria: {}, user {}", jsonPatch, criteria, user.orElse(null));

        return getMany(criteria, user).flatMap(dtos -> {
            Collection<DTO> previousDtos = dtos.stream()
                    .map(SerializationUtils::clone)
                    .toList();
            try {
                return updateMany(previousDtos, applyJsonPatch(dtos, jsonPatch, getObjectMapper()), user);
            } catch (BadRequestException | ValidationException | DomainNotFoundException e) {
                return Mono.error(e);
            }
        });
    }
}
