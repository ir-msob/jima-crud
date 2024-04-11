package ir.msob.jima.crud.test.write;


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
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * The {@code BaseSaveManyCrudResourceTest} interface defines test cases for the saveMany functionality of a CRUD resource.
 * It extends the {@code ParentCrudResourceTest} interface and provides methods to test the saveMany operation for CRUD resources.
 * The tests include scenarios for normal saveMany and mandatory saveMany operations.
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
public interface BaseSaveManyCrudResourceTest<
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
     * Tests the normal saveMany operation, asserting that the saved DTOs match the expected state.
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
    default void saveMany() throws ExecutionException, InterruptedException, BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.SAVE_MANY))
            return;

        Long countBefore = getDataProvider().countDb();
        Collection<DTO> dtos = saveManyRequest(Collections.singleton(this.getDataProvider().getNewDto()));
        assertCount(countBefore + 1);
        DTO dto = getDataProvider().getObjectMapper().convertValue(dtos.stream().findFirst().orElseThrow(DomainNotFoundException::new), getDtoClass());
        assertAll(this.getDataProvider().getNewDto(), dto);
        assertSave(this.getDataProvider().getNewDto(), dto);
    }

    /**
     * Tests the mandatory saveMany operation, asserting that the saved DTOs match the expected state.
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
    default void saveManyMandatory() throws ExecutionException, InterruptedException, BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.SAVE_MANY))
            return;

        Long countBefore = getDataProvider().countDb();
        Collection<DTO> dtos = saveManyRequest(Collections.singleton(this.getDataProvider().getMandatoryNewDto()));
        assertCount(countBefore + 1);
        DTO dto = getDataProvider().getObjectMapper().convertValue(dtos.stream().findFirst().orElseThrow(DomainNotFoundException::new), getDtoClass());
        assertMandatory(this.getDataProvider().getMandatoryNewDto(), dto);
        assertSave(this.getDataProvider().getMandatoryNewDto(), dto);
    }

    /**
     * Executes the saveMany operation for the CRUD resource with the specified DTOs and performs assertions on the resulting DTOs.
     *
     * @param dtos The collection of DTOs representing the new resources to be saved.
     * @return The saved DTOs.
     * @throws BadRequestException     If the request is malformed or invalid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    Collection<DTO> saveManyRequest(Collection<DTO> dtos);
}
