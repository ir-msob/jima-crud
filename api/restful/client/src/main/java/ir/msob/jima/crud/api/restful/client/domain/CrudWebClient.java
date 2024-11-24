package ir.msob.jima.crud.api.restful.client.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.api.restful.beans.util.ObjectToParam;
import ir.msob.jima.core.api.restful.commons.webclient.BaseWebClient;
import ir.msob.jima.core.beans.properties.JimaProperties;
import ir.msob.jima.core.commons.domain.DomainInfo;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.runtime.CommonRuntimeException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.criteria.BaseCriteria;
import ir.msob.jima.crud.api.restful.client.RestUtil;
import ir.msob.jima.crud.client.BaseCrudClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The `CrudWebClient` class provides a client for performing CRUD (Create, Read, Update, Delete) operations over a RESTful API.
 * It integrates with the MSOB Framework and serves as a web client for interacting with the backend services.
 *
 * <p>This class is designed to work with DTOs (Data Transfer Objects) and provides methods for various CRUD operations, such as creating, reading, updating, and deleting data. It uses the Spring WebClient for making HTTP requests to the backend API.
 */
@Service
@RequiredArgsConstructor
public class CrudWebClient implements BaseCrudClient, BaseWebClient {

    private final ObjectMapper objectMapper;
    private final ObjectToParam objectToParam;
    private final WebClient webClient;
    private final JimaProperties jimaProperties;

    private static String getUri(String suffix, DomainInfo domainInfo) {
        String uri;
        if (Strings.isNotBlank(suffix))
            uri = String.format("%s/%s", RestUtil.uri(domainInfo), suffix);
        else
            uri = RestUtil.uri(domainInfo);
        return uri;
    }

    /**
     * This method is used to convert an object to a JSON string.
     *
     * @param o The object to be converted.
     * @return A JSON string representation of the object.
     */
    @SneakyThrows
    public String createBody(Object o) {
        return objectMapper.writeValueAsString(o);
    }

    /**
     * This method is used to delete all entities that match the given criteria.
     *
     * @param dtoClass The class of the DTO.
     * @param criteria The criteria to match.
     * @param user     The user performing the operation.
     * @return A Mono that emits the IDs of the deleted entities.
     * @throws CommonRuntimeException If the rest client has not been implemented.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteAll(Class<DTO> dtoClass, C criteria, USER user) {
        throw new CommonRuntimeException("This rest client has not been implemented!");
    }

    /**
     * This method is used to delete an entity by its ID.
     *
     * @param dtoClass The class of the DTO.
     * @param id       The ID of the entity to be deleted.
     * @param user     The user performing the operation.
     * @return A Mono that emits the ID of the deleted entity.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<ID> deleteById(Class<DTO> dtoClass, ID id, USER user) {
        return webClient
                .delete()
                .uri(builder -> getUriWithId(builder, dtoClass, id.toString()))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(Object.class)
                .map(o -> (ID) o);
    }

    /**
     * This method is used to delete an entity that matches the given criteria.
     *
     * @param dtoClass The class of the DTO.
     * @param criteria The criteria to match.
     * @param user     The user performing the operation.
     * @return A Mono that emits the ID of the deleted entity.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<ID> delete(Class<DTO> dtoClass, C criteria, USER user) {
        return webClient
                .delete()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.DELETE, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(Object.class)
                .map(o -> (ID) o);
    }

    /**
     * This method is used to delete multiple entities that match the given criteria.
     *
     * @param dtoClass The class of the DTO.
     * @param criteria The criteria to match.
     * @param user     The user performing the operation.
     * @return A Mono that emits the IDs of the deleted entities.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteMany(Class<DTO> dtoClass, C criteria, USER user) {
        return webClient
                .delete()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.DELETE_MANY, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToFlux(Object.class)
                .map(o -> (ID) o)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    /**
     * This method is used to edit an entity by its ID.
     *
     * @param dtoClass  The class of the DTO.
     * @param id        The ID of the entity to be edited.
     * @param jsonPatch The JSON Patch document containing the changes to be applied.
     * @param user      The user performing the operation.
     * @return A Mono that emits the edited entity.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> editById(Class<DTO> dtoClass, ID id, JsonPatch jsonPatch, USER user) {
        return webClient
                .patch()
                .uri(builder -> getUriWithId(builder, dtoClass, id.toString()))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(jsonPatch))
                .retrieve()
                .bodyToMono(dtoClass);
    }

    /**
     * This method is used to edit an entity that matches the given criteria.
     *
     * @param dtoClass  The class of the DTO.
     * @param jsonPatch The JSON Patch document containing the changes to be applied.
     * @param criteria  The criteria to match.
     * @param user      The user performing the operation.
     * @return A Mono that emits the edited entity.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> edit(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, USER user) {
        return webClient
                .patch()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.EDIT, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(jsonPatch))
                .retrieve()
                .bodyToMono(dtoClass);
    }

    /**
     * This method is used to edit multiple entities that match the given criteria.
     *
     * @param dtoClass  The class of the DTO.
     * @param jsonPatch The JSON Patch document containing the changes to be applied.
     * @param criteria  The criteria to match.
     * @param user      The user performing the operation.
     * @return A Mono that emits the edited entities.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, USER user) {
        return webClient
                .patch()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.EDIT_MANY, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(jsonPatch))
                .retrieve()
                .bodyToFlux(dtoClass)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method is used to save a new entity.
     *
     * @param dtoClass The class of the DTO.
     * @param dto      The DTO to be saved.
     * @param user     The user performing the operation.
     * @return A Mono that emits the saved entity.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> save(Class<DTO> dtoClass, DTO dto, USER user) {
        return webClient
                .post()
                .uri(builder -> getUriWithCriteria(builder, dtoClass))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(dto))
                .retrieve()
                .bodyToMono(dtoClass);
    }

    /**
     * This method is used to save multiple new entities.
     *
     * @param dtoClass The class of the DTO.
     * @param dtos     The DTOs to be saved.
     * @param user     The user performing the operation.
     * @return A Mono that emits the saved entities.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Collection<DTO>> saveMany(Class<DTO> dtoClass, Collection<DTO> dtos, USER user) {
        return webClient
                .post()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.SAVE_MANY))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(dtos))
                .retrieve()
                .bodyToFlux(dtoClass)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method is used to update an entity by its ID.
     *
     * @param dtoClass The class of the DTO.
     * @param id       The ID of the entity to be updated.
     * @param dto      The DTO containing the new data.
     * @param user     The user performing the operation.
     * @return A Mono that emits the updated entity.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> updateById(Class<DTO> dtoClass, ID id, DTO dto, USER user) {
        return webClient
                .put()
                .uri(builder -> getUriWithId(builder, dtoClass, id.toString()))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(dto))
                .retrieve()
                .bodyToMono(dtoClass);
    }

    /**
     * This method is used to update an entity that matches the given criteria.
     *
     * @param dtoClass The class of the DTO.
     * @param dto      The DTO containing the new data.
     * @param user     The user performing the operation.
     * @return A Mono that emits the updated entity.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> update(Class<DTO> dtoClass, DTO dto, USER user) {
        return webClient
                .put()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.UPDATE))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(dto))
                .retrieve()
                .bodyToMono(dtoClass);
    }

    /**
     * This method is used to update multiple entities that match the given criteria.
     *
     * @param dtoClass The class of the DTO.
     * @param dtos     The DTOs containing the new data.
     * @param user     The user performing the operation.
     * @return A Mono that emits the updated entities.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Collection<DTO>> updateMany(Class<DTO> dtoClass, Collection<DTO> dtos, USER user) {
        return webClient
                .put()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.UPDATE_MANY))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(dtos))
                .retrieve()
                .bodyToFlux(dtoClass)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method is used to count the number of entities that match the given criteria.
     *
     * @param dtoClass The class of the DTO.
     * @param criteria The criteria to match.
     * @param user     The user performing the operation.
     * @return A Mono that emits the count of entities.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Long> count(Class<DTO> dtoClass, C criteria, USER user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.COUNT, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(Long.class)
                .retryWhen(jimaProperties.getClient().getRetryRequest().createRetryBackoffSpec());
    }

    /**
     * This method is used to count the total number of entities.
     *
     * @param dtoClass The class of the DTO.
     * @param user     The user performing the operation.
     * @return A Mono that emits the count of entities.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<Long> countAll(Class<DTO> dtoClass, USER user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.COUNT_ALL, null))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(Long.class)
                .retryWhen(jimaProperties.getClient().getRetryRequest().createRetryBackoffSpec());
    }

    /**
     * This method is used to check if an entity exists by its ID.
     *
     * @param dtoClass The class of the DTO.
     * @param id       The ID of the entity to check.
     * @param user     The user performing the operation.
     * @return A Mono that emits a boolean value indicating whether the entity exists.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> Mono<DTO> getById(Class<DTO> dtoClass, ID id, USER user) {
        return webClient
                .get()
                .uri(builder -> getUriWithId(builder, dtoClass, id.toString()))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(dtoClass)
                .retryWhen(jimaProperties.getClient().getRetryRequest().createRetryBackoffSpec());
    }

    /**
     * This method is used to check if an entity exists that matches the given criteria.
     *
     * @param dtoClass The class of the DTO.
     * @param criteria The criteria to match.
     * @param user     The user performing the operation.
     * @return A Mono that emits a boolean value indicating whether the entity exists.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> getOne(Class<DTO> dtoClass, C criteria, USER user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.GET_ONE, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(dtoClass)
                .retryWhen(jimaProperties.getClient().getRetryRequest().createRetryBackoffSpec());
    }

    /**
     * This method is used to check if an entity exists that matches the given criteria.
     *
     * @param dtoClass The class of the DTO.
     * @param criteria The criteria to match.
     * @param user     The user performing the operation.
     * @return A Mono that emits a boolean value indicating whether the entity exists.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> getMany(Class<DTO> dtoClass, C criteria, USER user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.GET_MANY, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToFlux(dtoClass)
                .retryWhen(jimaProperties.getClient().getRetryRequest().createRetryBackoffSpec())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * This method is used to get all entities.
     *
     * @param dtoClass The class of the DTO.
     * @param user     The user performing the operation.
     * @return A Flux that emits all entities.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Flux<DTO> getStream(Class<DTO> dtoClass, C criteria, USER user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.GET_STREAM, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToFlux(dtoClass)
                .retryWhen(jimaProperties.getClient().getRetryRequest().createRetryBackoffSpec());
    }

    /**
     * This method is used to get a page of entities.
     *
     * @param dtoClass The class of the DTO.
     * @param criteria The criteria to match.
     * @param user     The user performing the operation.
     * @return A Mono that emits a page of entities.
     */
    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Page<DTO>> getPage(Class<DTO> dtoClass, C criteria, USER user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(Object.class)
                .retryWhen(jimaProperties.getClient().getRetryRequest().createRetryBackoffSpec())
                .map(res -> {
                    Page<Object> page = objectMapper.convertValue(res, Page.class);
                    List<DTO> dtos = page.getContent().stream().map(r -> objectMapper.convertValue(r, dtoClass)).toList();
                    return new PageImpl<>(dtos);
                });
    }

    /**
     * This method is used to get the URI for a given criteria and pageable object.
     *
     * @param builder  The UriBuilder object to be modified.
     * @param dtoClass The class of the DTO.
     * @param suffix   The suffix to be appended to the URI.
     * @param criteria The criteria to match.
     * @return The URI for the given criteria and pageable object.
     */
    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> URI getUriWithCriteria(UriBuilder builder, Class<DTO> dtoClass, String suffix, C criteria) {
        return getUriWithCriteria(builder, dtoClass, suffix, criteria, null);
    }

    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> URI getUriWithCriteria(UriBuilder builder, Class<DTO> dtoClass, C criteria) {
        return getUriWithCriteria(builder, dtoClass, "", criteria, null);
    }

    /**
     * This method is used to get the URI for a given suffix object.
     *
     * @param builder  The UriBuilder object to be modified.
     * @param dtoClass The class of the DTO.
     * @param suffix   The suffix to be appended to the URI.
     * @return The URI for the given suffix object.
     */
    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> URI getUriWithId(UriBuilder builder, Class<DTO> dtoClass, String suffix) {
        BaseDto<ID> baseDto = dtoClass.getDeclaredConstructor().newInstance();
        DomainInfo domainInfo = DomainInfo.info.getAnnotation(baseDto.getClass());
        return builder
                .host(domainInfo.serviceName())
                .path(String.format("%s/%s", RestUtil.uri(domainInfo), suffix))
                .build();

    }

    /**
     * This method is used to get the URI for a given criteria.
     *
     * @param builder  The UriBuilder object to be modified.
     * @param dtoClass The class of the DTO.
     * @param suffix   The suffix to be appended to the URI.
     * @param criteria The criteria to match.
     * @param pageable The pageable object.
     * @return The URI for the given criteria.
     */
    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> URI getUriWithCriteria(UriBuilder builder, Class<DTO> dtoClass, String suffix, C criteria, Pageable pageable) {
        BaseDto<ID> baseDto = dtoClass.getDeclaredConstructor().newInstance();
        DomainInfo domainInfo = DomainInfo.info.getAnnotation(baseDto.getClass());
        return builder
                .host(domainInfo.serviceName())
                .path(getUri(suffix, domainInfo))
                .queryParams(objectToParam.convert(criteria, pageable))
                .build();

    }

    /**
     * This method is used to get the URI for a given criteria.
     *
     * @param builder  The UriBuilder object to be modified.
     * @param dtoClass The class of the DTO.
     * @param suffix   The suffix to be appended to the URI.
     * @return The URI for the given criteria.
     */
    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> URI getUriWithCriteria(UriBuilder builder, Class<DTO> dtoClass, String suffix) {
        BaseDto<ID> baseDto = dtoClass.getDeclaredConstructor().newInstance();
        DomainInfo domainInfo = DomainInfo.info.getAnnotation(baseDto.getClass());
        return builder
                .host(domainInfo.serviceName())
                .path(getUri(suffix, domainInfo))
                .build();
    }


    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> URI getUriWithCriteria(UriBuilder builder, Class<DTO> dtoClass) {
        return getUriWithCriteria(builder, dtoClass, "");
    }


    /**
     * This method is used to set the user information in the HTTP headers.
     *
     * @param builder The HttpHeaders object to be modified.
     * @param user    The user performing the operation.
     */
    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser> void setDefaultHeaders(HttpHeaders builder, USER user) {
        builder.setContentType(MediaType.APPLICATION_JSON);
        setUserInfo(builder, user);
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}