package ir.msob.jima.crud.api.rsocket.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.channel.message.CriteriaMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.read.BaseGetManyCrudResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;

public interface BaseGetManyCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {


    @SneakyThrows
    @Override
    default Collection<DTO> getManyRequest(DTO savedDto) {
        CriteriaMessage<ID, C> message = new CriteriaMessage<>();
        message.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId()));

        return getRSocketRequester()
                .route(getUri(Operations.GET_MANY))
                .data(message)
                .retrieveMono(Collection.class)
                .toFuture()
                .get();
    }
}
