package ir.msob.jima.crud.api.kafka.client.domain;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.api.kafka.beans.KafkaAsyncClient;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.*;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.ModelType;
import ir.msob.jima.crud.api.kafka.client.ChannelUtil;
import ir.msob.jima.crud.client.BaseCrudAsyncClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This class implements asynchronous CRUD operations using Kafka messages. It extends the {@link BaseCrudAsyncClient}
 * interface and provides methods for sending CRUD requests to a Kafka topic.
 */
@Component
@RequiredArgsConstructor
public class DomainCrudKafkaAsyncClient implements BaseCrudAsyncClient {

    private final KafkaAsyncClient asyncClient;

    public static <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> String createChannel(Class<DTO> dtoClass, String operation) {
        return ChannelUtil.getChannel(dtoClass, operation);
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DATA extends ModelType, DTO extends BaseDto<ID>> void send(ChannelMessage<USER, DATA> message, Class<DTO> dtoClass, String operation, USER user) {
        String channel = createChannel(dtoClass, operation);
        asyncClient.send(message, channel, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void deleteById(Class<DTO> dtoClass, ID id, Map<String, Serializable> metadata, String callback, USER user) {
        IdMessage<ID> data = createData(id);
        ChannelMessage<USER, IdMessage<ID>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.DELETE_BY_ID, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void delete(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.DELETE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void deleteMany(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.DELETE_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void deleteAll(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.DELETE_ALL, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void editById(Class<DTO> dtoClass, ID id, JsonPatch jsonPatch, Map<String, Serializable> metadata, String callback, USER user) {
        IdJsonPatchMessage<ID> data = createMessage(jsonPatch, id);
        ChannelMessage<USER, IdJsonPatchMessage<ID>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.EDIT_BY_ID, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void edit(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.EDIT, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.EDIT_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void save(Class<DTO> dtoClass, DTO dto, Map<String, Serializable> metadata, String callback, USER user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.SAVE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void saveMany(Class<DTO> dtoClass, List<DTO> dtos, Map<String, Serializable> metadata, String callback, USER user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.SAVE_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void updateById(Class<DTO> dtoClass, ID id, DTO dto, Map<String, Serializable> metadata, String callback, USER user) {
        DtoMessage<ID, DTO> data = createMessage(id, dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.UPDATE_BY_ID, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void update(Class<DTO> dtoClass, DTO dto, Map<String, Serializable> metadata, String callback, USER user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.UPDATE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void updateMany(Class<DTO> dtoClass, List<DTO> dtos, Map<String, Serializable> metadata, String callback, USER user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.UPDATE_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void count(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.COUNT, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void countAll(Class<DTO> dtoClass, Map<String, Serializable> metadata, String callback, USER user) {
        ChannelMessage<USER, ModelType> channelMessage = createChannelMessage(new ModelType(), metadata, callback);
        send(channelMessage, dtoClass, Operations.COUNT_ALL, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void getById(Class<DTO> dtoClass, ID id, Map<String, Serializable> metadata, String callback, USER user) {
        IdMessage<ID> data = createData(id);
        ChannelMessage<USER, IdMessage<ID>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.GET_BY_ID, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void getOne(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.GET_ONE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void getMany(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.GET_MANY, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void getPage(Class<DTO> dtoClass, C criteria, Map<String, Serializable> metadata, String callback, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data, metadata, callback);
        send(channelMessage, dtoClass, Operations.GET_PAGE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void getOne(Class<DTO> dtoClass, ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage, USER user) {
        send(channelMessage, dtoClass, Operations.GET_ONE, user);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> void getMany(Class<DTO> dtoClass, ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage, USER user) {
        send(channelMessage, dtoClass, Operations.GET_MANY, user);
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser> ChannelMessage<USER, IdMessage<ID>> createChannelMessage(IdMessage<ID> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, IdMessage<ID>>builder()
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

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, C extends BaseCriteria<ID>> ChannelMessage<USER, CriteriaMessage<ID, C>> createChannelMessage(CriteriaMessage<ID, C> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, CriteriaMessage<ID, C>>builder()
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser> ChannelMessage<USER, IdJsonPatchMessage<ID>> createChannelMessage(IdJsonPatchMessage<ID> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, IdJsonPatchMessage<ID>>builder()
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, C extends BaseCriteria<ID>> ChannelMessage<USER, JsonPatchMessage<ID, C>> createChannelMessage(JsonPatchMessage<ID, C> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, JsonPatchMessage<ID, C>>builder()
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> ChannelMessage<USER, DtoMessage<ID, DTO>> createChannelMessage(DtoMessage<ID, DTO> data, Map<String, Serializable> metadata, String callback) {
        return ChannelMessage.<USER, DtoMessage<ID, DTO>>builder()
                .metadata(metadata)
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder().channel(callback).build())
                .build();
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> ChannelMessage<USER, DtosMessage<ID, DTO>> createChannelMessage(DtosMessage<ID, DTO> data, Map<String, Serializable> metadata, String callback) {
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

    public <ID extends Comparable<ID> & Serializable> IdJsonPatchMessage<ID> createMessage(JsonPatch jsonPatch, ID id) {
        IdJsonPatchMessage<ID> message = new IdJsonPatchMessage<>();
        message.setId(id);
        message.setJsonPatch(jsonPatch);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> DtoMessage<ID, DTO> createMessage(DTO dto) {
        DtoMessage<ID, DTO> message = new DtoMessage<>();
        message.setDto(dto);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> DtoMessage<ID, DTO> createMessage(ID id, DTO dto) {
        DtoMessage<ID, DTO> message = new DtoMessage<>();
        message.setId(id);
        message.setDto(dto);
        return message;
    }

    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> DtosMessage<ID, DTO> createMessage(List<DTO> dtos) {
        DtosMessage<ID, DTO> message = new DtosMessage<>();
        message.setDtos(dtos);
        return message;
    }
}
