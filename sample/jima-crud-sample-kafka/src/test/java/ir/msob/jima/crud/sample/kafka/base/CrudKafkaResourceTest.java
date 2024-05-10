package ir.msob.jima.crud.sample.kafka.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.*;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.security.BaseTokenService;
import ir.msob.jima.core.ral.mongo.commons.query.QueryBuilder;
import ir.msob.jima.crud.api.kafka.client.ChannelUtil;
import ir.msob.jima.crud.api.kafka.test.BaseCrudKafkaListenerTest;
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
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;


public abstract class CrudKafkaResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoCrudRepository<D, C>,
        S extends CrudService<D, DTO, C, R>,
        DP extends CrudDataProvider<D, DTO, C, R, S>>
        implements BaseCrudKafkaListenerTest<ObjectId, ProjectUser, D, DTO, C, QueryBuilder, R, S, DP> {

    @Autowired
    DP dataProvider;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProjectUserService projectUserService;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Autowired
    ConsumerFactory<String, String> consumerFactory;

    @Override
    public Optional<ProjectUser> getSampleUser() {
        return getDataProvider().getSampleUser();
    }

    @Override
    public DP getDataProvider() {
        return dataProvider;
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

    @Override
    public ConsumerFactory<String, String> getConsumerFactory() {
        return consumerFactory;
    }

    @Override
    public Duration getSleepDuration() {
        return Duration.of(1, ChronoUnit.SECONDS);
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    @Override
    public String prepareTopic(String operation) {
        return ChannelUtil.getChannel(getDtoClass(), operation);
    }

    @Override
    public String prepareCallbackTopic() {
        return UUID.randomUUID().toString();
    }

}
