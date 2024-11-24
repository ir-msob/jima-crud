package ir.msob.jima.crud.api.grpc.test.domain;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.test.domain.read.*;
import ir.msob.jima.crud.api.grpc.test.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;

import java.io.Serializable;

/**
 * The {@code BaseCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for CRUD operations.
 * It extends multiple interfaces, each representing a specific CRUD operation, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes test methods for counting, getting, deleting, editing, saving, and updating entities using gRPC API.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <Q>    The type of query used for retrieving entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountAllCrudGrpcResourceTest
 * @see BaseCountCrudGrpcResourceTest
 * @see BaseGetManyCrudGrpcResourceTest
 * @see BaseGetByIdCrudGrpcResourceTest
 * @see BaseGetOneCrudGrpcResourceTest
 * @see BaseGetPageCrudGrpcResourceTest
 * @see BaseDeleteByIdCrudGrpcResourceTest
 * @see BaseDeleteCrudGrpcResourceTest
 * @see BaseDeleteManyCrudGrpcResourceTest
 * @see BaseEditManyCrudGrpcResourceTest
 * @see BaseEditByIdCrudGrpcResourceTest
 * @see BaseEditCrudGrpcResourceTest
 * @see BaseSaveManyCrudGrpcResourceTest
 * @see BaseSaveCrudGrpcResourceTest
 * @see BaseUpdateManyCrudGrpcResourceTest
 * @see BaseUpdateByIdCrudGrpcResourceTest
 * @see BaseUpdateCrudGrpcResourceTest
 */
public interface BaseCrudGrpcResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends
        BaseCountAllCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseCountCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetByIdCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetOneCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetPageCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteByIdCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditByIdCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateManyCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateByIdCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}