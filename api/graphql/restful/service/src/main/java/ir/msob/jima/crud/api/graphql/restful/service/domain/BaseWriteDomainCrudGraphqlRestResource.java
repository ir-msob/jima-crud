package ir.msob.jima.crud.api.graphql.restful.service.domain;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.graphql.restful.service.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;

import java.io.Serializable;

/**
 * The {@code BaseWriteDomainCrudGraphqlRestResource} interface extends several GraphQL resource interfaces,
 * providing write-specific GraphQL operations for a CRUD service.
 * It is generic and allows customization for different types such as ID, USER, D, DTO, C, Q, R, and S.
 * This interface combines methods from various write-child GraphQL resource interfaces.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseDomainCrudRepository<ID, USER, D, C, Q>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>}.
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
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>
        > extends BaseDeleteDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteByIdDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditByIdDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateByIdDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, Q, R, S> {
}
