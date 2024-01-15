package ir.msob.jima.crud.api.kafka.client;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.api.kafka.beans.KafkaAsyncClient;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.channel.message.DtoMessage;
import ir.msob.jima.core.commons.model.channel.message.DtosMessage;
import ir.msob.jima.core.commons.model.channel.message.JsonPatchMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.criteria.SampleCriteria;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.client.BaseCrudAsyncClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CrudKafkaAsyncClient implements BaseCrudAsyncClient {

    private final KafkaAsyncClient asyncClient;

    public static <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> String createChannel(Class<DTO> dtoClass, String operation) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return Constants.getChannel(dtoClass, operation);
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DATA extends ModelType, DTO extends BaseDto<ID>> void send(ChannelMessage<ID, USER, DATA> message, Class<DTO> dtoClass, String operation, Optional<USER> user) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String channel = createChannel(dtoClass, operation);
        asyncClient.send(message, channel, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> delete(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.DELETE, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> deleteMany(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.DELETE_MANY, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> deleteAll(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.DELETE_ALL, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> edit(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<ID, USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.EDIT, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<ID, USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.EDIT_MANY, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> save(Class<DTO> dtoClass, DTO dto, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<ID, USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.SAVE, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> saveMany(Class<DTO> dtoClass, List<DTO> dtos, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<ID, USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.SAVE_MANY, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> update(Class<DTO> dtoClass, DTO dto, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<ID, USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.UPDATE, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> updateMany(Class<DTO> dtoClass, List<DTO> dtos, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<ID, USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.UPDATE_MANY, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> count(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.COUNT, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Void> countAll(Class<DTO> dtoClass, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<ID, SampleCriteria<ID>> data = createData(null);
        ChannelMessage<ID, USER, CriteriaMessage<ID, SampleCriteria<ID>>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.COUNT_ALL, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getOne(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.GET_ONE, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getMany(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.GET_MANY, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getPage(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.GET_PAGE, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getOne(Class<DTO> dtoClass, ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage, Optional<USER> user) {
        send(channelMessage, dtoClass, Operations.GET_ONE, user);
        return Mono.empty();
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Void> getMany(Class<DTO> dtoClass, ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage, Optional<USER> user) {
        send(channelMessage, dtoClass, Operations.GET_MANY, user);
        return Mono.empty();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, C extends BaseCriteria<ID>> ChannelMessage<ID, USER, CriteriaMessage<ID, C>> createChannelMessage(CriteriaMessage<ID, C> data, Map<String, Serializable> metadata, String callback) {
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = new ChannelMessage<>();
        channelMessage.setMetadata(metadata);
        channelMessage.setData(data);
        channelMessage.setCallback(callback);
        return channelMessage;
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, C extends BaseCriteria<ID>> ChannelMessage<ID, USER, JsonPatchMessage<ID, C>> createChannelMessage(JsonPatchMessage<ID, C> data, Map<String, Serializable> metadata, String callback) {
        ChannelMessage<ID, USER, JsonPatchMessage<ID, C>> channelMessage = new ChannelMessage<>();
        channelMessage.setMetadata(metadata);
        channelMessage.setData(data);
        channelMessage.setCallback(callback);
        return channelMessage;
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> ChannelMessage<ID, USER, DtoMessage<ID, DTO>> createChannelMessage(DtoMessage<ID, DTO> data, Map<String, Serializable> metadata, String callback) {
        ChannelMessage<ID, USER, DtoMessage<ID, DTO>> channelMessage = new ChannelMessage<>();
        channelMessage.setMetadata(metadata);
        channelMessage.setData(data);
        channelMessage.setCallback(callback);
        return channelMessage;
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> ChannelMessage<ID, USER, DtosMessage<ID, DTO>> createChannelMessage(DtosMessage<ID, DTO> data, Map<String, Serializable> metadata, String callback) {
        ChannelMessage<ID, USER, DtosMessage<ID, DTO>> channelMessage = new ChannelMessage<>();
        channelMessage.setMetadata(metadata);
        channelMessage.setData(data);
        channelMessage.setCallback(callback);
        return channelMessage;
    }

    public <ID extends Comparable<ID> & Serializable, C extends BaseCriteria<ID>> CriteriaMessage<ID, C> createData(C criteria) {
        CriteriaMessage<ID, C> message = new CriteriaMessage<>();
        message.setCriteria(criteria);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, C extends BaseCriteria<ID>> JsonPatchMessage<ID, C> createMessage(JsonPatch jsonPatch, C criteria) {
        JsonPatchMessage<ID, C> message = new JsonPatchMessage<>();
        message.setCriteria(criteria);
        message.setJsonPatch(jsonPatch);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> DtoMessage<ID, DTO> createMessage(DTO dto) {
        DtoMessage<ID, DTO> message = new DtoMessage<>();
        message.setDto(dto);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> DtosMessage<ID, DTO> createMessage(List<DTO> dtos) {
        DtosMessage<ID, DTO> message = new DtosMessage<>();
        message.setDtos(dtos);
        return message;
    }
}
