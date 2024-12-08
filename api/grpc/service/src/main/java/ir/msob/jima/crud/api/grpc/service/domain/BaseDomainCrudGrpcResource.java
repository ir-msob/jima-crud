package ir.msob.jima.crud.api.grpc.service.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.grpc.service.domain.read.*;
import ir.msob.jima.crud.api.grpc.service.domain.write.*;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * The abstract class {@code BaseDomainCrudGrpcResource} provides a base implementation for gRPC (Google Remote Procedure Call) services in a CRUD (Create, Read, Update, Delete) context.
 * It implements the {@link ParentDomainCrudGrpcResource} interface, offering CRUD functionality.
 * This class is intended to be extended by specific gRPC service implementations.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <Q>    The type of query used for retrieving entities.
 * @param <R>    The type of CRUD repository used for database operations.
 * @param <S>    The type of CRUD service providing business logic.
 * @see ParentDomainCrudGrpcResource
 */
@RequiredArgsConstructor
public abstract class BaseDomainCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>
        > implements
        BaseCountAllDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetByIdDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteByIdDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditByIdDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateByIdDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyDomainCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S> {

    private final ObjectMapper objectMapper;

    /**
     * Returns the ObjectMapper instance.
     *
     * @return The ObjectMapper instance.
     */
    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}