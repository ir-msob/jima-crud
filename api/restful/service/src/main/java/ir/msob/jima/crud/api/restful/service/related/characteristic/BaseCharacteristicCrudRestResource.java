package ir.msob.jima.crud.api.restful.service.related.characteristic;

import ir.msob.jima.core.commons.dto.BaseDto;
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
        , S extends BaseCharacteristicCrudService<ID, USER, DTO, CH, C>
        > extends
        BaseDeleteByIdCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S>
        , BaseDeleteByKeyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S>
        , BaseDeleteCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S>
        , BaseDeleteManyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S>
        , BaseSaveCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S>
        , BaseSaveManyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S>
        , BaseUpdateByIdCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S>
        , BaseUpdateByKeyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S>
        , BaseUpdateCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S>
        , BaseUpdateManyCharacteristicCrudRestResource<ID, USER, DTO, CH, C, S> {


}