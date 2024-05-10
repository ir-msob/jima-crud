package ir.msob.jima.crud.sample.kafka.base.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.client.BaseAsyncClient;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.*;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.security.BaseUserService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.api.kafka.service.BaseCrudKafkaListener;
import ir.msob.jima.crud.sample.kafka.base.criteria.ProjectCriteria;
import ir.msob.jima.crud.sample.kafka.base.domain.ProjectDomain;
import ir.msob.jima.crud.sample.kafka.base.dto.ProjectDto;
import ir.msob.jima.crud.sample.kafka.base.repository.MongoCrudRepository;
import ir.msob.jima.crud.sample.kafka.base.security.ProjectUser;
import ir.msob.jima.crud.sample.kafka.base.security.ProjectUserService;
import ir.msob.jima.crud.sample.kafka.base.service.CrudService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;

import java.lang.reflect.Type;

/**
 * @param <D>
 * @param <DTO>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public abstract class CrudKafkaResource<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>
        > implements
        BaseCrudKafkaListener<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S> {

    @Autowired
    ProjectUserService projectUserService;

    @Autowired
    S service;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ConsumerFactory<String, String> consumerFactory;
    @Autowired
    BaseAsyncClient asyncClient;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public ConsumerFactory<String, String> getKafkaConsumerFactory() {
        return consumerFactory;
    }

    @Override
    public BaseAsyncClient getAsyncClient() {
        return asyncClient;
    }

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
