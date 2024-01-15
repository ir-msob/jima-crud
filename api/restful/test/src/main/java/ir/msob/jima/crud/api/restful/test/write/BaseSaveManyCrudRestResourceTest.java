package ir.msob.jima.crud.api.restful.test.write;

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
import ir.msob.jima.crud.test.write.BaseSaveManyCrudResourceTest;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Collection;


public interface BaseSaveManyCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,

        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseSaveManyCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRestResourceTest<ID, USER, D, DTO, C> {


    @Override
    default Collection<DTO> saveManyRequest(Collection<DTO> dtos) {
        return this.getWebTestClient()
                .post()
                .uri(String.format("%s/%s", getBaseUri(), Operations.SAVE_MANY))
                .headers(this::prepareHeader)
                .bodyValue(dtos)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.SAVE_MANY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(Collection.class)
                .returnResult()
                .getResponseBody();
    }
}
