package ir.msob.jima.crud.test.childdomain;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.BaseCoreDataProvider;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Generic data provider interface for CRUD operations on domain entities.
 * It extends {@link BaseCoreDataProvider} and adds CRUD-specific utility methods.
 *
 * @param <ID>   The type of entity ID, must be {@link Comparable} and {@link Serializable}.
 * @param <USER> The type of the user performing the operation.
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the Data Transfer Object (DTO).
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 * @param <S>    The type of service used for CRUD operations.
 */
public interface BaseChildDomainCrudDataProvider<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseChildDomain<ID>,
        DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseChildDomainCrudService<ID, USER, D, DTO, C, R>>
        extends BaseCoreDataProvider<ID, USER, D, DTO, C, R, S> {

    /**
     * Deletes all entities from the repository.
     */
    default void cleanups() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        getService().doDelete(getCriteriaClass().getConstructor().newInstance(),getSampleUser()).toFuture().get();
    }

    /**
     * Counts the total number of entities in the repository.
     *
     * @return the total entity count
     */
    default Long countDb() throws BadRequestException, DomainNotFoundException, ExecutionException,
            InterruptedException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        return getService().countAll(getNewDto().getParentId(),getSampleUser()).toFuture().get();
    }

    /**
     * Saves a new DTO in the repository.
     *
     * @return the saved DTO
     */
    default DTO saveNew() throws BadRequestException, DomainNotFoundException,
            ExecutionException, InterruptedException {
        return getService().save(getNewDto().getParentId(),getNewDto(), getSampleUser()).toFuture().get();
    }

    /**
     * Saves a new DTO with mandatory fields in the repository.
     *
     * @return the saved DTO
     */
    default DTO saveNewMandatory() throws BadRequestException, DomainNotFoundException,
            ExecutionException, InterruptedException {
        return getService().save(getMandatoryNewDto().getParentId(),getMandatoryNewDto(), getSampleUser()).toFuture().get();
    }

    /**
     * Asserts the correctness of the save operation.
     *
     * @param dto      the DTO before saving
     * @param savedDto the DTO after saving
     */
    default void assertSave(DTO dto, DTO savedDto) {
        // Implement save assertions
    }

    /**
     * Asserts the correctness of the save operation for mandatory fields.
     *
     * @param dto      the DTO before saving
     * @param savedDto the DTO after saving
     */
    default void assertMandatorySave(DTO dto, DTO savedDto) {
        // Implement mandatory save assertions
    }

    /**
     * Asserts the correctness of the update operation.
     *
     * @param dto        the DTO before update
     * @param updatedDto the DTO after update
     */
    default void assertUpdate(DTO dto, DTO updatedDto) {
        // Implement update assertions
    }

    /**
     * Asserts the correctness of the update operation for mandatory fields.
     *
     * @param dto        the DTO before update
     * @param updatedDto the DTO after update
     */
    default void assertMandatoryUpdate(DTO dto, DTO updatedDto) {
        // Implement mandatory update assertions
    }

    /**
     * Asserts the correctness of the retrieval operation.
     *
     * @param before the DTO before retrieval
     * @param after  the DTO after retrieval
     */
    default void assertGet(DTO before, DTO after) {
        // Implement get assertions
    }

    /**
     * Asserts the correctness of the retrieval operation for mandatory fields.
     *
     * @param before the DTO before retrieval
     * @param after  the DTO after retrieval
     */
    default void assertMandatoryGet(DTO before, DTO after) {
        // Implement get assertions
    }

    /**
     * Asserts the correctness of the delete operation.
     *
     * @param id       the ID of the deleted entity
     * @param savedDto the DTO before deletion
     */
    default void assertDelete(ID id, DTO savedDto) {
        assertThat(id).isEqualTo(savedDto.getId());
    }

    /**
     * Asserts that the repository contains the expected number of entities.
     *
     * @param expectedCount the expected count
     */
    @SneakyThrows
    default void assertCount(Long expectedCount) {
        Long currentCount = countDb();
        assertThat(currentCount).isEqualTo(expectedCount);
    }
}
