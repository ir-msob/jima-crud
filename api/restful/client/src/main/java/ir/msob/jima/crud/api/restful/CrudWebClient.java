package ir.msob.jima.crud.api.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.api.restful.beans.util.ObjectToParam;
import ir.msob.jima.core.api.restful.commons.webclient.BaseWebClient;
import ir.msob.jima.core.commons.annotation.domain.DomainService;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.exception.runtime.CommonRuntimeException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.client.BaseCrudClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
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
import reactor.util.retry.Retry;

import java.io.Serializable;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

    @Value("${msob.web.web-client.retry.retry-number:5}")
    private long retryNumber;
    @Value("${msob.web.web-client.retry.retry-delay:3000}")
    private long retryDelay;

    @SneakyThrows
    public String createBody(Object o) {
        return objectMapper.writeValueAsString(o);
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteAll(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        throw new CommonRuntimeException("This rest client has not been implemented!");
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<ID> delete(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        return webClient
                .delete()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.DELETE, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(Object.class)
                .map(o -> (ID) o);
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<ID>> deleteMany(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        return webClient
                .delete()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.DELETE_MANY, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToFlux(Object.class)
                .map(o -> (ID) o)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> edit(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Optional<USER> user) {
        return webClient
                .patch()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.EDIT, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(jsonPatch))
                .retrieve()
                .bodyToMono(dtoClass);
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> editMany(Class<DTO> dtoClass, JsonPatch jsonPatch, C criteria, Optional<USER> user) {
        return webClient
                .patch()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.EDIT_MANY, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(jsonPatch))
                .retrieve()
                .bodyToFlux(dtoClass)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<DTO> save(Class<DTO> dtoClass, DTO dto, Optional<USER> user) {
        return webClient
                .post()
                .uri(builder -> getUriWithCriteria(builder, dtoClass))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(dto))
                .retrieve()
                .bodyToMono(dtoClass);
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Collection<DTO>> saveMany(Class<DTO> dtoClass, Collection<DTO> dtos, Optional<USER> user) {
        return webClient
                .post()
                .uri(builder -> getUriWithCriteria(builder, dtoClass))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(dtos))
                .retrieve()
                .bodyToFlux(dtoClass)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<DTO> update(Class<DTO> dtoClass, DTO dto, Optional<USER> user) {
        return webClient
                .put()
                .uri(builder -> getUriWithCriteria(builder, dtoClass))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(dto))
                .retrieve()
                .bodyToMono(dtoClass);
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Collection<DTO>> updateMany(Class<DTO> dtoClass, Collection<DTO> dtos, Optional<USER> user) {
        return webClient
                .put()
                .uri(builder -> getUriWithCriteria(builder, dtoClass))
                .headers(builder -> setDefaultHeaders(builder, user))
                .bodyValue(createBody(dtos))
                .retrieve()
                .bodyToFlux(dtoClass)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Long> count(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.COUNT, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(Long.class)
                .retryWhen(Retry.backoff(retryNumber, Duration.ofMillis(retryDelay)));
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>> Mono<Long> countAll(Class<DTO> dtoClass, Optional<USER> user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.COUNT_ALL, null))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(Long.class)
                .retryWhen(Retry.backoff(retryNumber, Duration.ofMillis(retryDelay)));
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<DTO> getOne(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.GET_ONE, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(dtoClass)
                .retryWhen(Retry.backoff(retryNumber, Duration.ofMillis(retryDelay)));
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Collection<DTO>> getMany(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.GET_MANY, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToFlux(dtoClass)
                .retryWhen(Retry.backoff(retryNumber, Duration.ofMillis(retryDelay)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Flux<DTO> getStream(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.GET_STREAM, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToFlux(dtoClass)
                .retryWhen(Retry.backoff(retryNumber, Duration.ofMillis(retryDelay)));
    }

    @MethodStats
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> Mono<Page<DTO>> getPage(Class<DTO> dtoClass, C criteria, Optional<USER> user) {
        return webClient
                .get()
                .uri(builder -> getUriWithCriteria(builder, dtoClass, Operations.GET_PAGE, criteria))
                .headers(builder -> setDefaultHeaders(builder, user))
                .retrieve()
                .bodyToMono(Object.class)
                .retryWhen(Retry.backoff(retryNumber, Duration.ofMillis(retryDelay)))
                .map(res -> {
                    Page<Object> page = objectMapper.convertValue(res, Page.class);
                    List<DTO> dtos = page.getContent().stream().map(r -> objectMapper.convertValue(r, dtoClass)).toList();
                    return new PageImpl<>(dtos);
                });
    }

    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> URI getUriWithCriteria(UriBuilder builder, Class<DTO> dtoClass, String action, C criteria) {
        return getUriWithCriteria(builder, dtoClass, action, criteria, null);
    }

    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>, C extends BaseCriteria<ID>> URI getUriWithCriteria(UriBuilder builder, Class<DTO> dtoClass, String action, C criteria, Pageable pageable) {
        BaseDto<ID> baseDto = dtoClass.getDeclaredConstructor().newInstance();
        DomainService domainService = DomainService.info.getAnnotation(baseDto.getClass());
        return builder
                .host(domainService.serviceName())
                .path(String.format("%s/%s", RestUtil.uri(domainService), action))
                .queryParams(objectToParam.convert(criteria, pageable))
                .build();

    }

    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, DTO extends BaseDto<ID>> URI getUriWithCriteria(UriBuilder builder, Class<DTO> dtoClass) {
        BaseDto<ID> baseDto = dtoClass.getDeclaredConstructor().newInstance();
        DomainService domainService = DomainService.info.getAnnotation(baseDto.getClass());
        return builder
                .host(domainService.serviceName())
                .path(RestUtil.uri(domainService))
                .build();
    }

    @SneakyThrows
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser<ID>> void setDefaultHeaders(HttpHeaders builder, Optional<USER> user) {
        builder.setContentType(MediaType.APPLICATION_JSON);
        setUserInfo(builder, user);
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}