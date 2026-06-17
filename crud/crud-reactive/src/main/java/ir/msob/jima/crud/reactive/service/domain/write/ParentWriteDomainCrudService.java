package ir.msob.jima.crud.reactive.service.domain.write;

import ir.msob.jima.crud.reactive.service.domain.ParentDomainCrudReactiveService;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import org.jspecify.annotations.NonNull;
import reactor.core.publisher.Mono;

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
        R extends BaseReactiveRepository<ID, D, C>>
        extends ParentDomainCrudReactiveService<ID, USER, D, DTO, C, R> {

    /**
     * Fetches a single DTO by its ID.
     *
     * @param id   The ID of the DTO to fetch.
     * @param user A user object.
     * @return A Mono that emits the DTO.
     * @throws BadRequestException     If the request is malformed.
     * @throws DomainNotFoundException If the domain is not found.
     */
    default Mono<@NonNull DTO> getOneById(ID id, USER user) {
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
    default Mono<@NonNull Collection<DTO>> getManyByDto(Collection<DTO> dtos, USER user) {
        Collection<ID> ids = dtos
                .stream()
                .map(BaseDomain::getId)
                .toList();
        return this.doGetMany(CriteriaUtil.idCriteria(getCriteriaClass(), ids), user);
    }

}