package ir.msob.jima.crud.service.write;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.validation.ValidationException;
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
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
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
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>>
        extends ParentCrudService<ID, USER, D, DTO, C, R> {

    /**
     * Fetches a single DTO by its ID.
     *
     * @param id   The ID of the DTO to fetch.
     * @param user An optional user object.
     * @return A Mono that emits the DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If the domain is not found.
     */
    default Mono<DTO> getOneByID(ID id, Optional<USER> user) {
        return getOne(CriteriaUtil.idCriteria(getCriteriaClass(), id), user);
    }

    /**
     * Fetches multiple DTOs by a collection of DTO objects.
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
     * Applies a JSON patch to a collection of DTOs.
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
     * Applies a JSON patch to a DTO.
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
     * Performs actions before saving entities.
     *
     * @param dto  The DTO to be saved.
     * @param user An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> preSave(DTO dto, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Performs actions after saving entities.
     *
     * @param dto        The DTO that was saved.
     * @param savedDomain The saved domain.
     * @param user        An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> postSave(DTO dto, D savedDomain, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Saves a DTO.
     *
     * @param dto  The DTO to be saved.
     * @param user An optional user object.
     * @return A Mono that emits the saved DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If the domain is not found.
     */
    default Mono<DTO> save(@Valid DTO dto, Optional<USER> user) throws BadRequestException, DomainNotFoundException {
        return Mono.empty();
    }

    /**
     * Updates a DTO.
     *
     * @param previousDto The previous DTO before update.
     * @param dto         The DTO to be updated.
     * @param user        An optional user object.
     * @return A Mono that emits the updated DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws ValidationException     If the DTO is not valid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    @Override
    default Mono<DTO> update(DTO previousDto, @Valid DTO dto, Optional<USER> user) throws BadRequestException, ValidationException, DomainNotFoundException {
        return Mono.empty();
    }

    /**
     * Performs actions before updating entities.
     *
     * @param dto  The DTO to be updated.
     * @param user An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> preUpdate(DTO dto, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Performs actions after updating entities.
     *
     * @param dto          The DTO that was updated.
     * @param updatedDomain The updated domain.
     * @param user         An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> postUpdate(DTO dto, D updatedDomain, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Performs actions before deleting entities based on criteria.
     *
     * @param criteria The criteria used for deleting entities.
     * @param user     An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> preDelete(C criteria, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Performs actions after deleting entities based on criteria.
     *
     * @param dto       The DTO of the entity that was deleted.
     * @param criteria The criteria used for deleting entities.
     * @param user     An optional user object.
     * @return A Mono that emits void.
     */
    default Mono<Void> postDelete(DTO dto, C criteria, Optional<USER> user) {
        return Mono.empty();
    }

    /**
     * Adds audit information to a DTO.
     *
     * @param dto                  The DTO to be audited.
     * @param actionType           The type of audit action to record.
     * @param user                 An optional user object.
     */
    default void addAudit(DTO dto, AuditDomainActionType actionType, Optional<USER> user) {
        user.ifPresent(u -> {
            if (dto instanceof BaseAuditDomain auditDomainDto) {
                auditDomainDto.getAuditDomains().add(AuditDomain.<ID>builder()
                        .actionDate(Instant.now())
                        .actionType(actionType)
                        .relatedPartyId(String.valueOf(u.getId()))
                        .build());
            }
        });
    }

}