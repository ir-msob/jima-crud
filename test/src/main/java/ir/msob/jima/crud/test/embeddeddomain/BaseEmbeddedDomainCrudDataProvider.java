package ir.msob.jima.crud.test.embeddeddomain;

import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.embeddeddomain.BaseEmbeddedDomainCrudService;

import java.io.Serializable;

public interface BaseEmbeddedDomainCrudDataProvider<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , ED extends BaseEmbeddedDomain<ID>
        , DTO extends BaseDto<ID>
        , EDS extends BaseEmbeddedDomainCrudService<ID, USER, DTO>> {

    ED getNewEmbeddedDomain();

    void updateEmbeddedDomain(ED embeddedDomain);

    EDS getEmbeddedDomainService();

}