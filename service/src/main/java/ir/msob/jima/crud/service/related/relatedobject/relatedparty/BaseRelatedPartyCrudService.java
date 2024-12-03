package ir.msob.jima.crud.service.related.relatedobject.relatedparty;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.related.relatedobject.relatedparty.BaseRelatedPartyDto;
import ir.msob.jima.core.commons.related.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.core.commons.related.relatedobject.relatedparty.RelatedPartyCriteriaAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.related.relatedobject.ParentRelatedObjectService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

public interface BaseRelatedPartyCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RP extends RelatedPartyAbstract<ID>
        , C extends RelatedPartyCriteriaAbstract<ID, RP>>
        extends ParentRelatedObjectService<ID, String, USER, DTO, RP, C, BaseRelatedPartyDto<ID, RP>> {
    Logger log = LoggerFactory.getLogger(BaseRelatedPartyCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteByRelatedId(@NotNull ID parentId, @NotBlank String relatedId, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteByRelatedId(
                parentId
                , relatedId
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, List<RP> relatedParties, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , relatedParties
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, RP relatedParty, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , relatedParty
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, RP relatedParty, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , relatedParty
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateByRelatedId(@NotNull ID parentId, @NotBlank String relatedId, RP relatedParty, USER user) throws DomainNotFoundException, BadRequestException {
        return updateByRelatedId(
                parentId
                , relatedId
                , relatedParty
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, List<RP> relatedParties, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , relatedParties
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, RP relatedParty, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , relatedParty
                , criteria
                , dto -> ((BaseRelatedPartyDto<ID, RP>) dto).getRelatedParties()
                , user);
    }
}