package ir.msob.jima.crud.api.restful.test.embeddeddomain;

import ir.msob.jima.core.api.restful.test.BaseCoreRestResourceTest;
import ir.msob.jima.core.commons.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.core.commons.embeddeddomain.criteria.BaseEmbeddedCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.element.BaseElementAbstract;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.embeddeddomain.BaseEmbeddedDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.embeddeddomain.BaseEmbeddedDomainCrudDataProvider;
import ir.msob.jima.crud.test.embeddeddomain.BaseEmbeddedDomainCrudResourceTest;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Collection;

public interface BaseEmbeddedDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,

        ED extends BaseEmbeddedDomain<ID>,
        EC extends BaseEmbeddedCriteriaAbstract<ID, ED>,

        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>,
        EDS extends BaseEmbeddedDomainCrudService<ID, USER, DTO>,
        EDP extends BaseEmbeddedDomainCrudDataProvider<ID, USER, ED, DTO, EDS>>
        extends BaseCoreRestResourceTest<ID, USER, D, DTO, C>
        , BaseEmbeddedDomainCrudResourceTest<ID, USER, ED, EC, D, DTO, C, R, S, DP, EDS, EDP> {

    String getDomainUri();

    @Override
    default void updateByIdRequest(ID parentId, ID id, @NotNull @Valid ED child, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .put()
                .uri(String.format("%s/%s/%s/%s", getDomainUri(), parentId, getEmbeddedDomainUri(), id))
                .headers(this::prepareHeader)
                .bodyValue(child)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.UPDATE_BY_ID)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void updateRequest(ID parentId, EC criteria, @NotNull @Valid ED child, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .put()
                .uri(String.format("%s/%s/%s?%s.eq=%s", getDomainUri(), parentId, getEmbeddedDomainUri(), BaseElementAbstract.FN.id, criteria.getId().getEq()))
                .headers(this::prepareHeader)
                .bodyValue(child)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.UPDATE)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void updateManyRequest(ID parentId, Collection<ED> children, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .put()
                .uri(String.format("%s/%s/%s/%s", getDomainUri(), parentId, getEmbeddedDomainUri(), Operations.UPDATE_MANY))
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
                .uri(String.format("%s/%s/%s/%s", getDomainUri(), parentId, getEmbeddedDomainUri(), id))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus()
                .isEqualTo(OperationsStatus.DELETE_BY_ID)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void deleteRequest(ID parentId, EC criteria, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .delete()
                .uri(String.format("%s/%s/%s?%s.eq=%s", getDomainUri(), parentId, getEmbeddedDomainUri(), BaseElementAbstract.FN.id, criteria.getId().getEq()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus()
                .isEqualTo(OperationsStatus.DELETE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void deleteManyRequest(ID parentId, EC criteria, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .delete()
                .uri(String.format("%s/%s/%s/%s?%s.eq=%s", getDomainUri(), parentId, getEmbeddedDomainUri(), Operations.DELETE_MANY, BaseElementAbstract.FN.id, criteria.getId().getEq()))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus()
                .isEqualTo(OperationsStatus.DELETE_MANY)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void saveRequest(ID parentId, ED child, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .post()
                .uri(String.format("%s/%s/%s", getDomainUri(), parentId, getEmbeddedDomainUri()))
                .headers(this::prepareHeader)
                .bodyValue(child)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.SAVE)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void saveManyRequest(ID parentId, Collection<@Valid ED> children, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .post()
                .uri(String.format("%s/%s/%s/%s", getDomainUri(), parentId, getEmbeddedDomainUri(), Operations.SAVE_MANY))
                .headers(this::prepareHeader)
                .bodyValue(children)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.SAVE_MANY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }
}
