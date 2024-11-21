package ir.msob.jima.crud.api.grpc.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.grpc.service.read.*;
import ir.msob.jima.crud.api.grpc.service.write.*;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * The abstract class {@code BaseCrudGrpcResource} provides a base implementation for gRPC (Google Remote Procedure Call) services in a CRUD (Create, Read, Update, Delete) context.
 * It implements the {@link ParentCrudGrpcResource} interface, offering CRUD functionality.
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
 * @see ParentCrudGrpcResource
 */
public abstract class BaseCrudGrpcResource<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>
        > implements
        BaseCountAllCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseCountCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetManyCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetByIdCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetOneCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseGetPageCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteByIdCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseDeleteManyCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditByIdCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseEditManyCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseSaveManyCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateByIdCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S>,
        BaseUpdateManyCrudGrpcResource<ID, USER, D, DTO, C, Q, R, S> {

    @Autowired
    ObjectMapper objectMapper;

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