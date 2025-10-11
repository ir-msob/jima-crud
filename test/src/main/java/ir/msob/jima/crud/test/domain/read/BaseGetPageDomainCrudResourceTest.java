package ir.msob.jima.crud.test.domain.read;


import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.ParentDomainCrudResourceTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * The {@code BaseGetPageDomainCrudResourceTest} interface defines test cases for the getPage functionality of a CRUD resource.
 * It extends the {@code ParentDomainCrudResourceTest} interface and provides methods to test the getPage operation for CRUD resources.
 * The tests include scenarios for normal getPage and mandatory getPage operations.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, R, S, and DP.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseDomainCrudRepository<ID, USER, D, C>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseDomainCrudService<ID, USER, D, DTO, C, R>}.
 * @param <DP>   The type of the data provider associated with the resource, extending {@code BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>}.
 * @see ParentDomainCrudResourceTest
 */
public interface BaseGetPageDomainCrudResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends ParentDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Tests the getPage operation, asserting that the returned Page of DTOs is as expected.
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
    default void getPage() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.GET_PAGE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        Long countBefore = getDataProvider().countDb();
        getPageRequest(savedDto, dtos -> {
            if (dtos == null) throw new DomainNotFoundException();
            DTO dto = getDataProvider().getObjectMapper().convertValue(dtos.getContent().stream().findFirst().orElseThrow(DomainNotFoundException::new), getDtoClass());
            getDataProvider().assertGet(getDataProvider().getNewDto(), dto);
        });
        getDataProvider().assertCount(countBefore);
    }

    /**
     * Tests the mandatory getPage operation, asserting that the returned Page of DTOs is as expected.
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
    default void getPageMandatory() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.GET_PAGE))
            return;
        DTO savedDto = getDataProvider().saveNewMandatory();
        Long countBefore = getDataProvider().countDb();
        getPageRequest(savedDto, dtos -> {
            if (dtos == null) throw new DomainNotFoundException();
            DTO dto = getDataProvider().getObjectMapper().convertValue(dtos.getContent().stream().findFirst().orElseThrow(DomainNotFoundException::new), getDtoClass());
            getDataProvider().assertMandatoryGet(this.getDataProvider().getMandatoryNewDto(), dto);
        });
        getDataProvider().assertCount(countBefore);
    }


    void getPageRequest(DTO savedDto, Assertable<Page<DTO>> assertable);
}
