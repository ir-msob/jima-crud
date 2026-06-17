package ir.msob.jima.crud.api.service;

import ir.msob.jima.platform.api.element.criteria.BaseElementCriteria;
import ir.msob.jima.platform.api.element.dto.BaseElementDto;
import ir.msob.jima.platform.api.element.element.BaseElement;
import ir.msob.jima.platform.api.repository.BaseRepositoryParent;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.service.BaseServiceParent;

import java.io.Serializable;

public interface BaseCrudServiceParent<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseElement<ID>,
        DTO extends BaseElementDto<ID>,
        C extends BaseElementCriteria<ID>,
        R extends BaseRepositoryParent<ID, D, C>>
        extends BaseServiceParent<ID, USER, D, DTO, C, R> {

}
