package ir.msob.jima.crud.service.write;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.audit.AuditDomain;
import ir.msob.jima.core.commons.model.audit.AuditDomainActionType;
import ir.msob.jima.core.commons.model.audit.BaseAuditDomain;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.ParentCrudService;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * This interface represents a service for performing write operations on entities in a CRUD application.
 *
 * @param <ID>   The type of the entity's ID (must be comparable and serializable).
 * @param <USER> The type of the user.
 * @param <D>    The type of the entity domain.
 * @param <DTO>  The type of the Data Transfer Object (DTO).
 * @param <C>    The type of criteria used for querying.
 * @param <Q>    The type of query.
 * @param <R>    The repository for CRUD operations.
 */
public interface ParentWriteCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>>
        extends ParentCrudService<ID, USER, D, DTO, C, R> {

    /**
     * Get a single DTO by domain.
     *
     * @param domain The domain object to fetch.
     * @param user   An optional user object.
     * @return A Mono that emits the DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If the domain is not found.
     */
    @SneakyThrows
    default Mono<DTO> getOne(D domain, Optional<USER> user) {
        return getOne(CriteriaUtil.idCriteria(getCriteriaClass(), domain.getDomainId()), user);
    }

    /**
     * Get a single DTO by DTO object.
     *
     * @param dto  The DTO object to fetch.
     * @param user An optional user object.
     * @return A Mono that emits the DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If the domain is not found.
     */
    default Mono<DTO> getOneByDto(DTO dto, Optional<USER> user) {
        return getOne(CriteriaUtil.idCriteria(getCriteriaClass(), dto.getDomainId()), user);
    }

    /**
     * Get multiple DTOs by a collection of domain objects.
     *
     * @param domains A collection of domain objects to fetch.
     * @param user    An optional user object.
     * @return A Mono that emits a collection of DTOs.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If a domain is not found.
     */
    @SneakyThrows
    default Mono<Collection<DTO>> getManyByDomain(Collection<D> domains, Optional<USER> user) {
        Collection<ID> ids = prepareIds(domains);
        return this.getMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
    }

    /**
     * Get multiple DTOs by a collection of DTO objects.
     *
     * @param dtos A collection of DTO objects to fetch.
     * @param user An optional user object.
     * @return A Mono that emits a collection of DTOs.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If a domain is not found.
     */
    default Mono<Collection<DTO>> getManyByDto(Collection<DTO> dtos, Optional<USER> user) {
        Collection<ID> ids = dtos
                .stream()
                .map(BaseDomain::getDomainId)
                .toList();
        return this.getMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
    }

    /**
     * Validate entities before saving.
     *
     * @param dtos A collection of DTOs to be saved.
     * @param user An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> saveValidation(Collection<DTO> dtos, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Apply a JSON patch to a collection of DTOs.
     *
     * @param dtos         A collection of DTOs to be patched.
     * @param jsonPatch    The JSON patch to apply.
     * @param objectMapper The ObjectMapper for JSON processing.
     * @return A collection of patched DTOs.
     */
    default Collection<DTO> applyJsonPatch(Collection<DTO> dtos, JsonPatch jsonPatch, ObjectMapper objectMapper) {
        return dtos.stream()
                .map(dto -> applyJsonPatch(jsonPatch, dto, objectMapper))
                .toList();
    }

    /**
     * Apply a JSON patch to a DTO.
     *
     * @param jsonPatch    The JSON patch to apply.
     * @param dto          The DTO to be patched.
     * @param objectMapper The ObjectMapper for JSON processing.
     * @return The patched DTO.
     */
    @SneakyThrows
    default DTO applyJsonPatch(JsonPatch jsonPatch, DTO dto, ObjectMapper objectMapper) {
        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(dto, JsonNode.class));
        return (DTO) objectMapper.treeToValue(patched, dto.getClass());
    }

    /**
     * Perform actions before saving entities.
     *
     * @param dtos A collection of DTOs to be saved.
     * @param user An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> preSave(Collection<DTO> dtos, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Perform actions after saving entities.
     *
     * @param ids  A collection of entity IDs that were saved.
     * @param dtos A collection of DTOs that were saved.
     * @param user An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> postSave(Collection<ID> ids, Collection<DTO> dtos, Collection<D> savedDomains, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Validate entities before updating.
     *
     * @param ids  A collection of entity IDs to be updated.
     * @param dtos A collection of DTOs to be updated.
     * @param user An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> updateValidation(Collection<ID> ids, Collection<DTO> dtos, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Perform actions before updating entities.
     *
     * @param ids  A collection of entity IDs to be updated.
     * @param dtos A collection of DTOs to be updated.
     * @param user An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> preUpdate(Collection<ID> ids, Collection<DTO> dtos, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Perform actions after updating entities.
     *
     * @param ids  A collection of entity IDs that were updated.
     * @param dtos A collection of old DTOs before update.
     * @param user An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> postUpdate(Collection<ID> ids, Collection<DTO> dtos, Collection<D> updatedDomains, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Perform actions before deleting entities based on criteria.
     *
     * @param criteria The criteria used for deleting entities.
     * @param user     An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> preDelete(C criteria, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Perform actions after deleting entities based on criteria.
     *
     * @param ids      A collection of entity IDs that were deleted.
     * @param criteria The criteria used for deleting entities.
     * @param user     An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> postDelete(Collection<ID> ids, C criteria, Optional<USER> user) {
        return Mono.empty();
    }


    /**
     * Prepare a collection of domain objects from a collection of DTOs.
     *
     * @param dtos A collection of DTOs.
     * @param user An optional user object.
     * @return A collection of domain objects.
     */
    default Collection<D> prepareDomain(Collection<DTO> dtos, Optional<USER> user) {
        return dtos
                .stream()
                .map(dto -> toDomain(dto, user))
                .toList();
    }

    /**
     * Add audit information to a collection of DTOs.
     *
     * @param dtos                  A collection of DTOs to be audited.
     * @param auditDomainActionType The type of audit action to record.
     * @param user                  An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> addAudit(Collection<DTO> dtos, AuditDomainActionType auditDomainActionType, Optional<USER> user) {
        if (user.isEmpty()) {
            return Mono.empty();
        }

        Instant now = Instant.now();
        List<BaseAuditDomain<ID>> auditDomainDtos = dtos.stream()
                .filter(dto -> dto instanceof BaseAuditDomain<?>)
                .map(dto -> (BaseAuditDomain<ID>) dto)
                .toList();

        auditDomainDtos.forEach(auditDomainDto -> auditDomainDto.getAuditDomains()
                .add(AuditDomain.<ID>builder()
                        .actionDate(now)
                        .actionType(auditDomainActionType)
                        .relatedPartyId(user.get().getId())
                        .build()
                ));

        return Mono.empty();
    }

}