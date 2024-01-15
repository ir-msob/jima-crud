package ir.msob.jima.crud.api.grpc.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.crud.api.grpc.commons.CriteriaPageableMsg;
import ir.msob.jima.crud.api.grpc.commons.PageMsg;
import ir.msob.jima.crud.api.grpc.test.ParentCrudGrpcResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseGetPageCrudResourceTest;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

import java.io.Serializable;

public interface BaseGetPageCrudGrpcResourceTest<
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
        ParentCrudGrpcResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    @SneakyThrows
    @Override
    default Page<DTO> getPageRequest(DTO savedDto) throws DomainNotFoundException, BadRequestException {
        CriteriaPageableMsg msg = CriteriaPageableMsg.newBuilder()
                .setCriteria(convertToString(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getDomainId())))
                .setPageable(convertToString(PageRequest.of(0, 10)))
                .build();
        PageMsg res = getReactorCrudServiceStub().getPage(Mono.just(msg))
                .toFuture()
                .get();
        return convertToPage(res.getPage());
    }
}
