package ir.msob.jima.crud.test;

import ir.msob.jima.core.commons.data.BaseQuery;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.operation.ConditionalOnOperationUtil;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.BaseCoreResourceTest;
import ir.msob.jima.crud.commons.BaseCrudRepository;
import ir.msob.jima.crud.service.BaseCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This interface is designed for testing resources related to CRUD (Create, Read, Update, Delete) operations.
 * It defines methods to validate and assert various aspects of CRUD operations.
 *
 * @param <ID>   The type of the entity's ID, which must be comparable and serializable.
 * @param <USER> The type of the user object, typically representing the user performing CRUD operations.
 * @param <D>    The type of the domain entity being operated on.
 * @param <DTO>  The type of the data transfer object for the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <Q>    The type of query object used in data retrieval.
 * @param <R>    The type of the repository used for CRUD operations.
 * @param <S>    The type of the service used for CRUD operations.
 * @param <DP>   The type of data provider used for CRUD operations.
 */
public interface ParentCrudResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>>
        extends BaseCoreResourceTest<ID, USER, D, DTO, C> {

    Logger log = LoggerFactory.getLogger(ParentCrudResourceTest.class);


    /**
     * Determines whether a specific CRUD operation should be ignored for testing.
     *
     * @param operation The CRUD operation to check.
     * @return True if the operation should be ignored, false otherwise.
     */
    default boolean ignoreTest(String operation) {
        boolean result = !ConditionalOnOperationUtil.hasOperation(operation, getResourceClass());
        if (result) {
            log.info("Perform {} test for {} is ignored.", operation, getResourceClass().getSimpleName());
        }
        return result;
    }

    /**
     * Get the data provider associated with this test.
     *
     * @return The data provider used for CRUD operations.
     */
    DP getDataProvider();

    /**
     * Asserts all aspects of a CRUD operation before and after.
     *
     * @param before The DTO before the operation.
     * @param after  The DTO after the operation.
     */
    default void assertAll(DTO before, DTO after) {
        // Implement assertions for all aspects of CRUD operation here.
    }

    /**
     * Asserts mandatory aspects of a CRUD operation before and after.
     *
     * @param before The DTO before the operation.
     * @param after  The DTO after the operation.
     */
    default void assertMandatory(DTO before, DTO after) {
        // Implement assertions for mandatory aspects of CRUD operation here.
    }

    /**
     * Asserts the saving of a DTO before and after the operation.
     *
     * @param before The DTO before the operation.
     * @param after  The DTO after the operation.
     */
    default void assertSave(DTO before, DTO after) {
        // Implement assertions for saving a DTO operation here.
    }

    /**
     * Asserts the update of a DTO before and after the operation.
     *
     * @param before The DTO before the operation.
     * @param after  The DTO after the operation.
     */
    default void assertUpdate(DTO before, DTO after) {
        // Implement assertions for updating a DTO operation here.
    }

    /**
     * Asserts the deletion of a DTO before the operation.
     *
     * @param before The DTO before the operation.
     */
    default void assertDelete(DTO before) {
        // Implement assertions for deleting a DTO operation here.
    }

    /**
     * Asserts the retrieval of a DTO before and after the operation.
     *
     * @param before The DTO before the operation.
     * @param after  The DTO after the operation.
     */
    default void assertGet(DTO before, DTO after) {
        // Implement assertions for retrieving a DTO operation here.
    }

    /**
     * Asserts the count of entities in the database.
     *
     * @param expectedCount The expected count of entities.
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws ExecutionException        If an execution exception occurs.
     * @throws BadRequestException       If there is a bad request.
     * @throws InterruptedException      If the operation is interrupted.
     * @throws InvocationTargetException If an invocation target exception occurs.
     * @throws NoSuchMethodException     If a specific method is not found.
     * @throws InstantiationException    If an instance cannot be created.
     * @throws IllegalAccessException    If there is illegal access to a method or field.
     */
    default void assertCount(Long expectedCount) throws DomainNotFoundException, ExecutionException, BadRequestException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Long currentCount = getDataProvider().countDb();
        assertThat(currentCount).isEqualTo(expectedCount);
    }
}
