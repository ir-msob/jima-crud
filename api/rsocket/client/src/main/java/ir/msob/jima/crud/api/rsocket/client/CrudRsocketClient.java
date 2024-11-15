package ir.msob.jima.crud.api.rsocket.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.api.rsocket.commons.BaseRSocketRequesterLoadBalancer;
import ir.msob.jima.core.api.rsocket.commons.BaseRSocketRequesterMetadata;
import ir.msob.jima.core.commons.annotation.domain.DomainService;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.model.channel.ChannelMessage;
import ir.msob.jima.core.commons.model.channel.message.*;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.dto.ModelType;
import ir.msob.jima.core.commons.operation.Operations;
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

/**
 * The `CrudRsocketClient` class provides a client for performing CRUD (Create, Read, Update, Delete) operations over a RESTful API.
 * It integrates with the MSOB Framework and serves as a web client for interacting with the backend services.
 *
 * <p>This class is designed to work with DTOs (Data Transfer Objects) and provides methods for various CRUD operations, such as creating, reading, updating, and deleting data. It uses the Spring WebClient for making HTTP requests to the backend API.
 *
 * @author Yaqub Abdi
 */
@Service
@RequiredArgsConstructor
public class CrudRsocketClient implements BaseCrudClient {

    private final BaseRSocketRequesterMetadata rSocketRequesterMetadata;
    private final BaseRSocketRequesterLoadBalancer rSocketRequesterLoadBalancer;
    private final ObjectMapper objectMapper;

    /**
     * This method prepares the route for the RSocket request.
     *
     * @param dtoClass  the DTO class
     * @param operation the operation to be performed
     * @return the prepared route
     */
    public static <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> String prepareRoute(Class<DTO> dtoClass, String operation) {
        DomainService domainService = DomainService.info.getAnnotation(dtoClass);
        return String.format("api.%s.%s.%s", domainService.version(), domainService.domainName(), operation);
    }

    /**
     * This method prepares the application name for the RSocket request.
     *
     * @param dtoClass the DTO class
     * @return the prepared application name
     */
    private <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> String prepareApplicationName(Class<DTO> dtoClass) {
        return DomainService.info.getAnnotation(dtoClass).serviceName();
    }

    /**
     * This method provides a RSocket API for deleting a domain by its ID.
     * It creates a ChannelMessage with the ID and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param id       the ID of the domain to be deleted
     * @param user     the user
     * @return a Mono with the ID of the deleted domain
     */
    @MethodStats
    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<ID> deleteById(Class<DTO> dtoClass, ID id, USER user) {
        IdMessage<ID> data = createData(id);
        ChannelMessage<USER, IdMessage<ID>> channelMessage = createChannelMessage(data);
        channelMessage.setUser(user);
        String route = prepareRoute(dtoClass, Operations.DELETE_BY_ID);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (ID) o);
    }

    /**
     * This method provides a RSocket API for deleting a domain by its criteria.
     * It creates a ChannelMessage with the criteria and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param criteria the criteria of the domain to be deleted
     * @param user     the user performing the operation
     * @return a Mono with the ID of the deleted domain
     */
    @MethodStats
    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<ID> delete(Class<DTO> dtoClass, C criteria, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        channelMessage.setUser(user);
        String route = prepareRoute(dtoClass, Operations.DELETE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (ID) o);
    }

    /**
     * This method provides a RSocket API for deleting multiple domains by their criteria.
     * It creates a ChannelMessage with the criteria and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param criteria the criteria of the domains to be deleted
     * @param user     the user performing the operation
     * @return a Mono with the IDs of the deleted domains
     */
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteMany(Class<DTO> dtoClass, C criteria, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.DELETE_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList());
    }

    /**
     * This method provides a RSocket API for deleting all domains by their criteria.
     * It creates a ChannelMessage with the criteria and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param criteria the criteria of the domains to be deleted
     * @param user     the user performing the operation
     * @return a Mono with the IDs of the deleted domains
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteAll(Class<DTO> dtoClass, C criteria, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.DELETE_ALL);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList());
    }

    /**
     * This method provides a RSocket API for editing a domain by its ID.
     * It creates a ChannelMessage with the ID and JsonPatch and sends a RSocket request to the backend service.
     *
     * @param dtoClass  the DTO class
     * @param id        the ID of the domain to be edited
     * @param jsonPatch the JsonPatch to be applied
     * @param user      the user performing the operation
     * @return a Mono with the edited DTO
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> editById(Class<DTO> dtoClass, ID id, JsonPatch jsonPatch, USER user) {
        IdJsonPatchMessage<ID> data = createMessage(jsonPatch, id);
        ChannelMessage<USER, IdJsonPatchMessage<ID>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.EDIT_BY_ID);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    /**
     * This method provides a RSocket API for editing a domain by its criteria.
     * It creates a ChannelMessage with the criteria and JsonPatch and sends a RSocket request to the backend service.
     *
     * @param dtoClass  the DTO class
     * @param jsonPatch the JsonPatch to be applied
     * @param criteria  the criteria of the domain to be edited
     * @param user      the user performing the operation
     * @return a Mono with the edited DTO
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> edit(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, USER user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.EDIT);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    /**
     * This method provides a RSocket API for editing multiple domains by their criteria.
     * It creates a ChannelMessage with the criteria and JsonPatch and sends a RSocket request to the backend service.
     *
     * @param dtoClass  the DTO class
     * @param jsonPatch the JsonPatch to be applied
     * @param criteria  the criteria of the domains to be edited
     * @param user      the user performing the operation
     * @return a Mono with the edited DTO
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, USER user) {
        JsonPatchMessage<ID, C> data = createMessage(jsonPatch, criteria);
        ChannelMessage<USER, JsonPatchMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.EDIT_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList()); // TODO
    }

    /**
     * This method provides a RSocket API for saving a domain.
     * It creates a ChannelMessage with the DTO and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param dto      the DTO to be saved
     * @param user     the user performing the operation
     * @return a Mono with the saved DTO
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> save(Class<DTO> dtoClass, DTO dto, USER user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.SAVE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    /**
     * This method provides a RSocket API for saving multiple domains.
     * It creates a ChannelMessage with the DTOs and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param dtos     the DTOs to be saved
     * @param user     the user performing the operation
     * @return a Mono with the saved DTOs
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Collection<DTO>> saveMany(Class<DTO> dtoClass, Collection<DTO> dtos, USER user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.SAVE_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList()); // TODO
    }

    /**
     * This method provides a RSocket API for updating a domain by its ID.
     * It creates a ChannelMessage with the ID and DTO and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param id       the ID of the domain to be updated
     * @param dto      the DTO to be updated
     * @param user     the user performing the operation
     * @return a Mono with the updated DTO
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> updateById(Class<DTO> dtoClass, ID id, DTO dto, USER user) {
        DtoMessage<ID, DTO> data = createMessage(id, dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.UPDATE_BY_ID);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    /**
     * This method provides a RSocket API for updating a domain.
     * It creates a ChannelMessage with the DTO and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param dto      the DTO to be updated
     * @param user     the user performing the operation
     * @return a Mono with the updated DTO
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> update(Class<DTO> dtoClass, DTO dto, USER user) {
        DtoMessage<ID, DTO> data = createMessage(dto);
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.UPDATE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    /**
     * This method provides a RSocket API for updating multiple domains.
     * It creates a ChannelMessage with the DTOs and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param dtos     the DTOs to be updated
     * @param user     the user performing the operation
     * @return a Mono with the updated DTOs
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Collection<DTO>> updateMany(Class<DTO> dtoClass, Collection<DTO> dtos, USER user) {
        DtosMessage<ID, DTO> data = createMessage(dtos);
        ChannelMessage<USER, DtosMessage<ID, DTO>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.UPDATE_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList()); // TODO
    }

    /**
     * This method provides a RSocket API for counting the number of domains by their criteria.
     * It creates a ChannelMessage with the criteria and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param criteria the criteria of the domains to be counted
     * @param user     the user performing the operation
     * @return a Mono with the number of domains
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Long> count(Class<DTO> dtoClass, C criteria, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.COUNT);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Long.class);
    }

    /**
     * This method provides a RSocket API for counting all domains.
     * It creates a ChannelMessage with the DTO and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param user     the user performing the operation
     * @return a Mono with the number of domains
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Long> countAll(Class<DTO> dtoClass, USER user) {
        ModelType data = new ModelType();
        ChannelMessage<USER, ModelType> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.COUNT_ALL);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Long.class);
    }

    /**
     * This method provides a RSocket API for getting a domain by its ID.
     * It creates a ChannelMessage with the ID and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param id       the ID of the domain to be retrieved
     * @param user     the user performing the operation
     * @return a Mono with the retrieved DTO
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> getById(Class<DTO> dtoClass, ID id, USER user) {
        IdMessage<ID> data = createData(id);
        ChannelMessage<USER, IdMessage<ID>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.GET_BY_ID);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    /**
     * This method provides a RSocket API for getting a domain by its criteria.
     * It creates a ChannelMessage with the criteria and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param criteria the criteria of the domain to be retrieved
     * @param user     the user performing the operation
     * @return a Mono with the retrieved DTO
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> getOne(Class<DTO> dtoClass, C criteria, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.GET_ONE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Object.class)
                .map(o -> (DTO) o); // TODO
    }

    /**
     * This method provides a RSocket API for getting multiple domains by their criteria.
     * It creates a ChannelMessage with the criteria and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param criteria the criteria of the domains to be retrieved
     * @param user     the user performing the operation
     * @return a Mono with the retrieved DTOs
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> getMany(Class<DTO> dtoClass, C criteria, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.GET_MANY);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Collection.class)
                .map(collection -> collection.stream().toList()); // TODO
    }

    /**
     * This method provides a RSocket API for getting all domains.
     * It creates a ChannelMessage with the DTO and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param user     the user performing the operation
     * @return a Mono with the retrieved DTOs
     */
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Flux<DTO> getStream(Class<DTO> dtoClass, C criteria, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        channelMessage.setUser(user);
        String route = prepareRoute(dtoClass, Operations.GET_STREAM);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(objectMapper.writeValueAsString(channelMessage))
                .retrieveFlux(Object.class)
                .map(o -> objectMapper.convertValue(o, dtoClass));
    }

    /**
     * This method provides a RSocket API for getting all domains.
     * It creates a ChannelMessage with the DTO and sends a RSocket request to the backend service.
     *
     * @param dtoClass the DTO class
     * @param user     the user performing the operation
     * @return a Mono with the retrieved DTOs
     */
    @MethodStats
    @SneakyThrows
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Page<DTO>> getPage(Class<DTO> dtoClass, C criteria, USER user) {
        CriteriaMessage<ID, C> data = createData(criteria);
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = createChannelMessage(data);
        String route = prepareRoute(dtoClass, Operations.GET_PAGE);
        String applicationName = prepareApplicationName(dtoClass);

        return rSocketRequesterLoadBalancer.getRequester(applicationName)
                .route(route)
                .metadata(rSocketRequesterMetadata::metadata)
                .data(channelMessage)
                .retrieveMono(Page.class)
                .map(page -> (Page<DTO>) page);
    }

    /**
     * Creates a ChannelMessage with the provided IdJsonPatchMessage data.
     *
     * @param data   The IdJsonPatchMessage data to be included in the ChannelMessage.
     * @param <ID>   The type of the ID, which must be Comparable and Serializable.
     * @param <USER> The type of the User, which must extend BaseUser.
     * @return A ChannelMessage containing the provided IdJsonPatchMessage data.
     */
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser> ChannelMessage<USER, IdJsonPatchMessage<ID>> createChannelMessage(IdJsonPatchMessage<ID> data) {
        ChannelMessage<USER, IdJsonPatchMessage<ID>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    /**
     * Creates a ChannelMessage with the provided IdMessage data.
     *
     * @param data   The IdMessage data to be included in the ChannelMessage.
     * @param <ID>   The type of the ID, which must be Comparable and Serializable.
     * @param <USER> The type of the User, which must extend BaseUser.
     * @return A ChannelMessage containing the provided IdMessage data.
     */
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser> ChannelMessage<USER, IdMessage<ID>> createChannelMessage(IdMessage<ID> data) {
        ChannelMessage<USER, IdMessage<ID>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    /**
     * Creates a ChannelMessage with the provided ModelType data.
     *
     * @param data   The ModelType data to be included in the ChannelMessage.
     * @param <ID>   The type of the ID, which must be Comparable and Serializable.
     * @param <USER> The type of the User, which must extend BaseUser.
     * @return A ChannelMessage containing the provided ModelType data.
     */
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser> ChannelMessage<USER, ModelType> createChannelMessage(ModelType data) {
        ChannelMessage<USER, ModelType> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    /**
     * Creates a ChannelMessage with the provided CriteriaMessage data.
     *
     * @param data   The CriteriaMessage data to be included in the ChannelMessage.
     * @param <ID>   The type of the ID, which must be Comparable and Serializable.
     * @param <USER> The type of the User, which must extend BaseUser.
     * @param <C>    The type of the Criteria, which must extend BaseCriteria.
     * @return A ChannelMessage containing the provided CriteriaMessage data.
     */
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, C extends BaseCriteria<ID>> ChannelMessage<USER, CriteriaMessage<ID, C>> createChannelMessage(CriteriaMessage<ID, C> data) {
        ChannelMessage<USER, CriteriaMessage<ID, C>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    /**
     * Creates a ChannelMessage with the provided JsonPatchMessage data.
     *
     * @param data   The JsonPatchMessage data to be included in the ChannelMessage.
     * @param <ID>   The type of the ID, which must be Comparable and Serializable.
     * @param <USER> The type of the User, which must extend BaseUser.
     * @param <C>    The type of the Criteria, which must extend BaseCriteria.
     * @return A ChannelMessage containing the provided JsonPatchMessage data.
     */
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, C extends BaseCriteria<ID>> ChannelMessage<USER, JsonPatchMessage<ID, C>> createChannelMessage(JsonPatchMessage<ID, C> data) {
        ChannelMessage<USER, JsonPatchMessage<ID, C>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    /**
     * Creates a ChannelMessage with the provided DtoMessage data.
     *
     * @param data   The DtoMessage data to be included in the ChannelMessage.
     * @param <ID>   The type of the ID, which must be Comparable and Serializable.
     * @param <USER> The type of the User, which must extend BaseUser.
     * @param <DTO>  The type of the DTO, which must extend BaseDto.
     * @return A ChannelMessage containing the provided DtoMessage data.
     */
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> ChannelMessage<USER, DtoMessage<ID, DTO>> createChannelMessage(DtoMessage<ID, DTO> data) {
        ChannelMessage<USER, DtoMessage<ID, DTO>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    /**
     * Creates a ChannelMessage with the provided DtosMessage data.
     *
     * @param data   The DtosMessage data to be included in the ChannelMessage.
     * @param <ID>   The type of the ID, which must be Comparable and Serializable.
     * @param <USER> The type of the User, which must extend BaseUser.
     * @param <DTO>  The type of the DTO, which must extend BaseDto.
     * @return A ChannelMessage containing the provided DtosMessage data.
     */
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> ChannelMessage<USER, DtosMessage<ID, DTO>> createChannelMessage(DtosMessage<ID, DTO> data) {
        ChannelMessage<USER, DtosMessage<ID, DTO>> channelMessage = new ChannelMessage<>();
        channelMessage.setData(data);
        return channelMessage;
    }

    /**
     * Creates a ChannelMessage with the provided id data.
     *
     * @param id   The data to be included in the ChannelMessage.
     * @param <ID> The type of the ID, which must be Comparable and Serializable.
     * @return A ChannelMessage containing the provided data.
     */
    public <ID extends Comparable<ID> & Serializable> IdMessage<ID> createData(ID id) {
        IdMessage<ID> message = new IdMessage<>();
        message.setId(id);
        return message;
    }

    /**
     * Creates a ChannelMessage with the provided criteria data.
     *
     * @param criteria The data to be included in the ChannelMessage.
     * @param <ID>     The type of the ID, which must be Comparable and Serializable.
     * @param <C>      The type of the Criteria, which must extend BaseCriteria.
     * @return A ChannelMessage containing the provided data.
     */
    public <ID extends Comparable<ID> & Serializable, C extends BaseCriteria<ID>> CriteriaMessage<ID, C> createData(C criteria) {
        CriteriaMessage<ID, C> message = new CriteriaMessage<>();
        message.setCriteria(criteria);
        return message;
    }

    /**
     * Creates a ChannelMessage with the provided JsonPatch data.
     *
     * @param jsonPatch The data to be included in the ChannelMessage.
     * @param <ID>      The type of the ID, which must be Comparable and Serializable.
     * @return A ChannelMessage containing the provided data.
     */
    public <ID extends Comparable<ID> & Serializable> IdJsonPatchMessage<ID> createMessage(JsonPatch jsonPatch, ID id) {
        IdJsonPatchMessage<ID> message = new IdJsonPatchMessage<>();
        message.setId(id);
        message.setJsonPatch(jsonPatch);
        return message;
    }

    /**
     * Creates a ChannelMessage with the provided JsonPatch data.
     *
     * @param jsonPatch The data to be included in the ChannelMessage.
     * @param <ID>      The type of the ID, which must be Comparable and Serializable.
     * @param <C>       The type of the Criteria, which must extend BaseCriteria.
     * @return A ChannelMessage containing the provided data.
     */
    public <ID extends Comparable<ID> & Serializable, C extends BaseCriteria<ID>> JsonPatchMessage<ID, C> createMessage(JsonPatch jsonPatch, C criteria) {
        JsonPatchMessage<ID, C> message = new JsonPatchMessage<>();
        message.setCriteria(criteria);
        message.setJsonPatch(jsonPatch);
        return message;
    }

    /**
     * Creates a ChannelMessage with the provided DTO data.
     *
     * @param dto   The data to be included in the ChannelMessage.
     * @param <ID>  The type of the ID, which must be Comparable and Serializable.
     * @param <DTO> The type of the DTO, which must extend BaseDto.
     * @return A ChannelMessage containing the provided data.
     */
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> DtoMessage<ID, DTO> createMessage(ID id, DTO dto) {
        DtoMessage<ID, DTO> message = createMessage(dto);
        message.setId(id);
        return message;
    }

    /**
     * Creates a ChannelMessage with the provided DTO data.
     *
     * @param dto   The data to be included in the ChannelMessage.
     * @param <ID>  The type of the ID, which must be Comparable and Serializable.
     * @param <DTO> The type of the DTO, which must extend BaseDto.
     * @return A ChannelMessage containing the provided data.
     */
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> DtoMessage<ID, DTO> createMessage(DTO dto) {
        DtoMessage<ID, DTO> message = new DtoMessage<>();
        message.setDto(dto);
        return message;
    }

    /**
     * Creates a ChannelMessage with the provided DTOs data.
     *
     * @param dtos  The data to be included in the ChannelMessage.
     * @param <ID>  The type of the ID, which must be Comparable and Serializable.
     * @param <DTO> The type of the DTO, which must extend BaseDto.
     * @return A ChannelMessage containing the provided data.
     */
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> DtosMessage<ID, DTO> createMessage(Collection<DTO> dtos) {
        DtosMessage<ID, DTO> message = new DtosMessage<>();
        message.setDtos(dtos);
        return message;
    }

}