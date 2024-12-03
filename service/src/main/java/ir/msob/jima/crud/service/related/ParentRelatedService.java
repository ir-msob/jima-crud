package ir.msob.jima.crud.service.related;

import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.datanotfound.DataNotFoundException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.id.BaseIdService;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.related.BaseRelatedDto;
import ir.msob.jima.core.commons.related.BaseRelatedModel;
import ir.msob.jima.core.commons.related.BaseRelatedModelCriteria;
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
        , RM extends BaseRelatedModel<ID>
        , C extends BaseRelatedModelCriteria<ID, RM>
        , RDTO extends BaseRelatedDto<ID>> {

    default Class<RM> getRelatedModelClass() {
        return (Class<RM>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentRelatedService.class, 4);
    }

    default Class<C> getRelatedModelCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentRelatedService.class, 5);
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
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, RM idModel, Function<DTO, SortedSet<RM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getRelatedModelCriteriaClass().getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return update(parentId, idModel, criteria, getter, user);
    }


    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, RM idModel, @NotNull C criteria, Function<DTO, SortedSet<RM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getRelatedObjectDtoClass().isInstance(dto)) {
                        Iterator<RM> iterator = getter.apply(dto).iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            RM next = iterator.next();
                            if (criteria.isMatching(next)) {
                                idModel.setId(next.getId());
                                getter.apply(dto).add(idModel);
                                iterator.remove();
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            throw new DataNotFoundException("No related model found with Criteria in the parent DTO of type " + getRelatedObjectDtoClass().getSimpleName(), getRelatedModelClass().getSimpleName(), criteria.toString(), getRelatedObjectDtoClass());
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getRelatedObjectDtoClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, List<RM> idModels, Function<DTO, SortedSet<RM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getRelatedObjectDtoClass().isInstance(dto)) {
                        Iterator<RM> iterator = getter.apply(dto).iterator();

                        for (RM ro : idModels) {
                            boolean found = false;
                            while (iterator.hasNext()) {
                                RM next = iterator.next();
                                if (Objects.equals(ro.getId(), next.getId())) {
                                    getter.apply(dto).add(ro);
                                    iterator.remove();
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                throw new DataNotFoundException("No related object found with Criteria in the parent DTO of type " + getRelatedObjectDtoClass().getSimpleName(), getRelatedModelClass().getSimpleName(), null, getRelatedObjectDtoClass());
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
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, Function<DTO, SortedSet<RM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getRelatedModelCriteriaClass().getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return delete(parentId, criteria, getter, user);
    }


    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, @NotNull C criteria, Function<DTO, SortedSet<RM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getRelatedObjectDtoClass().isInstance(dto)) {
                        Iterator<RM> iterator = getter.apply(dto).iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            RM ro = iterator.next();

                            if (criteria.isMatching(ro)) {
                                iterator.remove();
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            throw new DataNotFoundException("No related object found with Criteria in the parent DTO of type " + getRelatedObjectDtoClass().getSimpleName(), getRelatedModelClass().getSimpleName(), criteria.toString(), getRelatedModelClass());
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getRelatedObjectDtoClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, @NotNull C criteria, Function<DTO, SortedSet<RM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getRelatedObjectDtoClass().isInstance(dto)) {
                        Iterator<RM> iterator = getter.apply(dto).iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            RM ro = iterator.next();
                            if (criteria.isMatching(ro)) {
                                iterator.remove();
                                found = true;
                            }
                        }

                        if (!found) {
                            throw new DataNotFoundException("No related object found with Criteria in the parent DTO of type " + getRelatedObjectDtoClass().getSimpleName(), getRelatedModelClass().getSimpleName(), criteria.toString(), getRelatedModelClass());
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getRelatedObjectDtoClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, @NotNull @Valid RM relatedObject, Function<DTO, SortedSet<RM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(parentId, Collections.singletonList(relatedObject), getter, user);
    }

    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, @NotEmpty List<@Valid RM> relatedObjects, Function<DTO, SortedSet<RM>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    for (RM idModel : relatedObjects) {
                        if (idModel.getId() == null || Strings.isBlank(idModel.getId().toString())) {
                            idModel.setId(getIdService().newId());
                        }
                    }

                    getter.apply(dto).addAll(relatedObjects);
                })
                .flatMap(dto -> updateDto(parentId, dto, user));
    }

    BaseIdService getIdService();
}
