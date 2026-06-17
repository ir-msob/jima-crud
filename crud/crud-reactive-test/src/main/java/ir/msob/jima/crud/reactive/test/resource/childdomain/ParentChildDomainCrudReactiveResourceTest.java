package ir.msob.jima.crud.reactive.test.resource.childdomain;

import ir.msob.jima.crud.reactive.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.reactive.test.dataprovider.childdomain.BaseChildDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.BaseCrudReactiveResourceTest;
import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

/**
 * This interface is designed for testing resources childdomain to CRUD (Create, Read, Update, Delete) operations.
 * It defines methods to validate and assert various aspects of CRUD operations.
 *
 * @param <ID>   The type of the entity's ID, which must be comparable and serializable.
 * @param <USER> The type of the user object, typically representing the user performing CRUD operations.
 * @param <D>    The type of the domain entity being operated on.
 * @param <DTO>  The type of the data transfer object for the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of the repository used for CRUD operations.
 * @param <S>    The type of the service used for CRUD operations.
 * @param <DP>   The type of data provider used for CRUD operations.
 */
public interface ParentChildDomainCrudReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseCrudReactiveResourceTest<ID, USER, D, DTO, C> {

    /**
     * Get the data provider associated with this test.
     *
     * @return The data provider used for CRUD operations.
     */
    DP getDataProvider();

}
