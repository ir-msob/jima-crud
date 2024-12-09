package ir.msob.jima.crud.service.child.relatedobject.relateddomain;

import ir.msob.jima.core.commons.child.relatedobject.relateddomain.BaseRelatedDomainDto;
import ir.msob.jima.core.commons.child.relatedobject.relateddomain.RelatedDomainAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relateddomain.RelatedDomainCriteriaAbstract;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.child.relatedobject.ParentRelatedObjectService;
import jakarta.validation.constraints.NotBlank;
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
        , DTO extends BaseDto<ID>
        , RD extends RelatedDomainAbstract<ID>
        , C extends RelatedDomainCriteriaAbstract<ID, RD>>
        extends ParentRelatedObjectService<ID, ID, USER, DTO, RD, C, BaseRelatedDomainDto<ID, RD>> {
    Logger log = LoggerFactory.getLogger(BaseRelatedDomainCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteByRelatedId(@NotNull ID parentId, @NotBlank ID relatedId, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteByRelatedId(
                parentId
                , relatedId
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, Collection<RD> relatedDomains, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , relatedDomains
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, RD relatedDomain, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , relatedDomain
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, RD relatedDomain, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , relatedDomain
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateByRelatedId(@NotNull ID parentId, @NotBlank ID relatedId, RD relatedDomain, USER user) throws DomainNotFoundException, BadRequestException {
        return updateByRelatedId(
                parentId
                , relatedId
                , relatedDomain
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, Collection<RD> relatedDomains, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , relatedDomains
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, RD relatedDomain, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , relatedDomain
                , criteria
                , dto -> ((BaseRelatedDomainDto<ID, RD>) dto).getRelatedDomains()
                , user);
    }

}