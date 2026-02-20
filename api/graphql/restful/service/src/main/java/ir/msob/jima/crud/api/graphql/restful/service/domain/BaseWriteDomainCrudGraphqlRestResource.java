package ir.msob.jima.crud.api.graphql.restful.service.domain;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.service.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;

import java.io.Serializable;

/**
 * The {@code BaseWriteDomainCrudGraphqlRestResource} interface extends several GraphQL resource interfaces,
 * providing write-specific GraphQL operations for a CRUD service.
 * It is generic and allows customization for different types such as ID, USER, D, DTO, C, R, and S.
 * This interface combines methods from various write-childdomain GraphQL resource interfaces.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseDomainCrudRepository<ID, USER, D, C>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseChildDomainCrudService<ID, USER, D, DTO, C, R>}.
 * @see BaseDeleteDomainCrudGraphqlRestResource
 * @see BaseDeleteManyDomainCrudGraphqlRestResource
 * @see BaseEditDomainCrudGraphqlRestResource
 * @see BaseEditManyDomainCrudGraphqlRestResource
 * @see BaseUpdateDomainCrudGraphqlRestResource
 * @see BaseUpdateManyDomainCrudGraphqlRestResource
 * @see BaseSaveDomainCrudGraphqlRestResource
 * @see BaseSaveManyDomainCrudGraphqlRestResource
 */
public interface BaseWriteDomainCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        > extends BaseDeleteDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteByIdDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseDeleteManyDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseEditByIdDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseEditDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseEditManyDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateByIdDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseUpdateManyDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseSaveDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseSaveManyDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S> {
}
