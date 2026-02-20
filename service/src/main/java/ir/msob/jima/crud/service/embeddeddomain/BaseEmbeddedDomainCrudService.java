package ir.msob.jima.crud.service.embeddeddomain;

import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.core.commons.embeddeddomain.EmbeddedDomainUtil;
import ir.msob.jima.core.commons.embeddeddomain.criteria.BaseEmbeddedCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.datanotfound.DataNotFoundException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.filter.Filter;
import ir.msob.jima.core.commons.id.BaseIdService;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.core.commons.util.Strings;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.jspecify.annotations.NonNull;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

/**
 * Base interface for CRUD operations on embedded domain entities.
 *
 * @param <ID>   The type of the identifier.
 * @param <USER> The type of the user context.
 * @param <DTO>  The type of the Data Transfer Object.
 */
public interface BaseEmbeddedDomainCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>> {

    /**
     * Get the class of the parent DTO.
     *
     * @return The class of the parent DTO.
     */
    default Class<DTO> getParentDtoClass() {
        return (Class<DTO>) GenericTypeUtil.resolveTypeArguments(this.getClass(), BaseEmbeddedDomainCrudService.class, 2);
    }

    /**
     * Get one DTO entity based on criteria.
     *
     * @param id   The id of entity.
     * @param user A user context.
     * @return A Mono that emits the found DTO entity.
     */
    Mono<@NonNull DTO> getDto(ID id, USER user);

    /**
     * Update a DTO entity.
     *
     * @param id   The id of entity.
     * @param dto  The DTO entity to be updated.
     * @param user A user context.
     * @return A Mono that emits the updated DTO entity.
     */
    Mono<@NonNull DTO> updateDto(ID id, @Valid DTO dto, USER user);

    /**
     * Update a embedded domain entity by id.
     *
     * @param parentId       The id of the parent entity.
     * @param id             The id of the embedded domain entity.
     * @param embeddedDomain The embedded domain entity to be updated.
     * @param cdClass        The class of the embedded domain entity.
     * @param user           A user context.
     * @return A Mono that emits the updated DTO entity.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    @SneakyThrows
    @MethodStats
    default <ED extends BaseEmbeddedDomain<ID>, EC extends BaseEmbeddedCriteria<ID, ED>> Mono<@NonNull DTO> updateById(@NotNull ID parentId, @NotNull ID id, ED embeddedDomain, Class<ED> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        EC criteria = (EC) EmbeddedDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return this.update(parentId, embeddedDomain, criteria, cdClass, user);
    }

    /**
     * Update a embedded domain entity based on criteria.
     *
     * @param parentId         The id of the parent entity.
     * @param embeddedDomain   The embedded domain entity to be updated.
     * @param embeddedCriteria The criteria to match the embedded domain entity.
     * @param cdClass          The class of the embedded domain entity.
     * @param user             A user context.
     * @return A Mono that emits the updated DTO entity.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    @MethodStats
    default <ED extends BaseEmbeddedDomain<ID>, EC extends BaseEmbeddedCriteria<ID, ED>> Mono<@NonNull DTO> update(@NotNull ID parentId, ED embeddedDomain, @NotNull EC embeddedCriteria, Class<ED> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {

                    Iterator<ED> iterator = EmbeddedDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).iterator();
                    boolean found = false;

                    while (iterator.hasNext()) {
                        ED next = iterator.next();
                        if (embeddedCriteria.isMatching(next)) {
                            embeddedDomain.setId(next.getId());
                            EmbeddedDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).add(embeddedDomain);
                            iterator.remove();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        throw new DataNotFoundException("No embeddeddomain model found with Criteria in the parent DTO of type " + dto.getClass().getSimpleName(), embeddedDomain.getClass().getSimpleName(), embeddedCriteria.toString());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    /**
     * Update multiple embedded domain entities.
     *
     * @param parentId        The id of the parent entity.
     * @param embeddedDomains The collection of embedded domain entities to be updated.
     * @param cdClass         The class of the embedded domain entities.
     * @param user            A user context.
     * @return A Mono that emits the updated DTO entity.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    @MethodStats
    default <ED extends BaseEmbeddedDomain<ID>> Mono<@NonNull DTO> updateMany(@NotNull ID parentId, Collection<ED> embeddedDomains, Class<ED> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {
                    Iterator<ED> iterator = EmbeddedDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).iterator();

                    for (ED cd : embeddedDomains) {
                        boolean found = false;
                        while (iterator.hasNext()) {
                            ED next = iterator.next();
                            if (Objects.equals(cd.getId(), next.getId())) {
                                EmbeddedDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).add(cd);
                                iterator.remove();
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            throw new DataNotFoundException("No embeddeddomain model found with Criteria in the parent DTO of type " + dto.getClass().getSimpleName());
                        }
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    /**
     * Delete a embedded domain entity by id.
     *
     * @param parentId The id of the parent entity.
     * @param id       The id of the embedded domain entity.
     * @param cdClass  The class of the embedded domain entity.
     * @param user     A user context.
     * @return A Mono that emits the updated DTO entity.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    @SneakyThrows
    @MethodStats
    default <ED extends BaseEmbeddedDomain<ID>, EC extends BaseEmbeddedCriteria<ID, ED>> Mono<@NonNull DTO> deleteById(@NotNull ID parentId, @NotNull ID id, Class<ED> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        EC criteria = (EC) EmbeddedDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return delete(parentId, criteria, cdClass, user);
    }

    /**
     * Delete a embedded domain entity based on criteria.
     *
     * @param parentId         The id of the parent entity.
     * @param embeddedCriteria The criteria to match the embedded domain entity.
     * @param cdClass          The class of the embedded domain entity.
     * @param user             A user context.
     * @return A Mono that emits the updated DTO entity.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    @MethodStats
    default <ED extends BaseEmbeddedDomain<ID>, EC extends BaseEmbeddedCriteria<ID, ED>> Mono<@NonNull DTO> delete(@NotNull ID parentId, @NotNull EC embeddedCriteria, Class<ED> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {

                    Iterator<ED> iterator = EmbeddedDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).iterator();
                    boolean found = false;

                    while (iterator.hasNext()) {
                        ED next = iterator.next();

                        if (embeddedCriteria.isMatching(next)) {
                            iterator.remove();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        throw new DataNotFoundException("No embeddeddomain object found with Criteria in the parent DTO of type ");
                    }

                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    /**
     * Deletes multiple embedded domain entities based on criteria.
     *
     * @param parentId         The id of the parent entity.
     * @param embeddedCriteria The criteria to match the embedded domain entities.
     * @param cdClass          The class of the embedded domain entities.
     * @param user             A user context.
     * @return A Mono that emits the updated DTO entity.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    @MethodStats
    default <ED extends BaseEmbeddedDomain<ID>, EC extends BaseEmbeddedCriteria<ID, ED>> Mono<@NonNull DTO> deleteMany(@NotNull ID parentId, @NotNull EC embeddedCriteria, Class<ED> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {
                    Iterator<ED> iterator = EmbeddedDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).iterator();
                    boolean found = false;

                    while (iterator.hasNext()) {
                        ED next = iterator.next();
                        if (embeddedCriteria.isMatching(next)) {
                            iterator.remove();
                            found = true;
                        }
                    }

                    if (!found) {
                        throw new DataNotFoundException("No embeddeddomain object found with Criteria in the parent DTO of type ");
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    /**
     * Saves a single embedded domain entity.
     *
     * @param parentId       The id of the parent entity.
     * @param embeddedDomain The embedded domain entity to be saved.
     * @param cdClass        The class of the embedded domain entity.
     * @param user           A user context.
     * @return A Mono that emits the updated DTO entity.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    @MethodStats
    default <ED extends BaseEmbeddedDomain<ID>> Mono<@NonNull DTO> save(@NotNull ID parentId, @NotNull @Valid ED embeddedDomain, Class<ED> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(parentId, Collections.singletonList(embeddedDomain), cdClass, user);
    }

    /**
     * Saves multiple embedded domain entities.
     *
     * @param parentId        The id of the parent entity.
     * @param embeddedDomains The collection of embedded domain entities to be saved.
     * @param cdClass         The class of the embedded domain entities.
     * @param user            A user context.
     * @return A Mono that emits the updated DTO entity.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws BadRequestException     If the request is invalid.
     */
    @MethodStats
    default <ED extends BaseEmbeddedDomain<ID>> Mono<@NonNull DTO> saveMany(@NotNull ID parentId, @NotEmpty Collection<@Valid ED> embeddedDomains, Class<ED> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {
                    for (ED cd : embeddedDomains) {
                        if (cd.getId() == null || Strings.isBlank(cd.getId().toString())) {
                            cd.setId(getIdService().newId());
                        }
                    }

                    EmbeddedDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).addAll(embeddedDomains);
                })
                .flatMap(dto -> updateDto(parentId, dto, user));
    }

    BaseIdService getIdService();
}
