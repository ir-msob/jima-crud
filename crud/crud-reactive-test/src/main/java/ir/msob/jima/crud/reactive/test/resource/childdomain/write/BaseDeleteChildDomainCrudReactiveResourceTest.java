package ir.msob.jima.crud.reactive.test.resource.childdomain.write;

import ir.msob.jima.crud.reactive.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.reactive.test.dataprovider.childdomain.BaseChildDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.childdomain.ParentChildDomainCrudReactiveResourceTest;
import ir.msob.jima.platform.api.childdomain.childdomain.BaseChildDomain;
import ir.msob.jima.platform.api.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.platform.api.childdomain.dto.BaseChildDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code BaseDeleteChildDomainCrudReactiveResourceTest} interface defines test cases for the delete functionality of a CRUD resource.
 * It extends the {@code ParentChildDomainCrudReactiveResourceTest} interface and provides methods to test the delete operation for CRUD resources.
 * The tests include scenarios for normal delete and mandatory delete operations.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, R, S, and DP.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseChildDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseChildDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseDomainCriteria<ID, USER>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseReactiveRepository<ID, USER, D, C>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseChildDomainCrudService<ID, USER, D, DTO, C, R>}.
 * @param <DP>   The type of the data provider associated with the resource, extending {@code BaseChildDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>}.
 * @see ParentChildDomainCrudReactiveResourceTest
 */
public interface BaseDeleteChildDomainCrudReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends ParentChildDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Tests the delete operation, asserting that the returned ID is as expected.
     *
     * @throws BadRequestException       If the request is malformed or invalid.
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws ExecutionException        If an execution exception occurs.
     * @throws InterruptedException      If the execution is interrupted.
     * @throws InvocationTargetException If an invocation target exception occurs.
     * @throws NoSuchMethodException     If the specified method is not found.
     * @throws InstantiationException    If an instantiation exception occurs.
     * @throws IllegalAccessException    If an illegal access exception occurs.
     */
    @Test
    @Rollback
    default void delete() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DTO savedDto = getDataProvider().saveNew();
        ID parentId = savedDto.getParentId();
        Long countBefore = getDataProvider().countDb();
        deleteRequest(parentId, savedDto, id -> {
            assertThat(id).matches(id::equals);
            getDataProvider().assertCount(countBefore - 1);
            getDataProvider().assertDelete(id, savedDto);
        });
    }

    /**
     * Tests the mandatory delete operation, asserting that the returned ID is as expected.
     *
     * @throws BadRequestException       If the request is malformed or invalid.
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws ExecutionException        If an execution exception occurs.
     * @throws InterruptedException      If the execution is interrupted.
     * @throws InvocationTargetException If an invocation target exception occurs.
     * @throws NoSuchMethodException     If the specified method is not found.
     * @throws InstantiationException    If an instantiation exception occurs.
     * @throws IllegalAccessException    If an illegal access exception occurs.
     */
    @Test
    @Rollback
    default void deleteMandatory() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DTO savedDto = getDataProvider().saveNewMandatory();
        ID parentId = savedDto.getParentId();
        Long countBefore = getDataProvider().countDb();
        deleteRequest(parentId, savedDto, id -> {
            assertThat(id).matches(id::equals);
            getDataProvider().assertCount(countBefore - 1);
            getDataProvider().assertDelete(id, savedDto);
        });
    }

    void deleteRequest(ID parentId, DTO savedDto, Assertable<ID> assertable);
}
