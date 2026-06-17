package ir.msob.jima.crud.test.dataprovider.domain;

import ir.msob.jima.crud.api.service.BaseCrudServiceParent;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.repository.BaseRepositoryParent;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.test.dataprovider.BaseDataProvider;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Generic data provider interface for CRUD operations on domain entities.
 * It extends {@link BaseDataProvider} and adds CRUD-specific utility methods.
 *
 * @param <ID>   The type of entity ID, must be {@link Comparable} and {@link Serializable}.
 * @param <USER> The type of the user performing the operation.
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the Data Transfer Object (DTO).
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of repository used for CRUD operations.
 * @param <S>    The type of service used for CRUD operations.
 */
public interface BaseDomainCrudDataProviderParent<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepositoryParent<ID, D, C>,
        S extends BaseCrudServiceParent<ID, USER, D, DTO, C, R>>
        extends BaseDataProvider<ID, USER, D, DTO, C, R, S> {

    /**
     * Deletes all entities from the repository.
     */
    void cleanups() throws BadRequestException, DomainNotFoundException;

    /**
     * Counts the total number of entities in the repository.
     *
     * @return the total entity count
     */
    Long countDb() throws BadRequestException, DomainNotFoundException, ExecutionException,
            InterruptedException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException;

    /**
     * Saves a new DTO in the repository.
     *
     * @return the saved DTO
     */
    DTO saveNew() throws BadRequestException, DomainNotFoundException,
            ExecutionException, InterruptedException;

    /**
     * Saves a new DTO with mandatory fields in the repository.
     *
     * @return the saved DTO
     */
    DTO saveNewMandatory() throws BadRequestException, DomainNotFoundException,
            ExecutionException, InterruptedException;

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
