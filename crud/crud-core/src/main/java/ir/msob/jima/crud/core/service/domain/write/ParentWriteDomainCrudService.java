package ir.msob.jima.crud.core.service.domain.write;

import ir.msob.jima.crud.api.service.BaseCrudService;
import ir.msob.jima.crud.core.service.domain.ParentDomainCrudService;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import org.jspecify.annotations.NonNull;

import java.io.Serializable;
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
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>>
        extends ParentDomainCrudService<ID, USER, D, DTO, C, R>,
        BaseCrudService<ID, USER, D, DTO, C, R> {

    /**
     * Fetches a single DTO by its ID.
     *
     * @param id   The ID of the DTO to fetch.
     * @param user A user object.
     * @return A Mono that emits the DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If the domain is not found.
     */
    default DTO getOneById(ID id, USER user) {
        return doGetOne(CriteriaUtil.idCriteria(getCriteriaClass(), id), user);
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
    default @NonNull Collection<DTO> getManyByDto(Collection<DTO> dtos, USER user) {
        Collection<ID> ids = dtos
                .stream()
                .map(BaseDomain::getId)
                .toList();
        return this.doGetMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
    }

}