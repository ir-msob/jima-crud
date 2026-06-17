package ir.msob.jima.crud.grpc.reactive.test.resource.domain;

import ir.msob.jima.crud.grpc.reactive.test.resource.domain.read.*;
import ir.msob.jima.crud.grpc.reactive.test.resource.domain.write.*;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

/**
 * The {@code BaseDomainCrudGrpcReactiveResourceTest} interface represents a set of gRPC-specific test methods for CRUD operations.
 * It extends multiple interfaces, each representing a specific CRUD operation, providing gRPC-specific testing capabilities.
 * <p>
 * The interface includes test methods for counting, getting, deleting, editing, saving, and updating entities using gRPC API.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountAllDomainCrudGrpcReactiveResourceTest
 * @see BaseCountDomainCrudGrpcReactiveResourceTest
 * @see BaseGetManyDomainCrudGrpcReactiveResourceTest
 * @see BaseGetByIdDomainCrudGrpcReactiveResourceTest
 * @see BaseGetOneDomainCrudGrpcReactiveResourceTest
 * @see BaseGetPageDomainCrudGrpcReactiveResourceTest
 * @see BaseDeleteByIdDomainCrudGrpcReactiveResourceTest
 * @see BaseDeleteDomainCrudGrpcReactiveResourceTest
 * @see BaseDeleteManyDomainCrudGrpcReactiveResourceTest
 * @see BaseEditManyDomainCrudGrpcReactiveResourceTest
 * @see BaseEditByIdDomainCrudGrpcReactiveResourceTest
 * @see BaseEditDomainCrudGrpcReactiveResourceTest
 * @see BaseSaveManyDomainCrudGrpcReactiveResourceTest
 * @see BaseSaveDomainCrudGrpcReactiveResourceTest
 * @see BaseUpdateManyDomainCrudGrpcReactiveResourceTest
 * @see BaseUpdateByIdDomainCrudGrpcReactiveResourceTest
 * @see BaseUpdateDomainCrudGrpcReactiveResourceTest
 */
public interface BaseDomainCrudGrpcReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateDomainCrudGrpcReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {
}