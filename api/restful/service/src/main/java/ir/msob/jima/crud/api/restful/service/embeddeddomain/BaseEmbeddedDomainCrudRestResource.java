package ir.msob.jima.crud.api.restful.service.embeddeddomain;

import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.core.commons.embeddeddomain.criteria.BaseEmbeddedCriteria;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.service.embeddeddomain.write.*;
import ir.msob.jima.crud.service.embeddeddomain.BaseEmbeddedDomainCrudService;

import java.io.Serializable;


public interface BaseEmbeddedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , ED extends BaseEmbeddedDomain<ID>
        , EC extends BaseEmbeddedCriteria<ID, ED>
        , DTO extends BaseDto<ID>
        , EDS extends BaseEmbeddedDomainCrudService<ID, USER, DTO>
        > extends
        BaseDeleteByIdEmbeddedDomainCrudRestResource<ID, USER, ED, EC, DTO, EDS>
        , BaseDeleteEmbeddedDomainCrudRestResource<ID, USER, ED, EC, DTO, EDS>
        , BaseDeleteManyEmbeddedDomainCrudRestResource<ID, USER, ED, EC, DTO, EDS>
        , BaseSaveEmbeddedDomainCrudRestResource<ID, USER, ED, DTO, EDS>
        , BaseSaveManyEmbeddedDomainCrudRestResource<ID, USER, ED, DTO, EDS>
        , BaseUpdateByIdEmbeddedDomainCrudRestResource<ID, USER, ED, EC, DTO, EDS>
        , BaseUpdateEmbeddedDomainCrudRestResource<ID, USER, ED, EC, DTO, EDS>
        , BaseUpdateManyEmbeddedDomainCrudRestResource<ID, USER, ED, DTO, EDS> {


}