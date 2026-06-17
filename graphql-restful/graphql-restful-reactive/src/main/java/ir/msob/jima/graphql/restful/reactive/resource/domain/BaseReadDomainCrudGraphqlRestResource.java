package ir.msob.jima.graphql.restful.reactive.resource.domain;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.graphql.restful.reactive.resource.domain.read.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

/**
 * The {@code BaseReadDomainCrudGraphqlRestResource} interface extends several GraphQL resource interfaces,
 * providing read-specific GraphQL operations for a CRUD service.
 * It is generic and allows customization for different types such as ID, USER, D, DTO, C, R, and S.
 * This interface combines methods from various read-childdomain GraphQL resource interfaces.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDomainDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseDomainCriteria<ID, USER>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseReactiveRepository<ID, USER, D, C>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseChildDomainCrudService<ID, USER, D, DTO, C, R>}.
 * @see BaseCountAllDomainCrudGraphqlRestResource
 * @see BaseCountDomainCrudGraphqlRestResource
 * @see BaseGetManyDomainCrudGraphqlRestResource
 * @see BaseGetOneDomainCrudGraphqlRestResource
 * @see BaseGetPageDomainCrudGraphqlRestResource
 */
public interface BaseReadDomainCrudGraphqlRestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,

        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        > extends BaseCountAllDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseCountDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetManyDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetByIdDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetOneDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S>,
        BaseGetPageDomainCrudGraphqlRestResource<ID, USER, D, DTO, C, R, S> {
}
