package ir.msob.jima.crud.test.childdomain.relatedobject.relatedintegration;

import ir.msob.jima.core.commons.childdomain.relatedobject.relatedintegration.RelatedIntegrationAbstract;
import ir.msob.jima.core.commons.childdomain.relatedobject.relatedintegration.RelatedIntegrationCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.element.Elements;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.childdomain.BaseChildCrudDataProvider;
import ir.msob.jima.crud.test.childdomain.ParentChildCrudResourceTest;
import ir.msob.jima.crud.test.childdomain.relatedobject.BaseRelatedObjectCrudResourceTest;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;

import java.io.Serializable;

/**
 * The {@code BaseDeleteDomainCrudResourceTest} interface defines test cases for the delete functionality of a CRUD resource.
 * It extends the {@code ParentDomainCrudResourceTest} interface and provides methods to test the delete operation for CRUD resources.
 * The tests include scenarios for normal delete and mandatory delete operations.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, Q, R, S, and DP.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseDomainCrudRepository<ID, USER, D, C, Q>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>}.
 * @param <DP>   The type of the data provider associated with the resource, extending {@code BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>}.
 * @see ParentChildCrudResourceTest
 */
public interface BaseRelatedIntegrationCrudResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,

        CD extends RelatedIntegrationAbstract<ID>,
        CC extends RelatedIntegrationCriteriaAbstract<ID, CD>,

        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>,

        CS extends BaseChildDomainCrudService<ID, USER, DTO>,
        CDP extends BaseChildCrudDataProvider<ID, USER, CD, DTO, CS>>
        extends BaseRelatedObjectCrudResourceTest<ID, String, USER, CD, CC, D, DTO, C, Q, R, S, DP, CS, CDP> {


    @Override
    default String getElement() {
        return Elements.RELATED_INTEGRATION;
    }

}