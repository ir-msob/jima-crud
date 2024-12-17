package ir.msob.jima.crud.api.restful.test.child.characteristic;

import ir.msob.jima.core.commons.child.characteristic.BaseCharacteristicContainer;
import ir.msob.jima.core.commons.child.characteristic.Characteristic;
import ir.msob.jima.core.commons.child.characteristic.CharacteristicCriteria;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.operation.OperationsStatus;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.restful.test.child.ParentChildCrudRestResourceTest;
import ir.msob.jima.crud.api.restful.test.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.child.characteristic.BaseCharacteristicCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.child.BaseChildCrudDataProvider;
import ir.msob.jima.crud.test.child.characteristic.BaseCharacteristicCrudResourceTest;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.read.BaseCountAllDomainCrudResourceTest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;

import java.io.Serializable;

/**
 * The {@code BaseCountAllDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for counting all entities.
 * It extends both the {@code BaseCountAllDomainCrudResourceTest} and {@code ParentChildCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to count all entities using RESTful API. The result of the count operation is the total number of entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <Q>    The type of query used for retrieving entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseCountAllDomainCrudResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseCharacteristicCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,

        CHILD extends Characteristic<ID>,
        CHILD_C extends CharacteristicCriteria<ID, CHILD>,
        CNT extends BaseCharacteristicContainer<ID, CHILD>,

        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID> & BaseCharacteristicContainer<ID, CHILD>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>,

        CHILD_S extends BaseCharacteristicCrudService<ID, USER, CHILD, CHILD_C, CNT, DTO>,
        CHILD_DP extends BaseChildCrudDataProvider<ID, USER, CHILD, CHILD_C, CNT, DTO, CHILD_S>>
        extends BaseCharacteristicCrudResourceTest<ID, USER, CHILD, CHILD_C, CNT, D, DTO, C, Q, R, S, DP, CHILD_S, CHILD_DP>,
        ParentChildCrudRestResourceTest<ID, USER, CHILD, CHILD_C, CNT, D, DTO, C, Q, R, S, DP, CHILD_S, CHILD_DP> {

    @Override
    default void deleteByKeyRequest(ID parentId, String key, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .delete()
                .uri(String.format("%s/%s/%s/key/%s", getBaseUri(), parentId, getElement(), key))
                .headers(this::prepareHeader)
                .exchange()
                .expectStatus()
                .isEqualTo(OperationsStatus.DELETE_BY_KEY)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

    @Override
    default void updateByKeyRequest(ID parentId, String key, @NotNull @Valid CHILD child, Assertable<DTO> assertable) {
        this.getWebTestClient()
                .put()
                .uri(String.format("%s/%s/%s/key/%s", getBaseUri(), parentId, getElement(), key))
                .headers(this::prepareHeader)
                .bodyValue(child)
                .exchange()
                .expectStatus().isEqualTo(OperationsStatus.UPDATE_BY_KEY)
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(getDtoClass())
                .value(assertable::assertThan);
    }

}