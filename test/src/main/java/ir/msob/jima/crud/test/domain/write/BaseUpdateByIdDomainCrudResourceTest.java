package ir.msob.jima.crud.test.domain.write;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.ParentDomainCrudResourceTest;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * The {@code BaseUpdateDomainCrudResourceTest} interface defines test cases for the update functionality of a CRUD resource.
 * It extends the {@code ParentDomainCrudResourceTest} interface and provides methods to test the update operation for CRUD resources.
 * The tests include scenarios for normal update and mandatory update operations.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, Q, R, S, and DP.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseDomainCrudRepository<ID, USER, D, C, Q>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>}.
 * @param <DP>   The type of the data provider associated with the resource, extending {@code BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>}.
 * @see ParentDomainCrudResourceTest
 */
public interface BaseUpdateByIdDomainCrudResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,

        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends ParentDomainCrudResourceTest<ID, USER, D, DTO, C, Q, R, S, DP> {

    /**
     * Tests the normal update operation, asserting that the updated DTO matches the expected state.
     * This test case is designed to validate the update operation for a CRUD resource under normal conditions.
     * The test will ignore if the operation is not supported.
     * The test will save a new DTO, update it, and then assert that the count of DTOs in the database remains the same.
     * It will also assert that all fields of the updated DTO match the expected state.
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
    default void updateById() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.UPDATE_BY_ID))
            return;

        DTO savedDto = getDataProvider().saveNew();
        this.getDataProvider().updateDto(savedDto);
        Long countBefore = getDataProvider().countDb();
        updateByIdRequest(savedDto, dto -> {
            assertAll(savedDto, dto);
            assertUpdate(savedDto, dto);
        });
        assertCount(countBefore);
    }

    /**
     * Tests the mandatory update operation, asserting that the updated DTO matches the expected state.
     * This test case is designed to validate the update operation for a CRUD resource under mandatory conditions.
     * The test will ignore if the operation is not supported.
     * The test will save a new mandatory DTO, update it, and then assert that the count of DTOs in the database remains the same.
     * It will also assert that all mandatory fields of the updated DTO match the expected state.
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
    default void updateByIdMandatory() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.UPDATE_BY_ID))
            return;

        DTO savedDto = getDataProvider().saveNewMandatory();
        this.getDataProvider().updateMandatoryDto(savedDto);
        Long countBefore = getDataProvider().countDb();
        updateByIdRequest(savedDto, dto -> {
            assertMandatory(savedDto, dto);
            assertUpdate(savedDto, dto);
        });
        assertCount(countBefore);
    }

    /**
     * Executes the update operation for the CRUD resource with the specified DTO and performs assertions on the resulting DTO.
     * This method is designed to be overridden by subclasses to provide the specific implementation of the update operation.
     *
     * @param dto The DTO representing the resource to be updated.
     * @throws BadRequestException     If the request is malformed or invalid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    void updateByIdRequest(DTO dto, Assertable<DTO> assertable);
}