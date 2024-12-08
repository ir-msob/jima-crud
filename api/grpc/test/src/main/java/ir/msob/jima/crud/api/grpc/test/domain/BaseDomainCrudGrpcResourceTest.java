package ir.msob.jima.crud.api.grpc.test.domain;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.test.domain.read.*;
import ir.msob.jima.crud.api.grpc.test.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.BaseDomainCrudDataProvider;

import java.io.Serializable;

/**
 * The {@code BaseDomainCrudGrpcResourceTest} interface represents a set of gRPC-specific test methods for CRUD operations.
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
 * @see BaseCountAllDomainCrudGrpcResourceTest
 * @see BaseCountDomainCrudGrpcResourceTest
 * @see BaseGetManyDomainCrudGrpcResourceTest
 * @see BaseGetByIdDomainCrudGrpcResourceTest
 * @see BaseGetOneDomainCrudGrpcResourceTest
 * @see BaseGetPageDomainCrudGrpcResourceTest
 * @see BaseDeleteByIdDomainCrudGrpcResourceTest
 * @see BaseDeleteDomainCrudGrpcResourceTest
 * @see BaseDeleteManyDomainCrudGrpcResourceTest
 * @see BaseEditManyDomainCrudGrpcResourceTest
 * @see BaseEditByIdDomainCrudGrpcResourceTest
 * @see BaseEditDomainCrudGrpcResourceTest
 * @see BaseSaveManyDomainCrudGrpcResourceTest
 * @see BaseSaveDomainCrudGrpcResourceTest
 * @see BaseUpdateManyDomainCrudGrpcResourceTest
 * @see BaseUpdateByIdDomainCrudGrpcResourceTest
 * @see BaseUpdateDomainCrudGrpcResourceTest
 */
public interface BaseDomainCrudGrpcResourceTest<
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
        BaseCountAllDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseCountDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetManyDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetByIdDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetOneDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseGetPageDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteByIdDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseDeleteManyDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditManyDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditByIdDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseEditDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveManyDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseSaveDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateManyDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateByIdDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>
        , BaseUpdateDomainCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {
}