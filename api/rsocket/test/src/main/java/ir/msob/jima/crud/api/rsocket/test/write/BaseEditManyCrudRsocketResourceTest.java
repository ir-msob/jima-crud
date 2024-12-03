package ir.msob.jima.crud.api.rsocket.test.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.JsonPatchMessage;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseEditManyCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface extends the BaseEditManyCrudResourceTest and ParentCrudRsocketResourceTest interfaces.
 * It provides a method for editing multiple resources via RSocket.
 *
 * @param <ID>   the type of the ID of the domain object, which must be comparable and serializable
 * @param <USER> the type of the user, which extends BaseUser
 * @param <D>    the type of the domain object, which extends BaseDomain
 * @param <DTO>  the type of the DTO object, which extends BaseDto
 * @param <C>    the type of the criteria object, which extends BaseCriteria
 * @param <Q>    the type of the query object, which extends BaseQuery
 * @param <R>    the type of the repository object, which extends BaseCrudRepository
 * @param <S>    the type of the service object, which extends BaseCrudService
 * @param <DP>   the type of the data provider object, which extends BaseCrudDataProvider
 */
public interface BaseEditManyCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * This method sends an edit request for multiple resources via RSocket.
     * It creates a JsonPatchMessage with the provided DTO and JsonPatch, and sends it to the specified route.
     *
     * @param savedDto  the DTO object that represents the saved state of the resources to be edited
     * @param jsonPatch the JsonPatch object that represents the changes to be applied to the resources
     */
    @SneakyThrows
    @Override
    default void editManyRequest(DTO savedDto, JsonPatch jsonPatch, Assertable<Collection<DTO>> assertable) {
        JsonPatchMessage<ID, C> data = new JsonPatchMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId()));
        data.setJsonPatch(jsonPatch);

        ChannelMessage<USER, JsonPatchMessage<ID, C>> message = ChannelMessage.<USER, JsonPatchMessage<ID, C>>builder()
                .data(data)
                .build();

        getRSocketRequester()
                .route(getUri(Operations.EDIT_MANY))
                .metadata(getRSocketRequesterMetadata()::metadata)
                .data(getObjectMapper().writeValueAsString(message))
                .retrieveMono(Collection.class)
                .doOnSuccess(assertable::assertThan)
                .toFuture()
                .get();
    }
}