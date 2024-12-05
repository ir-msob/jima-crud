package ir.msob.jima.crud.api.restful.service.related.characteristic;

import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.related.characteristic.BaseCharacteristicDto;
import ir.msob.jima.core.commons.related.characteristic.Characteristic;
import ir.msob.jima.core.commons.related.characteristic.CharacteristicCriteria;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.related.characteristic.write.*;
import ir.msob.jima.crud.service.related.characteristic.BaseCharacteristicCrudService;

import java.io.Serializable;


public interface BaseCharacteristicCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , CH extends Characteristic<ID>
        , C extends CharacteristicCriteria<ID, CH>
        , RDTO extends BaseCharacteristicDto<ID, CH>
        , S extends BaseCharacteristicCrudService<ID, USER, DTO, CH, C, RDTO>
        > extends
        BaseDeleteByIdCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S>
        , BaseDeleteByKeyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S>
        , BaseDeleteCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S>
        , BaseDeleteManyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S>
        , BaseSaveCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S>
        , BaseSaveManyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S>
        , BaseUpdateByIdCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S>
        , BaseUpdateByKeyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S>
        , BaseUpdateCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S>
        , BaseUpdateManyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, RDTO, S> {


}