package ir.msob.jima.crud.service.child.relatedaction;

import ir.msob.jima.core.commons.child.relatedaction.BaseRelatedActionContainer;
import ir.msob.jima.core.commons.child.relatedaction.RelatedActionAbstract;
import ir.msob.jima.core.commons.child.relatedaction.RelatedActionCriteriaAbstract;
import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.child.ParentChildCrudService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

public interface BaseRelatedActionCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , RA extends RelatedActionAbstract<ID>
        , C extends RelatedActionCriteriaAbstract<ID, RA>
        , CNT extends BaseRelatedActionContainer<ID, RA>
        , DTO extends BaseDto<ID> & BaseRelatedActionContainer<ID, RA>>
        extends ParentChildCrudService<ID, USER, RA, C, CNT, DTO> {
    Logger log = LoggerFactory.getLogger(BaseRelatedActionCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , BaseRelatedActionContainer::getRelatedActions
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> deleteByName(@NotNull ID parentId, @NotBlank String name, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return delete(parentId, criteria, BaseRelatedActionContainer::getRelatedActions, user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , BaseRelatedActionContainer::getRelatedActions
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , BaseRelatedActionContainer::getRelatedActions
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, Collection<RA> objectvalidations, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , objectvalidations
                , BaseRelatedActionContainer::getRelatedActions
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, RA objectvalidation, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , objectvalidation
                , BaseRelatedActionContainer::getRelatedActions
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, RA objectvalidation, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , objectvalidation
                , BaseRelatedActionContainer::getRelatedActions
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> updateByName(@NotNull ID parentId, @NotBlank String name, RA objectvalidation, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return update(parentId, objectvalidation, criteria, BaseRelatedActionContainer::getRelatedActions, user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, Collection<RA> objectvalidations, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , objectvalidations
                , BaseRelatedActionContainer::getRelatedActions
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, RA objectvalidation, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , objectvalidation
                , criteria
                , BaseRelatedActionContainer::getRelatedActions
                , user);
    }
}