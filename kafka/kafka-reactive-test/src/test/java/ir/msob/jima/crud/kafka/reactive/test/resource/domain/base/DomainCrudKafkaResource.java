package ir.msob.jima.crud.kafka.reactive.test.resource.domain.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.crud.kafka.reactive.resource.domain.BaseDomainCrudKafkaListener;
import ir.msob.jima.crud.reactive.testing.base.DomainCrudService;
import ir.msob.jima.crud.reactive.testing.base.MongoDomainCrudReactiveRepository;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.message.*;
import ir.msob.jima.platform.api.shared.ModelType;
import ir.msob.jima.platform.reactive.channel.ChannelMessageReactivePublisher;
import ir.msob.jima.platform.reactive.event.publisher.BaseEventReactivePublisher;
import ir.msob.jima.platform.testing.criteria.ProjectCriteria;
import ir.msob.jima.platform.testing.domain.ProjectDomain;
import ir.msob.jima.platform.testing.dto.ProjectDto;
import ir.msob.jima.platform.testing.security.ProjectUser;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;

/**
 * @param <D>
 * @param <DTO>
 * @param <C>
 * @param <R>
 * @param <S>
 * @author Yaqub Abdi
 */
public abstract class DomainCrudKafkaResource<
        D extends ProjectDomain,
        DTO extends ProjectDto,
        C extends ProjectCriteria,
        R extends MongoDomainCrudReactiveRepository<D, C>,
        S extends DomainCrudService<D, DTO, C, R>
        > implements
        BaseDomainCrudKafkaListener<String, ProjectUser, D, DTO, C, R, S> {

    @Getter
    @Autowired
    ProjectUserService userService;

    @Autowired
    S service;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ChannelMessageReactivePublisher channelMessageReactivePublisher;
    @Autowired
    ConsumerFactory<@NonNull String, @NonNull String> consumerFactory;
    @Autowired
    BaseEventReactivePublisher eventReactivePublisher;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public ConsumerFactory<@NonNull String, @NonNull String> getKafkaConsumerFactory() {
        return consumerFactory;
    }

    @Override
    public ChannelMessageReactivePublisher getChannelMessagePublisher() {
        return channelMessageReactivePublisher;
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
}
