package ir.msob.jima.crud.rsocket.reactive.test.resource.domain.read;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseCountDomainCrudReactiveResourceTest;
import ir.msob.jima.crud.rsocket.reactive.test.resource.domain.ParentDomainCrudRsocketResourceTest;
import ir.msob.jima.platform.api.channel.ChannelMessage;
import ir.msob.jima.platform.api.channel.message.CriteriaMessage;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.util.CriteriaUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * The {@code BaseCountDomainCrudRsocketReactiveResourceTest} interface represents a set of RSocket-specific test methods for counting entities.
 * It extends both the {@code BaseCountChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to count entities using RSocket API. The result of the count operation is the total number of entities that match the provided criteria.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountDomainCrudReactiveResourceTest
 * @see ParentDomainCrudRsocketResourceTest
 */
public interface BaseCountDomainCrudRsocketReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseCountDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RSocket request to count entities and extracts the result from the response.
     * The entities to be counted are determined by the criteria provided in the DTO.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be counted.
     */
    @SneakyThrows
    @Override
    default void countRequest(DTO savedDto, Assertable<Long> assertable) {
        // Create a new CriteriaMessage
        // Set the criteria of the message to the ID criteria of the DTO
        // Send a RSocket request to the COUNT operation URI with the CriteriaMessage as data
        // Retrieve the result as a Mono of type Long
        // Convert the Mono to a Future
        // Get the result from the Future
        CriteriaMessage<ID, C> data = new CriteriaMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId()));

        ChannelMessage<USER, CriteriaMessage<ID, C>> message = ChannelMessage.<USER, CriteriaMessage<ID, C>>builder()
                .data(data)
                .build();

        getRSocketRequester()
                .route(getUri(Operations.COUNT))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(Long.class)
                .doOnSuccess(assertable::assertThan)
                .toFuture()
                .get();
    }
}