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
import ir.msob.jima.crud.test.write.BaseEditManyCrudResourceTest;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Collection;

public interface BaseEditManyCrudRestResourceTest<
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
        ParentCrudRestResourceTest<ID, USER, D, DTO, C> {
    @Override
    default Collection<DTO> editManyRequest(DTO savedDto, JsonPatch jsonPatch) {
        return this.getWebTestClient()
                .patch()
                .uri(String.format("%s/%s?%s.eq=%s", getBaseUri(), Operations.EDIT_MANY, savedDto.getDomainIdName(), savedDto.getDomainId()))
                .headers(this::prepareHeader)
                .bodyValue(jsonPatch)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.EDIT_MANY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(Collection.class)
                .returnResult()
                .getResponseBody();
    }
}
