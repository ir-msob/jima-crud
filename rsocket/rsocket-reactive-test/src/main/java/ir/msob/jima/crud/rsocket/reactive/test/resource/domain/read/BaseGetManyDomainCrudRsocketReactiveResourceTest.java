package ir.msob.jima.crud.rsocket.reactive.test.resource.domain.read;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.read.BaseGetManyDomainCrudReactiveResourceTest;
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
import java.util.Collection;

/**
 * The {@code BaseGetManyDomainCrudRsocketReactiveResourceTest} interface represents a set of RSocket-specific test methods for retrieving multiple entities.
 * It extends both the {@code BaseGetManyChildDomainCrudReactiveResourceTest} and {@code ParentDomainCrudRsocketResourceTest} interfaces, providing RSocket-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve multiple entities using RSocket API. The entities to be retrieved are determined by the criteria provided in the DTO.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetManyDomainCrudReactiveResourceTest
 * @see ParentDomainCrudRsocketResourceTest
 */
public interface BaseGetManyDomainCrudRsocketReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetManyDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RSocket request to retrieve multiple entities and extracts the result from the response.
     * The entities to be retrieved are determined by the criteria provided in the DTO.
     *
     * @param savedDto The data transfer object (DTO) representing the entity to be retrieved.
     */
    @SneakyThrows
    @Override
    default void getManyRequest(DTO savedDto, Assertable<Collection<DTO>> assertable) {
        // Create a new CriteriaMessage
        // Set the criteria of the message to the ID criteria of the DTO
        // Send a RSocket request to the GET_MANY operation URI with the CriteriaMessage as data
        // Retrieve the result as a Mono of type Collection
        // Convert the Mono to a Future
        // Get the result from the Future
        CriteriaMessage<ID, C> data = new CriteriaMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId()));

        ChannelMessage<USER, CriteriaMessage<ID, C>> message = ChannelMessage.<USER, CriteriaMessage<ID, C>>builder()
                .data(data)
                .build();

        getRSocketRequester()
                .route(getUri(Operations.GET_MANY))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(Collection.class)
                .doOnSuccess(assertable::assertThan)
                .toFuture()
                .get();
    }
}