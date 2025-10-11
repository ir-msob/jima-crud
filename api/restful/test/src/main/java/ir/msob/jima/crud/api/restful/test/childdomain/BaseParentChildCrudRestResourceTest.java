package ir.msob.jima.crud.api.restful.test.childdomain;

import ir.msob.jima.core.api.restful.test.BaseCoreRestResourceTest;
import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.element.BaseElementAbstract;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.childdomain.BaseChildCrudDataProvider;
import ir.msob.jima.crud.test.childdomain.ParentChildCrudResourceTest;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Collection;

public interface BaseParentChildCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,

        CD extends BaseChildDomain<ID>,
        CC extends BaseChildCriteriaAbstract<ID, CD>,

        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>,
        CDP extends BaseChildCrudDataProvider<ID, USER, CD, DTO, CS>>
        extends BaseCoreRestResourceTest<ID, USER, D, DTO, C>
        , ParentChildCrudResourceTest<ID, USER, CD, CC, D, DTO, C, R, S, DP, CS, CDP> {

    String getBaseUri();

    @Override
    default void updateByIdRequest(ID parentId, ID id, @NotNull @Valid CD child, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .put()
                .uri(String.format("%s/%s/%s/%s", getBaseUri(), parentId, getElement(), id))
                .headers(this::prepareHeader)
                .bodyValue(child)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.UPDATE_BY_ID)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void updateRequest(ID parentId, CC criteria, @NotNull @Valid CD child, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .put()
                .uri(String.format("%s/%s/%s?%s.eq=%s", getBaseUri(), parentId, getElement(), BaseElementAbstract.FN.id, criteria.getId().getEq()))
                .headers(this::prepareHeader)
                .bodyValue(child)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.UPDATE)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void updateManyRequest(ID parentId, Collection<CD> children, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .put()
                .uri(String.format("%s/%s/%s/%s", getBaseUri(), parentId, getElement(), Operations.UPDATE_MANY))
                .headers(this::prepareHeader)
                .bodyValue(children)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.UPDATE_MANY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);

    }

    @Override
    default void deleteByIdRequest(ID parentId, ID id, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .delete()
                .uri(String.format("%s/%s/%s/%s", getBaseUri(), parentId, getElement(), id))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus()
                .isEqualTo(OperationsStatus.DELETE_BY_ID)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void deleteRequest(ID parentId, CC criteria, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .delete()
                .uri(String.format("%s/%s/%s?%s.eq=%s", getBaseUri(), parentId, getElement(), BaseElementAbstract.FN.id, criteria.getId().getEq()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus()
                .isEqualTo(OperationsStatus.DELETE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void deleteManyRequest(ID parentId, CC criteria, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .delete()
                .uri(String.format("%s/%s/%s/%s?%s.eq=%s", getBaseUri(), parentId, getElement(), Operations.DELETE_MANY, BaseElementAbstract.FN.id, criteria.getId().getEq()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus()
                .isEqualTo(OperationsStatus.DELETE_MANY)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void saveRequest(ID parentId, CD child, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .post()
                .uri(String.format("%s/%s/%s", getBaseUri(), parentId, getElement()))
                .headers(this::prepareHeader)
                .bodyValue(child)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.SAVE)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void saveManyRequest(ID parentId, Collection<@Valid CD> children, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .post()
                .uri(String.format("%s/%s/%s/%s", getBaseUri(), parentId, getElement(), Operations.SAVE_MANY))
                .headers(this::prepareHeader)
                .bodyValue(children)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.SAVE_MANY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }
}
