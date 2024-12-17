package ir.msob.jima.crud.service.child.characteristic;

import ir.msob.jima.core.commons.child.characteristic.BaseCharacteristicContainer;
import ir.msob.jima.core.commons.child.characteristic.Characteristic;
import ir.msob.jima.core.commons.child.characteristic.CharacteristicCriteria;
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

public interface BaseCharacteristicCrudService<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CH extends Characteristic<ID>
        , C extends CharacteristicCriteria<ID, CH>
        , CNT extends BaseCharacteristicContainer<ID, CH>
        , DTO extends BaseDto<ID> & BaseCharacteristicContainer<ID, CH>>
        extends ParentChildCrudService<ID, USER, CH, C, CNT, DTO> {
    Logger log = LoggerFactory.getLogger(BaseCharacteristicCrudService.class);

    @Transactional
    @MethodStats
    default Mono<DTO> deleteById(@NotNull ID parentId, @NotNull ID id, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteById(
                parentId
                , id
                , BaseCharacteristicContainer::getCharacteristics
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> deleteByKey(@NotNull ID parentId, @NotBlank String key, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setKey(Filter.eq(key));
        return delete(parentId, criteria, BaseCharacteristicContainer::getCharacteristics, user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> deleteMany(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return deleteMany(
                parentId
                , criteria
                , BaseCharacteristicContainer::getCharacteristics
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> delete(@NotNull ID parentId, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return delete(
                parentId
                , criteria
                , BaseCharacteristicContainer::getCharacteristics
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> saveMany(@NotNull ID parentId, Collection<CH> characteristics, USER user) throws DomainNotFoundException, BadRequestException {
        return saveMany(
                parentId
                , characteristics
                , BaseCharacteristicContainer::getCharacteristics
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> save(@NotNull ID parentId, CH characteristic, USER user) throws DomainNotFoundException, BadRequestException {
        return save(
                parentId
                , characteristic
                , BaseCharacteristicContainer::getCharacteristics
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateById(@NotNull ID parentId, @NotNull ID id, CH characteristic, USER user) throws DomainNotFoundException, BadRequestException {
        return updateById(
                parentId
                , id
                , characteristic
                , BaseCharacteristicContainer::getCharacteristics
                , user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> updateByKey(@NotNull ID parentId, @NotBlank String key, CH characteristic, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getChildCriteriaClass().getConstructor().newInstance();
        criteria.setKey(Filter.eq(key));
        return update(parentId, characteristic, criteria, BaseCharacteristicContainer::getCharacteristics, user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> updateMany(@NotNull ID parentId, Collection<CH> characteristics, USER user) throws DomainNotFoundException, BadRequestException {
        return updateMany(
                parentId
                , characteristics
                , BaseCharacteristicContainer::getCharacteristics
                , user);
    }

    @Transactional
    @MethodStats
    default Mono<DTO> update(@NotNull ID parentId, CH characteristic, C criteria, USER user) throws DomainNotFoundException, BadRequestException {
        return update(
                parentId
                , characteristic
                , criteria
                , BaseCharacteristicContainer::getCharacteristics
                , user);
    }
}