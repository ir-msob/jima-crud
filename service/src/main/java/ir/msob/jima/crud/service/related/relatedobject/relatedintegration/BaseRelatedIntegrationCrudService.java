package ir.msob.jima.crud.service.related.relatedobject.relatedintegration;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.related.relatedobject.relatedintegration.BaseRelatedIntegrationDto;
import ir.msob.jima.core.commons.related.relatedobject.relatedintegration.RelatedIntegrationAbstract;
import ir.msob.jima.core.commons.related.relatedobject.relatedintegration.RelatedIntegrationCriteriaAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.related.relatedobject.ParentRelatedObjectService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;

public interface BaseRelatedIntegrationCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RI extends RelatedIntegrationAbstract<ID>
        , C extends RelatedIntegrationCriteriaAbstract<ID, RI>>
        extends ParentRelatedObjectService<ID, String, USER, DTO, RI, C, BaseRelatedIntegrationDto<ID, RI>> {
    Logger log = LoggerFactory.getLogger(BaseRelatedIntegrationCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteByRelatedId(@NotNull ID parentId, @NotBlank String relatedId, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteByRelatedId(
                parentId
                , relatedId
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, Collection<RI> relatedIntegrations, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , relatedIntegrations
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, RI relatedIntegration, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , relatedIntegration
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, RI relatedIntegration, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , relatedIntegration
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateByRelatedId(@NotNull ID parentId, @NotBlank String relatedId, RI relatedIntegration, USER user) throws DomainNotFoundException, BadRequestException {
        return updateByRelatedId(
                parentId
                , relatedId
                , relatedIntegration
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, Collection<RI> relatedIntegrations, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , relatedIntegrations
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, RI relatedIntegration, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , relatedIntegration
                , criteria
                , dto -> ((BaseRelatedIntegrationDto<ID, RI>) dto).getRelatedIntegrations()
                , user);
    }
}