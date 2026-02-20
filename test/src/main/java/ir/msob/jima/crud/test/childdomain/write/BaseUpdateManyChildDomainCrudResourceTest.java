package ir.msob.jima.crud.test.childdomain.write;


import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.childdomain.BaseChildDomainCrudDataProvider;
import ir.msob.jima.crud.test.childdomain.ParentChildDomainCrudResourceTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * The {@code BaseUpdateManyChildDomainCrudResourceTest} interface defines test cases for the updateMany functionality of a CRUD resource.
 * It extends the {@code ParentChildDomainCrudResourceTest} interface and provides methods to test the updateMany operation for CRUD resources.
 * The tests include scenarios for normal updateMany and mandatory updateMany operations.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, R, S, and DP.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseChildDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseChildDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseDomainCrudRepository<ID, USER, D, C>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseChildDomainCrudService<ID, USER, D, DTO, C, R>}.
 * @param <DP>   The type of the data provider associated with the resource, extending {@code BaseChildDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>}.
 * @see ParentChildDomainCrudResourceTest
 */
public interface BaseUpdateManyChildDomainCrudResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,

        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseChildDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends ParentChildDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Tests the normal updateMany operation, asserting that the updated DTOs match the expected state.
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
    default void updateMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.UPDATE_MANY))
            return;

        DTO savedDto = getDataProvider().saveNew();
        ID parentId = savedDto.getParentId();
        DTO savedDtoForUpdate = savedDto.copy();
        this.getDataProvider().updateDto(savedDtoForUpdate);
        Long countBefore = getDataProvider().countDb();
        updateManyRequest(parentId, Collections.singleton(savedDtoForUpdate), updatedDtos -> {
            DTO updatedDto = getDataProvider().getObjectMapper().convertValue(updatedDtos.stream().findFirst().orElseThrow(DomainNotFoundException::new), getDtoClass());
            getDataProvider().assertUpdate(savedDto, updatedDto);
        });

        getDataProvider().assertCount(countBefore);
    }

    /**
     * Tests the mandatory updateMany operation, asserting that the updated DTOs match the expected state.
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
    default void updateManyMandatory() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.UPDATE_MANY))
            return;

        DTO savedDto = getDataProvider().saveNewMandatory();
        ID parentId = savedDto.getParentId();
        DTO savedDtoForUpdate = savedDto.copy();
        this.getDataProvider().updateMandatoryDto(savedDtoForUpdate);
        Long countBefore = getDataProvider().countDb();
        updateManyRequest(parentId, Collections.singleton(savedDtoForUpdate), updatedDtos -> {
            DTO updatedDto = getDataProvider().getObjectMapper().convertValue(updatedDtos.stream().findFirst().orElseThrow(DomainNotFoundException::new), getDtoClass());
            getDataProvider().assertMandatoryUpdate(savedDto, updatedDto);
        });
        getDataProvider().assertCount(countBefore);
    }

    /**
     * Executes the updateMany operation for the CRUD resource with the specified DTOs and performs assertions on the resulting DTOs.
     *
     * @param dtos The DTOs representing the resources to be updated.
     * @throws BadRequestException     If the request is malformed or invalid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    void updateManyRequest(ID parentId, Collection<DTO> dtos, Assertable<Collection<DTO>> assertable);
}
