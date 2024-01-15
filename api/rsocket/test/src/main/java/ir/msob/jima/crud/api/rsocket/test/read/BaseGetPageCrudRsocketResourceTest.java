package ir.msob.jima.crud.api.rsocket.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.channel.message.PageableMessage;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.rsocket.test.ParentCrudRsocketResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseGetPageCrudResourceTest;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;

public interface BaseGetPageCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseGetPageCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRsocketResourceTest<ID, USER, D, DTO, C> {


    @SneakyThrows
    @Override
    default Page<DTO> getPageRequest(DTO savedDto) throws DomainNotFoundException, BadRequestException {
        PageableMessage<ID, C> message = new PageableMessage<>();
        message.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId()));
        message.setPageable(PageRequest.of(0, 10));

        return getRSocketRequester()
                .route(getUri(Operations.GET_PAGE))
                .data(message)
                .retrieveMono(Page.class)
                .toFuture()
                .get();
    }
}
