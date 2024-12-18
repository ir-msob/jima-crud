package ir.msob.jima.crud.service.childdomain;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.ChildDomainUtil;
import ir.msob.jima.core.commons.childdomain.criteria.*;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.datanotfound.DataNotFoundException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.filter.Filter;
import ir.msob.jima.core.commons.id.BaseIdService;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public interface BaseChildDomainCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>> {

    // TODO
    default Class<DTO> getParentDtoClass() {
        return (Class<DTO>) GenericTypeUtil.resolveTypeArguments(this.getClass(), BaseChildDomainCrudService.class, 2);
    }

    /**
     * Get one DTO entity based on criteria.
     *
     * @param id   The id of entity.
     * @param user A user context.
     * @return A Mono that emits the found DTO entity.
     */
    Mono<DTO> getDto(ID id, USER user);

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
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteriaKey<ID, CD>> Mono<DTO> updateByKey(@NotNull ID parentId, @NotBlank String key, CD childDomain, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setKey(Filter.eq(key));
        return this.update(parentId, childDomain, criteria, cdClass, user);
    }

    @SneakyThrows
    @MethodStats
    default <RID extends Comparable<RID> & Serializable, CD extends BaseChildDomain<ID>, CC extends BaseChildCriteriaRelatedId<ID, RID, CD>> Mono<DTO> updateByRelatedId(@NotNull ID parentId, @NotNull RID relatedId, CD childDomain, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setRelatedId(Filter.eq(relatedId));
        return this.update(parentId, childDomain, criteria, cdClass, user);
    }

    @SneakyThrows
    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteriaName<ID, CD>> Mono<DTO> updateByName(@NotNull ID parentId, @NotBlank String name, CD childDomain, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return this.update(parentId, childDomain, criteria, cdClass, user);
    }

    @SneakyThrows
    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteriaType<ID, CD>> Mono<DTO> updateByType(@NotNull ID parentId, @NotBlank String type, CD childDomain, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setType(Filter.eq(type));
        return this.update(parentId, childDomain, criteria, cdClass, user);
    }


    @SneakyThrows
    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteria<ID, CD>> Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, CD childDomain, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return this.update(parentId, childDomain, criteria, cdClass, user);
    }


    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteria<ID, CD>> Mono<DTO> update(@NotNull ID parentId, CD childDomain, @NotNull CC childCriteria, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {

                    Iterator<CD> iterator = ChildDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).iterator();
                    boolean found = false;

                    while (iterator.hasNext()) {
                        CD next = iterator.next();
                        if (childCriteria.isMatching(next)) {
                            childDomain.setId(next.getId());
                            ChildDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).add(childDomain);
                            iterator.remove();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        throw new DataNotFoundException("No childdomain model found with Criteria in the parent DTO of type " + dto.getClass().getSimpleName(), childDomain.getClass().getSimpleName(), childCriteria.toString());
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default <CD extends BaseChildDomain<ID>> Mono<DTO> updateMany(@NotNull ID parentId, Collection<CD> childDomains, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {
                    Iterator<CD> iterator = ChildDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).iterator();

                    for (CD cd : childDomains) {
                        boolean found = false;
                        while (iterator.hasNext()) {
                            CD next = iterator.next();
                            if (Objects.equals(cd.getId(), next.getId())) {
                                ChildDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).add(cd);
                                iterator.remove();
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            throw new DataNotFoundException("No childdomain model found with Criteria in the parent DTO of type " + dto.getClass().getSimpleName());
                        }
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @SneakyThrows
    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteriaKey<ID, CD>> Mono<DTO> deleteByKey(@NotNull ID parentId, @NotBlank String key, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setKey(Filter.eq(key));
        return delete(parentId, criteria, cdClass, user);
    }

    @SneakyThrows
    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteriaName<ID, CD>> Mono<DTO> deleteByName(@NotNull ID parentId, @NotBlank String name, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return delete(parentId, criteria, cdClass, user);
    }

    @SneakyThrows
    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteriaType<ID, CD>> Mono<DTO> deleteByType(@NotNull ID parentId, @NotBlank String type, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setType(Filter.eq(type));
        return delete(parentId, criteria, cdClass, user);
    }

    @SneakyThrows
    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteria<ID, CD>> Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setId(Filter.eq(id));
        return delete(parentId, criteria, cdClass, user);
    }

    @SneakyThrows
    @MethodStats
    default <RID extends Comparable<RID> & Serializable, CD extends BaseChildDomain<ID>, CC extends BaseChildCriteriaRelatedId<ID, RID, CD>> Mono<DTO> deleteByRelatedId(@NotNull ID parentId, @NotNull RID relatedId, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        CC criteria = (CC) ChildDomainUtil.getCriteriaClass(cdClass, getParentDtoClass()).getConstructor().newInstance();
        criteria.setRelatedId(Filter.eq(relatedId));
        return delete(parentId, criteria, cdClass, user);
    }


    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteria<ID, CD>> Mono<DTO> delete(@NotNull ID parentId, @NotNull CC childCriteria, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {

                    Iterator<CD> iterator = ChildDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).iterator();
                    boolean found = false;

                    while (iterator.hasNext()) {
                        CD next = iterator.next();

                        if (childCriteria.isMatching(next)) {
                            iterator.remove();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        throw new DataNotFoundException("No childdomain object found with Criteria in the parent DTO of type ");
                    }

                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default <CD extends BaseChildDomain<ID>, CC extends BaseChildCriteria<ID, CD>> Mono<DTO> deleteMany(@NotNull ID parentId, @NotNull CC childCriteria, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {
                    Iterator<CD> iterator = ChildDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).iterator();
                    boolean found = false;

                    while (iterator.hasNext()) {
                        CD next = iterator.next();
                        if (childCriteria.isMatching(next)) {
                            iterator.remove();
                            found = true;
                        }
                    }

                    if (!found) {
                        throw new DataNotFoundException("No childdomain object found with Criteria in the parent DTO of type ");
                    }
                })
                .flatMap(updatedDto -> updateDto(parentId, updatedDto, user));
    }

    @MethodStats
    default <CD extends BaseChildDomain<ID>> Mono<DTO> save(@NotNull ID parentId, @NotNull @Valid CD childDomain, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(parentId, Collections.singletonList(childDomain), cdClass, user);
    }

    @MethodStats
    default <CD extends BaseChildDomain<ID>> Mono<DTO> saveMany(@NotNull ID parentId, @NotEmpty Collection<@Valid CD> childDomains, Class<CD> cdClass, USER user) throws DomainNotFoundException, BadRequestException {
        return getDto(parentId, user)
                .doOnNext(dto -> {
                    for (CD cd : childDomains) {
                        if (cd.getId() == null || Strings.isBlank(cd.getId().toString())) {
                            cd.setId(getIdService().newId());
                        }
                    }

                    ChildDomainUtil.getFunction(cdClass, getParentDtoClass()).apply(dto).addAll(childDomains);
                })
                .flatMap(dto -> updateDto(parentId, dto, user));
    }

    BaseIdService getIdService();
}
