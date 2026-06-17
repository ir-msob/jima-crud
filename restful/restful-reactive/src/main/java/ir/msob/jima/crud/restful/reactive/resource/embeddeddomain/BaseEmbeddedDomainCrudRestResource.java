package ir.msob.jima.crud.restful.reactive.resource.embeddeddomain;

import ir.msob.jima.crud.reactive.service.embeddeddomain.BaseEmbeddedDomainCrudReactiveService;
import ir.msob.jima.crud.restful.reactive.resource.embeddeddomain.write.*;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.embeddeddomain.criteria.BaseEmbeddedCriteria;
import ir.msob.jima.platform.api.embeddeddomain.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.platform.api.security.BaseUser;

import java.io.Serializable;


public interface BaseEmbeddedDomainCrudRestResource<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , ED extends BaseEmbeddedDomain<ID>
        , EC extends BaseEmbeddedCriteria<ID, ED>
        , DTO extends BaseDomainDto<ID>
        , EDS extends BaseEmbeddedDomainCrudReactiveService<ID, USER, DTO>
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