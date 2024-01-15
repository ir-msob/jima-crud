package ir.msob.jima.crud.api.restful.test.write;

import com.github.fge.jsonpatch.JsonPatch;
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
import ir.msob.jima.crud.test.write.BaseEditCrudResourceTest;
import org.springframework.http.MediaType;

import java.io.Serializable;

public interface BaseEditCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseEditCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP>,
        ParentCrudRestResourceTest<ID, USER, D, DTO, C> {

    @Override
    default DTO editRequest(DTO savedDto, JsonPatch jsonPatch) {
        return this.getWebTestClient()
                .patch()
                .uri(String.format("%s/%s?%s.eq=%s", getBaseUri(), Operations.EDIT, savedDto.getDomainIdName(), savedDto.getDomainId()))
                .headers(this::prepareHeader)
                .bodyValue(jsonPatch)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.EDIT)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(this.getDataProvider().getService().getDtoClass())
                .returnResult()
                .getResponseBody();
    }
}
