package ir.msob.jima.crud.api.rsocket.test.write;

import com.github.fge.jsonpatch.JsonPatch;
import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.message.JsonPatchMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.write.BaseEditManyCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

public interface BaseEditManyCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {


    @SneakyThrows
    @Override
    default Collection<DTO> editManyRequest(DTO savedDto, JsonPatch jsonPatch) {
        JsonPatchMessage<ID, C> message = new JsonPatchMessage<>();
        message.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId()));
        message.setJsonPatch(jsonPatch);

        return getRSocketRequester()
                .route(getUri(Operations.EDIT_MANY))
                .data(message)
                .retrieveMono(Collection.class)
                .toFuture()
                .get();
    }
}
