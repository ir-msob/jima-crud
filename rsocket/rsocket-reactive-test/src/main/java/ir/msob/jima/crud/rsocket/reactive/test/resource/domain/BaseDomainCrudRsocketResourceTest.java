package ir.msob.jima.crud.rsocket.reactive.test.resource.domain;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.rsocket.reactive.test.resource.domain.read.*;
import ir.msob.jima.crud.rsocket.reactive.test.resource.domain.write.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

/**
 * This interface aggregates all the CRUD RSocket resource test interfaces.
 * It provides methods for creating, reading, updating, and deleting resources via RSocket.
 *
 * @param <ID>   the type of the ID of the domain object, which must be comparable and serializable
 * @param <USER> the type of the user, which extends BaseUser
 * @param <D>    the type of the domain object, which extends BaseDomain
 * @param <DTO>  the type of the DTO object, which extends BaseDomainDto
 * @param <C>    the type of the criteria object, which extends BaseDomainCriteria
 * @param <R>    the type of the repository object, which extends BaseReactiveRepository
 * @param <S>    the type of the service object, which extends BaseChildDomainCrudReactiveService
 * @param <DP>   the type of the data provider object, which extends BaseChildDomainCrudDataProvider
 */
public interface BaseDomainCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseCountDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseGetManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseGetOneDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseGetPageDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseDeleteDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseDeleteManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseEditManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseEditDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseSaveManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseSaveDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseUpdateManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        BaseUpdateDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, R, S, DP> {
}