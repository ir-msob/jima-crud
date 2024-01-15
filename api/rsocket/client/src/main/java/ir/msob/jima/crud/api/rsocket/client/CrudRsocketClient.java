package ir.msob.jima.crud.api.rsocket.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.api.rsocket.commons.BaseRSocketRequesterLoadBalancer;
import ir.msob.jima.core.api.rsocket.commons.BaseRSocketRequesterMetadata;
import ir.msob.jima.core.commons.annotation.domain.DomainService;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.channel.message.DtoMessage;
import ir.msob.jima.core.commons.model.channel.message.DtosMessage;
import ir.msob.jima.core.commons.model.channel.message.JsonPatchMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.criteria.SampleCriteria;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.client.BaseCrudClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

/**
 * The `CrudRsocketClient` class provides a client for performing CRUD (Create, Read, Update, Delete) operations over a RESTful API.
 * It integrates with the MSOB Framework and serves as a web client for interacting with the backend services.
 *
 * <p>This class is designed to work with DTOs (Data Transfer Objects) and provides methods for various CRUD operations, such as creating, reading, updating, and deleting data. It uses the Spring WebClient for making HTTP requests to the backend API.
 */
@Service
@RequiredArgsConstructor
public class CrudRsocketClient implements BaseCrudClient {

    private final BaseRSocketRequesterMetadata rSocketRequesterMetadata;
    private final BaseRSocketRequesterLoadBalancer rSocketRequesterLoadBalancer;
    private final ObjectMapper objectMapper;

    public static <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> String prepareRoute(Class<DTO> dtoClass, String operation) {
        DomainService domainService = DomainService.info.getAnnotation(dtoClass);
        return String.format("api.%s.%s.%s", domainService.version(), domainService.domainName(), operation);
    }

    private <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> String prepareApplicationName(Class<DTO> dtoClass) {
        return DomainService.info.getAnnotation(dtoClass).serviceName();
    }

    @MethodStats
    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<ID> delete(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        channelMessage.setUser(user.orElse(null));
        String route = prepareRoute(dtoClass, Operations.DELETE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (ID) o);
    }

    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteMany(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.DELETE_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList());
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteAll(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.DELETE_ALL);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList());
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> edit(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Optional<USER> user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<ID, USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.EDIT);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Optional<USER> user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<ID, USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.EDIT_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList()); // TODO
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<DTO> save(Class<DTO> dtoClass, DTO dto, Optional<USER> user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<ID, USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.SAVE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Collection<DTO>> saveMany(Class<DTO> dtoClass, Collection<DTO> dtos, Optional<USER> user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<ID, USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.SAVE_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList()); // TODO
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<DTO> update(Class<DTO> dtoClass, DTO dto, Optional<USER> user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<ID, USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.UPDATE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Collection<DTO>> updateMany(Class<DTO> dtoClass, Collection<DTO> dtos, Optional<USER> user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<ID, USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.UPDATE_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList()); // TODO
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Long> count(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.COUNT);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Long.class);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Long> countAll(Class<DTO> dtoClass, Optional<USER> user) {
        CriteriaMessage<ID, SampleCriteria<ID>> data = createData(null);
        ChannelMessage<ID, USER, CriteriaMessage<ID, SampleCriteria<ID>>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.COUNT_ALL);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Long.class);
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> getOne(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.GET_ONE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> getMany(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.GET_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList()); // TODO
    }

    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Flux<DTO> getStream(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        channelMessage.setUser(user.orElse(null));
        String route = prepareRoute(dtoClass, Operations.GET_STREAM);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(objectMapper.writeValueAsString(channelMessage))
                .retrieveFlux(Object.class)
                .map(o -> objectMapper.convertValue(o, dtoClass));
    }

    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Page<DTO>> getPage(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.GET_PAGE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Page.class)
                .map(page -> (Page<DTO>) page);
    }


    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, C extends BaseCriteria<ID>> ChannelMessage<ID, USER, CriteriaMessage<ID, C>> createChannelMessage(CriteriaMessage<ID, C> data) {
        ChannelMessage<ID, USER, CriteriaMessage<ID, C>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, C extends BaseCriteria<ID>> ChannelMessage<ID, USER, JsonPatchMessage<ID, C>> createChannelMessage(JsonPatchMessage<ID, C> data) {
        ChannelMessage<ID, USER, JsonPatchMessage<ID, C>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> ChannelMessage<ID, USER, DtoMessage<ID, DTO>> createChannelMessage(DtoMessage<ID, DTO> data) {
        ChannelMessage<ID, USER, DtoMessage<ID, DTO>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> ChannelMessage<ID, USER, DtosMessage<ID, DTO>> createChannelMessage(DtosMessage<ID, DTO> data) {
        ChannelMessage<ID, USER, DtosMessage<ID, DTO>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
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

    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> DtosMessage<ID, DTO> createMessage(Collection<DTO> dtos) {
        DtosMessage<ID, DTO> message = new DtosMessage<>();
        message.setDtos(dtos);
        return message;
    }

}