package ir.msob.jima.crud.service.child;

import ir.msob.jima.core.commons.child.BaseChild;
import ir.msob.jima.core.commons.child.BaseChildCriteria;
import ir.msob.jima.core.commons.child.BaseContainer;
import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.datanotfound.DataNotFoundException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.id.BaseIdService;
import ir.msob.jima.core.commons.methodstats.MethodStats;
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

public interface ParentChildService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CHILD extends BaseChild<ID>
        , C extends BaseChildCriteria<ID, CHILD>
        , CNT extends BaseContainer
        , DTO extends BaseDto<ID> & BaseContainer> {

    default Class<CHILD> getChildClass() {
        return (Class<CHILD>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentChildService.class, 2);
    }

    default Class<C> getChildCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentChildService.class, 3);
    }

    default Class<CNT> getContinerClass() {
        return (Class<CNT>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentChildService.class, 4);
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
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, CHILD relatedModel, Function<DTO, SortedSet<CHILD>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return update(parentId, relatedModel, criteria, getter, user);
    }


    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, CHILD relatedModel, @NotNull C criteria, Function<DTO, SortedSet<CHILD>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getContinerClass().isInstance(dto)) {
                        Iterator<CHILD> iterator = getter.apply(dto).iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            CHILD next = iterator.next();
                            if (criteria.isMatching(next)) {
                                relatedModel.setId(next.getId());
                                getter.apply(dto).add(relatedModel);
                                iterator.remove();
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            throw new DataNotFoundException("No child model found with Criteria in the parent DTO of type " + getContinerClass().getSimpleName(), getChildClass().getSimpleName(), criteria.toString(), getContinerClass());
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getContinerClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, Collection<CHILD> relatedModels, Function<DTO, SortedSet<CHILD>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getContinerClass().isInstance(dto)) {
                        Iterator<CHILD> iterator = getter.apply(dto).iterator();

                        for (CHILD ro : relatedModels) {
                            boolean found = false;
                            while (iterator.hasNext()) {
                                CHILD next = iterator.next();
                                if (Objects.equals(ro.getId(), next.getId())) {
                                    getter.apply(dto).add(ro);
                                    iterator.remove();
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                throw new DataNotFoundException("No child object found with Criteria in the parent DTO of type " + getContinerClass().getSimpleName(), getChildClass().getSimpleName(), null, getContinerClass());
                            }
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getContinerClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @SneakyThrows
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, Function<DTO, SortedSet<CHILD>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return delete(parentId, criteria, getter, user);
    }


    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, @NotNull C criteria, Function<DTO, SortedSet<CHILD>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getContinerClass().isInstance(dto)) {
                        Iterator<CHILD> iterator = getter.apply(dto).iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            CHILD ro = iterator.next();

                            if (criteria.isMatching(ro)) {
                                iterator.remove();
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            throw new DataNotFoundException("No child object found with Criteria in the parent DTO of type " + getContinerClass().getSimpleName(), getChildClass().getSimpleName(), criteria.toString(), getChildClass());
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getContinerClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, @NotNull C criteria, Function<DTO, SortedSet<CHILD>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    if (getContinerClass().isInstance(dto)) {
                        Iterator<CHILD> iterator = getter.apply(dto).iterator();
                        boolean found = false;

                        while (iterator.hasNext()) {
                            CHILD ro = iterator.next();
                            if (criteria.isMatching(ro)) {
                                iterator.remove();
                                found = true;
                            }
                        }

                        if (!found) {
                            throw new DataNotFoundException("No child object found with Criteria in the parent DTO of type " + getContinerClass().getSimpleName(), getChildClass().getSimpleName(), criteria.toString(), getChildClass());
                        }
                    } else {
                        throw new BadRequestException("Provided DTO is of type " + dto.getClass().getSimpleName() + " but expected type is " + getContinerClass().getSimpleName());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, @NotNull @Valid CHILD relatedObject, Function<DTO, SortedSet<CHILD>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(parentId, Collections.singletonList(relatedObject), getter, user);
    }

    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, @NotEmpty Collection<@Valid CHILD> relatedObjects, Function<DTO, SortedSet<CHILD>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        return getDtoById(parentId, user)
                .doOnNext(dto -> {
                    for (CHILD relatedModel : relatedObjects) {
                        if (relatedModel.getId() == null || Strings.isBlank(relatedModel.getId().toString())) {
                            relatedModel.setId(getIdService().newId());
                        }
                    }

                    getter.apply(dto).addAll(relatedObjects);
                })
                .flatMap(dto -> updateDto(parentId, dto, user));
    }

    BaseIdService getIdService();
}
