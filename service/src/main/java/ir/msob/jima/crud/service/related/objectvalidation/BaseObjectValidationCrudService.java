package ir.msob.jima.crud.service.related.objectvalidation;

import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.related.objectvalidation.BaseObjectValidationDto;
import ir.msob.jima.core.commons.related.objectvalidation.ObjectValidationAbstract;
import ir.msob.jima.core.commons.related.objectvalidation.ObjectValidationCriteriaAbstract;
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

public interface BaseObjectValidationCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , OV extends ObjectValidationAbstract<ID>
        , C extends ObjectValidationCriteriaAbstract<ID, OV>
        , OVDTO extends BaseObjectValidationDto<ID, OV>>
        extends ParentRelatedService<ID, USER, DTO, OV, C, OVDTO> {
    Logger log = LoggerFactory.getLogger(BaseObjectValidationCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations()
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> deleteByName(@NotNull ID parentId, @NotBlank String name, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getRelatedModelCriteriaClass().getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return delete(parentId, criteria, dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations(), user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, List<OV> objectvalidations, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , objectvalidations
                , dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, OV objectvalidation, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , objectvalidation
                , dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, OV objectvalidation, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , objectvalidation
                , dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations()
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> updateByName(@NotNull ID parentId, @NotBlank String name, OV objectvalidation, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getRelatedModelCriteriaClass().getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return update(parentId, objectvalidation, criteria, dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations(), user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, List<OV> objectvalidations, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , objectvalidations
                , dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations()
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, OV objectvalidation, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , objectvalidation
                , criteria
                , dto -> ((BaseObjectValidationDto<ID, OV>) dto).getObjectValidations()
                , user);
    }
}