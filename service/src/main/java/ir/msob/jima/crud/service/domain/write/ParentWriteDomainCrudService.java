package ir.msob.jima.crud.service.domain.write;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.childdomain.ChildDomainUtil;
import ir.msob.jima.core.commons.childdomain.auditdomain.AuditDomainAbstract;
import ir.msob.jima.core.commons.childdomain.auditdomain.AuditDomainActionType;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.validation.ValidationException;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.ParentDomainCrudService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;

/**
 * This interface represents a service for performing write operations on entities in a CRUD application.
 *
 * @param <ID>   The type of the entity's ID (must be comparable and serializable).
 * @param <USER> The type of the user.
 * @param <D>    The type of the entity domain.
 * @param <DTO>  The type of the Data Transfer Object (DTO).
 * @param <C>    The type of criteria used for querying.
 * @param <R>    The repository for CRUD operations.
 */
public interface ParentWriteDomainCrudService<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>>
        extends ParentDomainCrudService<ID, USER, D, DTO, C, R> {

    /**
     * Fetches a single DTO by its ID.
     *
     * @param id   The ID of the DTO to fetch.
     * @param user A user object.
     * @return A Mono that emits the DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If the domain is not found.
     */
    default Mono<DTO> getOneById(ID id, USER user) {
        return getOne(CriteriaUtil.idCriteria(getCriteriaClass(), id), user);
    }

    /**
     * Fetches multiple DTOs by a collection of DTO objects.
     *
     * @param dtos A collection of DTO objects to fetch.
     * @param user A user object.
     * @return A Mono that emits a collection of DTOs.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If a domain is not found.
     */
    default Mono<Collection<DTO>> getManyByDto(Collection<DTO> dtos, USER user) {
        Collection<ID> ids = dtos
                .stream()
                .map(BaseDomain::getId)
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
     * Saves a DTO.
     *
     * @param dto  The DTO to be saved.
     * @param user A user object.
     * @return A Mono that emits the saved DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If the domain is not found.
     */
    default Mono<DTO> save(@Valid DTO dto, USER user) throws BadRequestException, DomainNotFoundException {
        return Mono.empty();
    }

    /**
     * Updates a DTO.
     *
     * @param previousDto The previous DTO before update.
     * @param dto         The DTO to be updated.
     * @param user        A user object.
     * @return A Mono that emits the updated DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws ValidationException     If the DTO is not valid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    @Override
    default Mono<DTO> update(DTO previousDto, @Valid DTO dto, USER user) throws BadRequestException, ValidationException, DomainNotFoundException {
        return Mono.empty();
    }


    /**
     * Adds audit information to a DTO.
     *
     * @param dto        The DTO to be audited.
     * @param actionType The type of audit action to record.
     * @param user       A user object.
     */
    default void addAudit(DTO dto, AuditDomainActionType actionType, USER user) {

        RelatedPartyAbstract<ID> relatedParty = new RelatedPartyAbstract<>() {
        };
        relatedParty.setRelatedId(user.getId());
        relatedParty.setName(user.getName());

        AuditDomainAbstract<ID, RelatedPartyAbstract<ID>> auditDomain = new AuditDomainAbstract<>() {
        };
        auditDomain.setActionDate(Instant.now());
        auditDomain.setActionType(actionType.name());
        auditDomain.setRelatedParty(relatedParty);

        if (ChildDomainUtil.hasFunction((Class<AuditDomainAbstract<ID, RelatedPartyAbstract<ID>>>) auditDomain.getClass(), getDtoClass())) {
            ChildDomainUtil.getFunction((Class<AuditDomainAbstract<ID, RelatedPartyAbstract<ID>>>) auditDomain.getClass(), getDtoClass()).apply(dto).add(auditDomain);
        }

    }

}