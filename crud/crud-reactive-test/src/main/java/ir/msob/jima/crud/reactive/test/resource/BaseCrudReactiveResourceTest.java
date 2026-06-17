package ir.msob.jima.crud.reactive.test.resource;

import ir.msob.jima.platform.api.domain.dto.BaseDtoTypeReference;
import ir.msob.jima.platform.api.element.criteria.BaseElementCriteria;
import ir.msob.jima.platform.api.element.dto.BaseElementDto;
import ir.msob.jima.platform.api.element.element.BaseElement;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.test.resource.BaseResourceTest;

import java.io.Serializable;


public interface BaseCrudReactiveResourceTest<ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseElement<ID>,
        DTO extends BaseElementDto<ID>,
        C extends BaseElementCriteria<ID>>
        extends BaseResourceTest<ID, USER, D, DTO, C>
        , BaseDtoTypeReference<ID, DTO, C> {


}