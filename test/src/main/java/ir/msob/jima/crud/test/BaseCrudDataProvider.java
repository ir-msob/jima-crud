package ir.msob.jima.crud.test;

import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.BaseCoreDataProvider;
import ir.msob.jima.crud.commons.domain.BaseCrudRepository;
import ir.msob.jima.crud.service.domain.BaseCrudService;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * This interface is designed to provide data-related methods for CRUD operations.
 * It extends the BaseDataProvider interface, which includes common data provider methods.
 *
 * @param <ID>   The type of the entity's ID, which must be comparable and serializable.
 * @param <USER> The type of the user object, typically representing the user performing CRUD operations.
 * @param <D>    The type of the domain entity being operated on.
 * @param <DTO>  The type of the data transfer object for the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <Q>    The type of query object used in data retrieval.
 * @param <R>    The type of the repository used for CRUD operations.
 * @param <S>    The type of the service used for CRUD operations.
 */
public interface BaseCrudDataProvider<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseCrudRepository<ID, USER, D, C, Q>,
        S extends BaseCrudService<ID, USER, D, DTO, C, Q, R>>
        extends BaseCoreDataProvider<ID, USER, D, DTO, C, R, S> {

    /**
     * This method performs cleanup operations by deleting all entities in the repository.
     *
     * @throws BadRequestException       If there is a bad request.
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws ExecutionException        If an execution exception occurs.
     * @throws InterruptedException      If the operation is interrupted.
     * @throws NoSuchMethodException     If a specific method is not found.
     * @throws InstantiationException    If an instance cannot be created.
     * @throws IllegalAccessException    If there is illegal access to a method or field.
     * @throws InvocationTargetException If an invocation target exception occurs.
     */
    default void cleanups() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        getService().deleteAll(getSampleUser())
                .toFuture()
                .get();
    }

    /**
     * This method counts the number of entities in the database.
     *
     * @return The count of entities in the database.
     * @throws BadRequestException       If there is a bad request.
     * @throws DomainNotFoundException   If the domain is not found.
     * @throws ExecutionException        If an execution exception occurs.
     * @throws InterruptedException      If the operation is interrupted.
     * @throws InvocationTargetException If an invocation target exception occurs.
     * @throws NoSuchMethodException     If a specific method is not found.
     * @throws InstantiationException    If an instance cannot be created.
     * @throws IllegalAccessException    If there is illegal access to a method or field.
     */
    default Long countDb() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return getService()
                .countAll(getSampleUser())
                .toFuture()
                .get();
    }

    /**
     * This method saves a new data transfer object (DTO) in the database.
     *
     * @return The DTO that was saved.
     * @throws BadRequestException     If there is a bad request.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws ExecutionException      If an execution exception occurs.
     * @throws InterruptedException    If the operation is interrupted.
     */
    default DTO saveNew() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException {
        return getService()
                .save(getNewDto(), getSampleUser())
                .toFuture()
                .get();
    }

    /**
     * This method saves a new mandatory data transfer object (DTO) in the database.
     * The "mandatory" nature of the DTO may imply that certain fields must be provided.
     *
     * @return The DTO that was saved.
     * @throws BadRequestException     If there is a bad request.
     * @throws DomainNotFoundException If the domain is not found.
     * @throws ExecutionException      If an execution exception occurs.
     * @throws InterruptedException    If the operation is interrupted.
     */
    default DTO saveNewMandatory() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException {
        return getService()
                .save(getMandatoryNewDto(), getSampleUser())
                .toFuture()
                .get();
    }
}
