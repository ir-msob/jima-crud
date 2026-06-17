package ir.msob.jima.crud.grpc.test.service.domain;

import ir.msob.jima.crud.grpc.test.service.domain.read.*;
import ir.msob.jima.crud.grpc.test.service.domain.write.*;
import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.test.dataprovider.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

/**
 * The {@code BaseDomainCrudGrpcTestResource} interface represents a set of gRPC-specific test methods for CRUD operations.
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
 * @see BaseCountAllDomainCrudGrpcTestResource
 * @see BaseCountDomainCrudGrpcTestResource
 * @see BaseGetManyDomainCrudGrpcTestResource
 * @see BaseGetByIdDomainCrudGrpcTestResource
 * @see BaseGetOneDomainCrudGrpcTestResource
 * @see BaseGetPageDomainCrudGrpcTestResource
 * @see BaseDeleteByIdDomainCrudGrpcTestResource
 * @see BaseDeleteDomainCrudGrpcTestResource
 * @see BaseDeleteManyDomainCrudGrpcTestResource
 * @see BaseEditManyDomainCrudGrpcTestResource
 * @see BaseEditByIdDomainCrudGrpcTestResource
 * @see BaseEditDomainCrudGrpcTestResource
 * @see BaseSaveManyDomainCrudGrpcTestResource
 * @see BaseSaveDomainCrudGrpcTestResource
 * @see BaseUpdateManyDomainCrudGrpcTestResource
 * @see BaseUpdateByIdDomainCrudGrpcTestResource
 * @see BaseUpdateDomainCrudGrpcTestResource
 */
public interface BaseDomainCrudGrpcTestResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends
        BaseCountAllDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseCountDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetManyDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetByIdDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetOneDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseGetPageDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteByIdDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseDeleteManyDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditManyDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditByIdDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseEditDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveManyDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseSaveDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateManyDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateByIdDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP>
        , BaseUpdateDomainCrudGrpcTestResource<ID, USER, D, DTO, C, R, S, DP> {
}