package ir.msob.jima.crud.reactive.test.dataprovider.embeddeddomain;

import ir.msob.jima.crud.reactive.service.embeddeddomain.BaseEmbeddedDomainCrudReactiveService;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.embeddeddomain.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.platform.api.security.BaseUser;

import java.io.Serializable;

public interface BaseEmbeddedDomainCrudDataProvider<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , ED extends BaseEmbeddedDomain<ID>
        , DTO extends BaseDomainDto<ID>
        , EDS extends BaseEmbeddedDomainCrudReactiveService<ID, USER, DTO>> {

    ED getNewEmbeddedDomain();

    void updateEmbeddedDomain(ED embeddedDomain);

    EDS getEmbeddedDomainService();

}