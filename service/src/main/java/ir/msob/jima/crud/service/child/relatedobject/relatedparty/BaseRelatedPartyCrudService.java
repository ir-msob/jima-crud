package ir.msob.jima.crud.service.child.relatedobject.relatedparty;

import ir.msob.jima.core.commons.child.relatedobject.relatedparty.BaseRelatedPartyContainer;
import ir.msob.jima.core.commons.child.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.core.commons.child.relatedobject.relatedparty.RelatedPartyCriteriaAbstract;
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

public interface BaseRelatedPartyCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , RP extends RelatedPartyAbstract<ID>
        , C extends RelatedPartyCriteriaAbstract<ID, RP>
        , CNT extends BaseRelatedPartyContainer<ID, RP>
        , DTO extends BaseDto<ID> & BaseRelatedPartyContainer<ID, RP>>
        extends ParentRelatedObjectService<ID, String, USER, RP, C, CNT, DTO> {
    Logger log = LoggerFactory.getLogger(BaseRelatedPartyCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteByRelatedId(@NotNull ID parentId, @NotBlank String relatedId, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteByRelatedId(
                parentId
                , relatedId
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, Collection<RP> relatedParties, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , relatedParties
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, RP relatedParty, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , relatedParty
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, RP relatedParty, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , relatedParty
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateByRelatedId(@NotNull ID parentId, @NotBlank String relatedId, RP relatedParty, USER user) throws DomainNotFoundException, BadRequestException {
        return updateByRelatedId(
                parentId
                , relatedId
                , relatedParty
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, Collection<RP> relatedParties, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , relatedParties
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, RP relatedParty, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , relatedParty
                , criteria
                , BaseRelatedPartyContainer::getRelatedParties
                , user);
    }
}