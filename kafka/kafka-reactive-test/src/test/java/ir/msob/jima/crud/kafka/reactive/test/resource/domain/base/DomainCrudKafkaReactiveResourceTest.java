package ir.msob.jima.crud.kafka.reactive.test.resource.domain.base;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.kafka.reactive.test.resource.domain.BaseDomainCrudReactiveKafkaListenerTest;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudDataProvider;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudService;
import ir.msob.jima.crud.reactive.testing.base.MongoDomainCrudReactiveRepository;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.ChannelUtil;
import ir.msob.jima.platform.api.channel.message.*;
import ir.msob.jima.platform.api.shared.ModelType;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import ir.msob.jima.platform.testing.dto.ProjectDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;


public abstract class DomainCrudKafkaReactiveResourceTest<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudReactiveRepository<D, C>,
        S extends DomainCrudService<D, DTO, C, R>,
        DP extends DomainCrudDataProvider<D, DTO, C, R, S>>
        implements BaseDomainCrudReactiveKafkaListenerTest<String, ProjectUser, D, DTO, C, R, S, DP> {

    @Autowired
    DP dataProvider;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProjectUserService projectUserService;
    @Autowired
    KafkaTemplate<@NonNull String, @NonNull String> kafkaTemplate;
    @Autowired
    ConsumerFactory<@NonNull String, @NonNull String> consumerFactory;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Override
    public ProjectUser getSampleUser() {
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
    public TypeReference<ChannelMessage<ProjectUser, ModelType>> getChannelMessageModelTypeReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, CriteriaMessage<String, C>>> getChannelMessageCriteriaReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, PageableMessage<String, C>>> getChannelMessagePageableReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, PageMessage<String, DTO>>> getChannelMessagePageReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, JsonPatchMessage<String, C>>> getChannelMessageJsonPatchReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, DtoMessage<String, DTO>>> getChannelMessageDtoReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, DtosMessage<String, DTO>>> getChannelMessageDtosReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, IdMessage<String>>> getChannelMessageIdReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, IdsMessage<String>>> getChannelMessageIdsReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, IdJsonPatchMessage<String>>> getChannelMessageIdJsonPatchReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public TypeReference<ChannelMessage<ProjectUser, LongMessage>> getChannelMessageLongReferenceType() {
        return new TypeReference<>() {
        };
    }

    @Override
    public ConsumerFactory<@NonNull String, @NonNull String> getConsumerFactory() {
        return consumerFactory;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public KafkaTemplate<@NonNull String, @NonNull String> getKafkaTemplate() {
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
