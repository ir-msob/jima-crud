package ir.msob.jima.crud.service.related.relatedaction;

import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.related.relatedaction.BaseRelatedActionDto;
import ir.msob.jima.core.commons.related.relatedaction.RelatedActionAbstract;
import ir.msob.jima.core.commons.related.relatedaction.RelatedActionCriteriaAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.related.ParentRelatedService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

public interface BaseRelatedActionCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RA extends RelatedActionAbstract<ID>
        , C extends RelatedActionCriteriaAbstract<ID, RA>
        , RADTO extends BaseRelatedActionDto<ID, RA>>
        extends ParentRelatedService<ID, USER, DTO, RA, C, RADTO> {
    Logger log = LoggerFactory.getLogger(BaseRelatedActionCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions()
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> deleteByName(@NotNull ID parentId, @NotBlank String name, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getRelatedModelCriteriaClass().getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return delete(parentId, criteria, dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions(), user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, List<RA> objectvalidations, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , objectvalidations
                , dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, RA objectvalidation, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , objectvalidation
                , dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, RA objectvalidation, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , objectvalidation
                , dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions()
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> updateByName(@NotNull ID parentId, @NotBlank String name, RA objectvalidation, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getRelatedModelCriteriaClass().getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return update(parentId, objectvalidation, criteria, dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions(), user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, List<RA> objectvalidations, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , objectvalidations
                , dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, RA objectvalidation, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , objectvalidation
                , criteria
                , dto -> ((BaseRelatedActionDto<ID, RA>) dto).getRelatedActions()
                , user);
    }
}