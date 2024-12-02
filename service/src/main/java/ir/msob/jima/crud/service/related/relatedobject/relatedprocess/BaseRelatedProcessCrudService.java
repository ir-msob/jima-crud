package ir.msob.jima.crud.service.related.relatedobject.relatedprocess;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.related.relatedobject.relatedprocess.BaseRelatedProcessDto;
import ir.msob.jima.core.commons.related.relatedobject.relatedprocess.RelatedProcessAbstract;
import ir.msob.jima.core.commons.related.relatedobject.relatedprocess.RelatedProcessCriteriaAbstract;
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

public interface BaseRelatedProcessCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , C extends RelatedProcessCriteriaAbstract<ID>
        , RP extends RelatedProcessAbstract<ID>>
        extends ParentRelatedObjectService<ID, String, USER, DTO, C, RP, BaseRelatedProcessDto<ID, RP>> {
    Logger log = LoggerFactory.getLogger(BaseRelatedProcessCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteByRelatedId(@NotNull ID parentId, @NotBlank String relatedId, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteByRelatedId(
                parentId
                , relatedId
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, List<RP> relatedProcesses, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , relatedProcesses
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, RP relatedProcess, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , relatedProcess
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, RP relatedProcess, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , relatedProcess
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateByRelatedId(@NotNull ID parentId, @NotBlank String relatedId, RP relatedProcess, USER user) throws DomainNotFoundException, BadRequestException {
        return updateByRelatedId(
                parentId
                , relatedId
                , relatedProcess
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, List<RP> relatedProcesses, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , relatedProcesses
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, RP relatedProcess, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , relatedProcess
                , criteria
                , dto -> ((BaseRelatedProcessDto<ID, RP>) dto).getRelatedProcesses()
                , user);
    }
}