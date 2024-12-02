package ir.msob.jima.crud.service.related;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.domain.BaseIdModelAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.datanotfound.DataNotFoundException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.id.BaseIdService;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.related.BaseRelatedDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

public interface ParentRelatedService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , C extends BaseCriteria<ID>
        , IDM extends BaseIdModelAbstract<ID>
        , RDTO extends BaseRelatedDto<ID>> {

    default Class<C> getCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentRelatedService.class, 4);
    }

    default Class<IDM> getIdModelClass() {
        return (Class<IDM>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentRelatedService.class, 5);
    }

    default Class<RDTO> getRelatedObjectDtoClass() {
        return (Class<RDTO>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentRelatedService.class, 6);
    }

    /**
     * Get one DTO entity based on criteria.
     *
     * @param id   The id of entity.
     * @param user A user context.
     * @return A Mono that emits the found DTO entity.
     */
    Mono<DTO> getDtoById(ID id, USER user);

    /**
     * Update a DTO entity.
     *
     * @param id   The id of entity.
     * @param dto  The DTO entity to be updated.
     * @param user A user context.
     * @return A Mono that emits the updated DTO entity.
     */
    Mono<DTO> updateDto(ID id, @Valid DTO dto, USER user);

    @SneakyThrows
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, IDM idModel, Function<DTO, SortedSet<IDM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getCriteriaClass().getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return update(parentId, idModel, criteria, getter, user);
    }


    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, IDM idModel, C criteria, Function<DTO, SortedSet<IDM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getRelatedObjectDtoClass().isInstance(dto)) {
                        Iterator<IDM> iterator = getter.apply(dto).iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            IDM next = iterator.next();
                            if (this.isMatching(criteria, next)) {
                                idModel.setDomainId(next.getDomainId());
                                getter.apply(dto).add(idModel);
                                iterator.remove();
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            throw new DataNotFoundException("No related model found with Criteria in the parent DTO of type " + getRelatedObjectDtoClass().getSimpleName(), getIdModelClass().getSimpleName(), criteria.toString(), getRelatedObjectDtoClass());
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getRelatedObjectDtoClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, List<IDM> idModels, Function<DTO, SortedSet<IDM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getRelatedObjectDtoClass().isInstance(dto)) {
                        Iterator<IDM> iterator = getter.apply(dto).iterator();

                        for (IDM ro : idModels) {
                            boolean found = false;
                            while (iterator.hasNext()) {
                                IDM next = iterator.next();
                                if (Objects.equals(ro.getId(), next.getId())) {
                                    getter.apply(dto).add(ro);
                                    iterator.remove();
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                throw new DataNotFoundException("No related object found with Criteria in the parent DTO of type " + getRelatedObjectDtoClass().getSimpleName(), getIdModelClass().getSimpleName(), null, getRelatedObjectDtoClass());
                            }
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getRelatedObjectDtoClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @SneakyThrows
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, Function<DTO, SortedSet<IDM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getCriteriaClass().getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return delete(parentId, criteria, getter, user);
    }


    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, Function<DTO, SortedSet<IDM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getRelatedObjectDtoClass().isInstance(dto)) {
                        Iterator<IDM> iterator = getter.apply(dto).iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            IDM ro = iterator.next();

                            if (this.isMatching(criteria, ro)) {
                                iterator.remove();
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            throw new DataNotFoundException("No related object found with Criteria in the parent DTO of type " + getRelatedObjectDtoClass().getSimpleName(), getIdModelClass().getSimpleName(), criteria.toString(), getIdModelClass());
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getRelatedObjectDtoClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, Function<DTO, SortedSet<IDM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getRelatedObjectDtoClass().isInstance(dto)) {
                        Iterator<IDM> iterator = getter.apply(dto).iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            IDM ro = iterator.next();
                            if (this.isMatching(criteria, ro)) {
                                iterator.remove();
                                found = true;
                            }
                        }

                        if (!found) {
                            throw new DataNotFoundException("No related object found with Criteria in the parent DTO of type " + getRelatedObjectDtoClass().getSimpleName(), getIdModelClass().getSimpleName(), criteria.toString(), getIdModelClass());
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getRelatedObjectDtoClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, @NotNull @Valid IDM relatedObject, Function<DTO, SortedSet<IDM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(parentId, Collections.singletonList(relatedObject), getter, user);
    }

    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, @NotEmpty List<@Valid IDM> relatedObjects, Function<DTO, SortedSet<IDM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    for (IDM idModel : relatedObjects) {
                        if (idModel.getDomainId() == null || Strings.isBlank(idModel.getDomainId().toString())) {
                            idModel.setDomainId(getIdService().newId());
                        }
                    }

                    getter.apply(dto).addAll(relatedObjects);
                })
                .flatMap(dto -> updateDto(parentId, dto, user));
    }

    BaseIdService getIdService();

    boolean isMatching(C criteria, IDM idModel);

}
