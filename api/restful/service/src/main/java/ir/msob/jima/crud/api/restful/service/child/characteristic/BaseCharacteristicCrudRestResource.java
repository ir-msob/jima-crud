package ir.msob.jima.crud.api.restful.service.child.characteristic;

import ir.msob.jima.core.commons.child.characteristic.BaseCharacteristicContainer;
import ir.msob.jima.core.commons.child.characteristic.Characteristic;
import ir.msob.jima.core.commons.child.characteristic.CharacteristicCriteria;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.child.characteristic.write.*;
import ir.msob.jima.crud.service.child.characteristic.BaseCharacteristicCrudService;

import java.io.Serializable;


public interface BaseCharacteristicCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CH extends Characteristic<ID>
        , C extends CharacteristicCriteria<ID, CH>
        , CNT extends BaseCharacteristicContainer<ID, CH>
        , DTO extends BaseDto<ID> & BaseCharacteristicContainer<ID, CH>

        , S extends BaseCharacteristicCrudService<ID, USER, CH, C, CNT, DTO>
        > extends
        BaseDeleteByIdCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S>
        , BaseDeleteByKeyCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S>
        , BaseDeleteCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S>
        , BaseDeleteManyCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S>
        , BaseSaveCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S>
        , BaseSaveManyCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S>
        , BaseUpdateByIdCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S>
        , BaseUpdateByKeyCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S>
        , BaseUpdateCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S>
        , BaseUpdateManyCharacteristicCrudRestResource<ID, USER, CH, C, CNT, DTO, S> {


}