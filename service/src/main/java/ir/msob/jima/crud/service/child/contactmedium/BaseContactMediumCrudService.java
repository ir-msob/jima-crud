package ir.msob.jima.crud.service.child.contactmedium;

import ir.msob.jima.core.commons.child.contactmedium.BaseContactMediumContainer;
import ir.msob.jima.core.commons.child.contactmedium.ContactMediumAbstract;
import ir.msob.jima.core.commons.child.contactmedium.ContactMediumCriteriaAbstract;
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

public interface BaseContactMediumCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CM extends ContactMediumAbstract<ID>
        , C extends ContactMediumCriteriaAbstract<ID, CM>
        , CNT extends BaseContactMediumContainer<ID, CM>
        , DTO extends BaseDto<ID> & BaseContactMediumContainer<ID, CM>>
        extends ParentChildCrudService<ID, USER, CM, C, CNT, DTO> {
    Logger log = LoggerFactory.getLogger(BaseContactMediumCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , BaseContactMediumContainer::getContactMediums
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> deleteByName(@NotNull ID parentId, @NotBlank String name, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return delete(parentId, criteria, BaseContactMediumContainer::getContactMediums, user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> deleteByType(@NotNull ID parentId, @NotBlank String type, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setType(Filter.eq(type));
        return delete(parentId, criteria, BaseContactMediumContainer::getContactMediums, user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , BaseContactMediumContainer::getContactMediums
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , BaseContactMediumContainer::getContactMediums
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, Collection<CM> contactmediums, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , contactmediums
                , BaseContactMediumContainer::getContactMediums
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, CM contactmedium, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , contactmedium
                , BaseContactMediumContainer::getContactMediums
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, CM contactmedium, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , contactmedium
                , BaseContactMediumContainer::getContactMediums
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> updateByName(@NotNull ID parentId, @NotBlank String name, CM contactmedium, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setName(Filter.eq(name));
        return update(parentId, contactmedium, criteria, BaseContactMediumContainer::getContactMediums, user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> updateByType(@NotNull ID parentId, @NotBlank String type, CM contactmedium, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setType(Filter.eq(type));
        return update(parentId, contactmedium, criteria, BaseContactMediumContainer::getContactMediums, user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, Collection<CM> contactmediums, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , contactmediums
                , BaseContactMediumContainer::getContactMediums
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, CM contactmedium, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , contactmedium
                , criteria
                , BaseContactMediumContainer::getContactMediums
                , user);
    }
}