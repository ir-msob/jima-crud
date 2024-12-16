package ir.msob.jima.crud.api.rsocket.test;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.rsocket.test.read.*;
import ir.msob.jima.crud.api.rsocket.test.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;

import java.io.Serializable;

/**
 * This interface aggregates all the CRUD RSocket resource test interfaces.
 * It provides methods for creating, reading, updating, and deleting resources via RSocket.
 *
 * @param <ID>   the type of the ID of the domain object, which must be comparable and serializable
 * @param <USER> the type of the user, which extends BaseUser
 * @param <D>    the type of the domain object, which extends BaseDomain
 * @param <DTO>  the type of the DTO object, which extends BaseDto
 * @param <C>    the type of the criteria object, which extends BaseCriteria
 * @param <Q>    the type of the query object, which extends BaseQuery
 * @param <R>    the type of the repository object, which extends BaseDomainCrudRepository
 * @param <S>    the type of the service object, which extends BaseDomainCrudService
 * @param <DP>   the type of the data provider object, which extends BaseDomainCrudDataProvider
 */
public interface BaseDomainCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends
        BaseCountAllDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseCountDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseGetManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseGetOneDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseGetPageDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseDeleteDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseDeleteManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseEditManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseEditDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseSaveManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseSaveDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseUpdateManyDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseUpdateDomainCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}