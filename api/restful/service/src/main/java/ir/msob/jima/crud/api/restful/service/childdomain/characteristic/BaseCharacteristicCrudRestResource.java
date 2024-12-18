package ir.msob.jima.crud.api.restful.service.childdomain.characteristic;

import ir.msob.jima.core.commons.childdomain.characteristic.Characteristic;
import ir.msob.jima.core.commons.childdomain.characteristic.CharacteristicCriteria;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.childdomain.characteristic.write.*;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;

import java.io.Serializable;


public interface BaseCharacteristicCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends Characteristic<ID>
        , CC extends CharacteristicCriteria<ID, CD>
        , DTO extends BaseDto<ID>
        , CS extends BaseChildDomainCrudService<ID, USER, DTO>
        > extends
        BaseDeleteByIdCharacteristicCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteByKeyCharacteristicCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteCharacteristicCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseDeleteManyCharacteristicCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseSaveCharacteristicCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseSaveManyCharacteristicCrudRestResource<ID, USER, CD, DTO, CS>
        , BaseUpdateByIdCharacteristicCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateByKeyCharacteristicCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateCharacteristicCrudRestResource<ID, USER, CD, CC, DTO, CS>
        , BaseUpdateManyCharacteristicCrudRestResource<ID, USER, CD, DTO, CS> {


}