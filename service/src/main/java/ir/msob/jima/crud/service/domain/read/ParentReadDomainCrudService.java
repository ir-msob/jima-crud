package ir.msob.jima.crud.service.domain.read;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.ParentDomainCrudService;

import java.io.Serializable;

/**
 * This interface defines a parent service for reading entities based on specific criteria.
 * It provides methods that can be overridden to customize the behavior before and after the get operation.
 *
 * @param <ID>   The type of entity IDs. It must be comparable and serializable.
 * @param <USER> The type of the user associated with the operations. It must extend BaseUser.
 * @param <D>    The type of the domain entities. It must extend BaseDomain.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities. It must extend BaseDto.
 * @param <C>    The type of the criteria used for filtering entities. It must extend BaseCriteria.
 * @param <R>    The type of the CRUD repository used for data access. It must extend BaseDomainCrudRepository.
 */
public interface ParentReadDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>> extends ParentDomainCrudService<ID, USER, D, DTO, C, R> {


}