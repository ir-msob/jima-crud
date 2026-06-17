package ir.msob.jima.crud.kafka.reactive.client;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.crud.reactive.client.BaseCrudEventReactiveClient;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.ChannelUtil;
import ir.msob.jima.platform.api.channel.message.*;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.methodstats.MethodStats;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.shared.ModelType;
import ir.msob.jima.platform.reactive.event.publisher.BaseEventReactivePublisher;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jspecify.annotations.NonNull;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This class implements asynchronous CRUD operations using Kafka messages. It extends the {@link BaseCrudEventReactiveClient}
 * interface and provides methods for sending CRUD requests to a Kafka topic.
 */
@RequiredArgsConstructor
public class DomainCrudKafkaAsyncClient implements BaseCrudEventReactiveClient {

    private final BaseEventReactivePublisher eventReactivePublisher;

    public static <ID extends Comparable<ID> & Serializable, DTO extends BaseDomainDto<ID>> String createChannel(Class<DTO> dtoClass, String operation) {
        return ChannelUtil.getChannel(dtoClass, operation);
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DATA extends ModelType, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> send(ChannelMessage<USER, DATA> message, Class<DTO> dtoClass, String operation, USER user) {
        String channel = createChannel(dtoClass, operation);
        return eventReactivePublisher.send(channel, message, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> deleteById(Class<DTO> dtoClass, ID id, Map<String, Serializable> metadata, String callback, USER user) {
        IdMessage<ID> data = createData(id);
        ChannelMessage<USER, IdMessage<ID>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.DELETE_BY_ID, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> delete(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.DELETE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> deleteMany(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.DELETE_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> deleteAll(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.DELETE_ALL, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> editById(Class<DTO> dtoClass, ID id, JsonPatch jsonPatch, Map<String, Serializable> metadata, String callback, USER user) {
        IdJsonPatchMessage<ID> data = createMessage(jsonPatch, id);
        ChannelMessage<USER, IdJsonPatchMessage<ID>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.EDIT_BY_ID, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> edit(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.EDIT, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.EDIT_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> save(Class<DTO> dtoClass, DTO dto, Map<String, Serializable> metadata, String callback, USER user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.SAVE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> save(Class<DTO> dtoClass, DTO dto, USER user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        return send(channelMessage, dtoClass, Operations.SAVE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> saveMany(Class<DTO> dtoClass, List<DTO> dtos, Map<String, Serializable> metadata, String callback, USER user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.SAVE_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> updateById(Class<DTO> dtoClass, ID id, DTO dto, Map<String, Serializable> metadata, String callback, USER user) {
        DtoMessage<ID, DTO> data = createMessage(id, dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.UPDATE_BY_ID, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> update(Class<DTO> dtoClass, DTO dto, Map<String, Serializable> metadata, String callback, USER user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.UPDATE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> updateMany(Class<DTO> dtoClass, List<DTO> dtos, Map<String, Serializable> metadata, String callback, USER user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.UPDATE_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> count(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.COUNT, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> countAll(Class<DTO> dtoClass, Map<String, Serializable> metadata, String callback, USER user) {
        ChannelMessage<USER, ModelType> channelMessage = createChannelMessage(new ModelType(), metadata, callback);
        return send(channelMessage, dtoClass, Operations.COUNT_ALL, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> Mono<@NonNull Void> getById(Class<DTO> dtoClass, ID id, Map<String, Serializable> metadata, String callback, USER user) {
        IdMessage<ID> data = createData(id);
        ChannelMessage<USER, IdMessage<ID>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.GET_BY_ID, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> getOne(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.GET_ONE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> getMany(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.GET_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> getPage(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        return send(channelMessage, dtoClass, Operations.GET_PAGE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> getOne(Class<DTO> dtoClass, ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage, USER user) {
        return send(channelMessage, dtoClass, Operations.GET_ONE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>, C extends BaseDomainCriteria<ID>> Mono<@NonNull Void> getMany(Class<DTO> dtoClass, ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage, USER user) {
        return send(channelMessage, dtoClass, Operations.GET_MANY, user);
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser> ChannelMessage<USER, IdMessage<ID>> createChannelMessage(IdMessage<ID> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, IdMessage<ID>>builder()
                .key(data.getId() != null ? String.valueOf(data.getId()) : null)
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();

    }

    public <USER extends BaseUser> ChannelMessage<USER, ModelType> createChannelMessage(ModelType data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, ModelType>builder()
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, C extends BaseDomainCriteria<ID>> ChannelMessage<USER, CriteriaMessage<ID, C>> createChannelMessage(CriteriaMessage<ID, C> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, CriteriaMessage<ID, C>>builder()
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser> ChannelMessage<USER, IdJsonPatchMessage<ID>> createChannelMessage(IdJsonPatchMessage<ID> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, IdJsonPatchMessage<ID>>builder()
                .key(data.getId() != null ? String.valueOf(data.getId()) : null)
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, C extends BaseDomainCriteria<ID>> ChannelMessage<USER, JsonPatchMessage<ID, C>> createChannelMessage(JsonPatchMessage<ID, C> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, JsonPatchMessage<ID, C>>builder()
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> ChannelMessage<USER, DtoMessage<ID, DTO>> createChannelMessage(DtoMessage<ID, DTO> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, DtoMessage<ID, DTO>>builder()
                .key(data.getId() != null ? String.valueOf(data.getId()) : null)
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> ChannelMessage<USER, DtoMessage<ID, DTO>> createChannelMessage(DtoMessage<ID, DTO> data) {
        return ChannelMessage.<USER, DtoMessage<ID, DTO>>builder()
                .key(data.getId() != null ? String.valueOf(data.getId()) : null)
                .data(data)
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDomainDto<ID>> ChannelMessage<USER, DtosMessage<ID, DTO>> createChannelMessage(DtosMessage<ID, DTO> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, DtosMessage<ID, DTO>>builder()
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable> IdMessage<ID> createData(ID id) {
        IdMessage<ID> message = new IdMessage<>();
        message.setId(id);
        return message;
    }


    public <ID extends Comparable<ID> & Serializable, C extends BaseDomainCriteria<ID>> CriteriaMessage<ID, C> createData(C criteria) {
        CriteriaMessage<ID, C> message = new CriteriaMessage<>();
        message.setCriteria(criteria);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, C extends BaseDomainCriteria<ID>> JsonPatchMessage<ID, C> createMessage(JsonPatch jsonPatch, C criteria) {
        JsonPatchMessage<ID, C> message = new JsonPatchMessage<>();
        message.setCriteria(criteria);
        message.setJsonPatch(jsonPatch);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable> IdJsonPatchMessage<ID> createMessage(JsonPatch jsonPatch, ID id) {
        IdJsonPatchMessage<ID> message = new IdJsonPatchMessage<>();
        message.setId(id);
        message.setJsonPatch(jsonPatch);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDomainDto<ID>> DtoMessage<ID, DTO> createMessage(DTO dto) {
        DtoMessage<ID, DTO> message = new DtoMessage<>();
        message.setDto(dto);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDomainDto<ID>> DtoMessage<ID, DTO> createMessage(ID id, DTO dto) {
        DtoMessage<ID, DTO> message = new DtoMessage<>();
        message.setId(id);
        message.setDto(dto);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDomainDto<ID>> DtosMessage<ID, DTO> createMessage(List<DTO> dtos) {
        DtosMessage<ID, DTO> message = new DtosMessage<>();
        message.setDtos(dtos);
        return message;
    }
}
