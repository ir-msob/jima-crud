package ir.msob.jima.crud.test.read;


import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import ir.msob.jima.crud.test.ParentCrudResourceTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * The {@code BaseCountCrudResourceTest} interface defines test cases for the count functionality of a CRUD resource.
 * It extends the {@code ParentCrudResourceTest} interface and provides methods to test the count operation for CRUD resources.
 * The tests include scenarios for normal count and mandatory count operations.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, Q, R, S, and DP.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser<ID>}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseCrudRepository<ID, USER, D, C, Q>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseCrudService<ID, USER, D, DTO, C, Q, R>}.
 * @param <DP>   The type of the data provider associated with the resource, extending {@code BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>}.
 * @see ParentCrudResourceTest
 */
public interface BaseCountCrudResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends ir.msob.jima.crud.test.BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends ParentCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Tests the count operation, asserting that the count is as expected.
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
    @Transactional
    default void count() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.COUNT))
            return;
        DTO savedDto = getDataProvider().saveNew();
        Long countBefore = getDataProvider().countDb();
        this.countRequest(savedDto);
        assertCount(countBefore);
    }

    /**
     * Tests the mandatory count operation, asserting that the count is as expected.
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
    @Transactional
    default void countMandatory() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.COUNT))
            return;
        DTO savedDto = getDataProvider().saveNewMandatory();
        Long countBefore = getDataProvider().countDb();
        Long count = this.countRequest(savedDto);
        Assertions.assertThat(count).isEqualTo(1);
        assertCount(countBefore);
    }

    /**
     * Executes the count operation for the CRUD resource with the specified DTO and performs assertions.
     *
     * @param savedDto The DTO for which the count operation is performed.
     * @return The count of resources associated with the specified DTO.
     * @throws BadRequestException     If the request is malformed or invalid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Long countRequest(DTO savedDto);
}
