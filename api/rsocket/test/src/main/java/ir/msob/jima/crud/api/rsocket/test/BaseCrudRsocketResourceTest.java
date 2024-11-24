package ir.msob.jima.crud.api.rsocket.test;

import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.rsocket.test.read.*;
import ir.msob.jima.crud.api.rsocket.test.write.*;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;

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
 * @param <R>    the type of the repository object, which extends BaseCrudRepository
 * @param <S>    the type of the service object, which extends BaseCrudService
 * @param <DP>   the type of the data provider object, which extends BaseCrudDataProvider
 */
public interface BaseCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends
        BaseCountAllCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseCountCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseGetManyCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseGetOneCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseGetPageCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseDeleteCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseDeleteManyCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseEditManyCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseEditCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseSaveManyCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseSaveCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseUpdateManyCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        BaseUpdateCrudRsocketResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}