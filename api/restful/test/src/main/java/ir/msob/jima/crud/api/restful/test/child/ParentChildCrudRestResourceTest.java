package ir.msob.jima.crud.api.restful.test.child;

import ir.msob.jima.core.api.restful.test.BaseCoreRestResourceTest;
import ir.msob.jima.core.commons.child.BaseChildAbstract;
import ir.msob.jima.core.commons.child.BaseChildCriteriaAbstract;
import ir.msob.jima.core.commons.child.BaseContainer;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.element.BaseElementAbstract;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.child.ParentChildCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.child.BaseChildCrudDataProvider;
import ir.msob.jima.crud.test.child.ParentChildCrudResourceTest;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Collection;

public interface ParentChildCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,

        CHILD extends BaseChildAbstract<ID>,
        CHILD_C extends BaseChildCriteriaAbstract<ID, CHILD>,
        CNT extends BaseContainer,

        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID> & BaseContainer,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>,

        CHILD_S extends ParentChildCrudService<ID, USER, CHILD, CHILD_C, CNT, DTO>, // TODO:
        CHILD_DP extends BaseChildCrudDataProvider<ID, USER, CHILD, CHILD_C, CNT, DTO, CHILD_S>>
        extends BaseCoreRestResourceTest<ID, USER, D, DTO, C>
        , ParentChildCrudResourceTest<ID, USER, CHILD, CHILD_C, CNT, D, DTO, C, Q, R, S, DP, CHILD_S, CHILD_DP> {

    String getBaseUri();


    @Override
    default void updateByIdRequest(ID parentId, ID id, @NotNull @Valid CHILD child, Assertable<DTO> assertable) {
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
    default void updateRequest(ID parentId, CHILD_C criteria, @NotNull @Valid CHILD child, Assertable<DTO> assertable) {
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
    default void updateManyRequest(ID parentId, Collection<CHILD> children, Assertable<DTO> assertable) {
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
    default void deleteRequest(ID parentId, CHILD_C criteria, Assertable<DTO> assertable) {
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
    default void deleteManyRequest(ID parentId, CHILD_C criteria, Assertable<DTO> assertable) {
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
    default void saveRequest(ID parentId, CHILD child, Assertable<DTO> assertable) {
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
    default void saveManyRequest(ID parentId, Collection<@Valid CHILD> children, Assertable<DTO> assertable) {
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
