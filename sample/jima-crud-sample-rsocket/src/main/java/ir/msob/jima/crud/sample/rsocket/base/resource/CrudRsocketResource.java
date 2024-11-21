package ir.msob.jima.crud.sample.rsocket.base.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.*;
import ir.msob.jima.core.commons.dto.ModelType;
import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.api.rsocket.service.BaseCrudRsocketResource;
import ir.msob.jima.crud.sample.rsocket.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.rsocket.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.rsocket.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.rsocket.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.rsocket.base.security.ProjectUser;
import ir.msob.jima.crud.sample.rsocket.base.security.ProjectUserService;
import ir.msob.jima.crud.sample.rsocket.base.service.CrudService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;

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
    ObjectMapper objectMapper;

    @Autowired
    S service;

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
        return new TypeReference<ChannelMessage<ProjectUser, ModelType>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, CriteriaMessage<ObjectId, C>>> getCriteriaReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, CriteriaMessage<ObjectId, C>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, PageableMessage<ObjectId, C>>> getCriteriaPageReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, PageableMessage<ObjectId, C>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, JsonPatchMessage<ObjectId, C>>> getEditReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, JsonPatchMessage<ObjectId, C>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, DtoMessage<ObjectId, DTO>>> getDtoReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, DtoMessage<ObjectId, DTO>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, DtosMessage<ObjectId, DTO>>> getDtosReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, DtosMessage<ObjectId, DTO>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, IdMessage<ObjectId>>> getIdReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, IdMessage<ObjectId>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, IdJsonPatchMessage<ObjectId>>> getIdJsonPatchReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, IdJsonPatchMessage<ObjectId>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, PageMessage<ObjectId, DTO>>> getPageReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, PageMessage<ObjectId, DTO>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, IdsMessage<ObjectId>>> getIdsReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, IdsMessage<ObjectId>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, LongMessage>> getLongReferenceType() {
        return new TypeReference<ChannelMessage<ProjectUser, LongMessage>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
    }
}
