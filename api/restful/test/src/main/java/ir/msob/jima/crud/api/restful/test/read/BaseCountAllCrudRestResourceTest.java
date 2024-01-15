package ir.msob.jima.crud.api.restful.test.read;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.operation.Operations;
import ir.msob.jima.core.commons.model.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.api.restful.test.ParentCrudRestResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.BaseCrudDataProvider;
import ir.msob.jima.crud.test.read.BaseCountAllCrudResourceTest;

import java.io.Serializable;

public interface BaseCountAllCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseCountAllCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRestResourceTest<ID, USER, D, DTO, C> {

    @Override
    default Long countAllRequest() {
        return this.getWebTestClient()
                .get()
                .uri(String.format("%s/%s", getBaseUri(), Operations.COUNT_ALL))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.COUNT_ALL)
                .expectBody(Long.class)
                .returnResult()
                .getResponseBody();
    }
}
