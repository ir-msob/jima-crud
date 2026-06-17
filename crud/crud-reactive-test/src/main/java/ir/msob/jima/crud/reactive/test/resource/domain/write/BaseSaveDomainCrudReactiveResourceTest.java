package ir.msob.jima.crud.reactive.test.resource.domain.write;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.domain.ParentDomainCrudReactiveResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
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

/**
 * The {@code BaseSaveChildDomainCrudReactiveResourceTest} interface defines test cases for the save functionality of a CRUD resource.
 * It extends the {@code ParentChildDomainCrudReactiveResourceTest} interface and provides methods to test the save operation for CRUD resources.
 * The tests include scenarios for normal save and mandatory save operations.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, R, S, and DP.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDomainDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseDomainCriteria<ID, USER>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseReactiveRepository<ID, USER, D, C>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseChildDomainCrudService<ID, USER, D, DTO, C, R>}.
 * @param <DP>   The type of the data provider associated with the resource, extending {@code BaseChildDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>}.
 * @see ParentDomainCrudReactiveResourceTest
 */
public interface BaseSaveDomainCrudReactiveResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>>
        extends ParentDomainCrudReactiveResourceTest<ID, USER, D, DTO, C, R, S, DP> {

    /**
     * Tests the normal save operation, asserting that the saved DTO matches the expected state.
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
    default void save() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Long countBefore = getDataProvider().countDb();
        saveRequest(this.getDataProvider().getNewDto(), savedDto -> {
            getDataProvider().assertSave(this.getDataProvider().getNewDto(), savedDto);
            getDataProvider().assertCount(countBefore + 1);
        });
    }

    /**
     * Tests the mandatory save operation, asserting that the saved DTO matches the expected state.
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
    default void saveMandatory() throws ExecutionException, InterruptedException, BadRequestException, DomainNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Long countBefore = getDataProvider().countDb();
        saveRequest(this.getDataProvider().getMandatoryNewDto(),
                savedDto -> {
                    getDataProvider().assertMandatorySave(this.getDataProvider().getMandatoryNewDto(), savedDto);
                    getDataProvider().assertCount(countBefore + 1);
                });
    }

    /**
     * Executes the save operation for the CRUD resource with the specified DTO and performs assertions on the resulting DTO.
     *
     * @param dto The DTO representing the new resource to be saved.
     * @throws BadRequestException     If the request is malformed or invalid.
     * @throws DomainNotFoundException If the domain is not found.
     */
    void saveRequest(DTO dto, Assertable<DTO> assertable);
}
