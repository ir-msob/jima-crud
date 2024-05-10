package ir.msob.jima.crud.api.rsocket.service.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.*;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.core.ral.mongo.it.criteria.ProjectCriteria;
import ir.msob.jima.core.ral.mongo.it.domain.ProjectDomain;
import ir.msob.jima.core.ral.mongo.it.dto.ProjectDto;
import ir.msob.jima.core.ral.mongo.it.security.ProjectUser;
import ir.msob.jima.crud.api.rsocket.service.BaseCrudRsocketResource;
import ir.msob.jima.crud.ral.mongo.it.base.CrudService;
import ir.msob.jima.crud.ral.mongo.it.base.MongoCrudRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <D>
 * @param <DTO>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public abstract class CrudRsocketResource<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>
        > implements
        BaseCrudRsocketResource<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S> {

    @Autowired
    ProjectUserService projectUserService;

    @Autowired
    S service;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public BaseUserService getUserService() {
        return projectUserService;
    }

    @Override
    public S getService() {
        return service;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, ModelType>> getModelTypeReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, CriteriaMessage<ObjectId, C>>> getCriteriaReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, PageableMessage<ObjectId, C>>> getCriteriaPageReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, JsonPatchMessage<ObjectId, C>>> getEditReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, DtoMessage<ObjectId, DTO>>> getDtoReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, IdMessage<ObjectId>>> getIdReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, IdJsonPatchMessage<ObjectId>>> getIdJsonPatchReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, PageMessage<ObjectId, DTO>>> getPageReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, DtosMessage<ObjectId, DTO>>> getDtosReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, IdsMessage<ObjectId>>> getIdsReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, LongMessage>> getLongReferenceType() {
        return new TypeReference<>() {
        };
    }
}
