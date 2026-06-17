package ir.msob.jima.crud.restful.reactive.test.resource.embeddeddomain;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.service.embeddeddomain.BaseEmbeddedDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.dataprovider.embeddeddomain.BaseEmbeddedDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.embeddeddomain.BaseEmbeddedDomainCrudReactiveResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.element.element.BaseElementAbstract;
import ir.msob.jima.platform.api.embeddeddomain.criteria.BaseEmbeddedCriteriaAbstract;
import ir.msob.jima.platform.api.embeddeddomain.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.restful.reactive.test.resource.BaseCoreRestReactiveResourceTest;
import ir.msob.jima.platform.test.Assertable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.Collection;

public interface BaseEmbeddedDomainCrudRestReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,

        ED extends BaseEmbeddedDomain<ID>,
        EC extends BaseEmbeddedCriteriaAbstract<ID, ED>,

        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>,
        EDS extends BaseEmbeddedDomainCrudReactiveService<ID, USER, DTO>,
        EDP extends BaseEmbeddedDomainCrudReactiveDataProvider<ID, USER, ED, DTO, EDS>>
        extends BaseCoreRestReactiveResourceTest<ID, USER, D, DTO, C>
        , BaseEmbeddedDomainCrudReactiveResourceTest<ID, USER, ED, EC, D, DTO, C, R, S, DP, EDS, EDP> {

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
