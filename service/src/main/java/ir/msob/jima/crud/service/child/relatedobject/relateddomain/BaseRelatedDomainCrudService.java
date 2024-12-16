package ir.msob.jima.crud.service.child.relatedobject.relateddomain;

import ir.msob.jima.core.commons.child.relatedobject.relateddomain.BaseRelatedDomainContainer;
import ir.msob.jima.core.commons.child.relatedobject.relateddomain.RelatedDomainAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relateddomain.RelatedDomainCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.child.relatedobject.ParentRelatedObjectService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

public interface BaseRelatedDomainCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , RD extends RelatedDomainAbstract<ID>
        , C extends RelatedDomainCriteriaAbstract<ID, RD>
        , CNT extends BaseRelatedDomainContainer<ID, RD>
        , DTO extends BaseDto<ID> & BaseRelatedDomainContainer<ID, RD>>
        extends ParentRelatedObjectService<ID, ID, USER, RD, C, CNT, DTO> {
    Logger log = LoggerFactory.getLogger(BaseRelatedDomainCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteByRelatedId(@NotNull ID parentId, @NotNull ID relatedId, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteByRelatedId(
                parentId
                , relatedId
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, Collection<RD> relatedDomains, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , relatedDomains
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, RD relatedDomain, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , relatedDomain
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, RD relatedDomain, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , relatedDomain
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateByRelatedId(@NotNull ID parentId, @NotNull ID relatedId, RD relatedDomain, USER user) throws DomainNotFoundException, BadRequestException {
        return updateByRelatedId(
                parentId
                , relatedId
                , relatedDomain
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, Collection<RD> relatedDomains, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , relatedDomains
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, RD relatedDomain, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , relatedDomain
                , criteria
                , BaseRelatedDomainContainer::getRelatedDomains
                , user);
    }

}