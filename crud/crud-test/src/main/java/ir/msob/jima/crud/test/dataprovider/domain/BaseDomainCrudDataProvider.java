package ir.msob.jima.crud.test.dataprovider.domain;

import ir.msob.jima.crud.core.service.domain.read.BaseCountAllDomainCrudService;
import ir.msob.jima.crud.core.service.domain.write.BaseDeleteAllDomainCrudService;
import ir.msob.jima.crud.core.service.domain.write.BaseSaveDomainCrudService;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.test.dataprovider.BaseDataProvider;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

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
public interface BaseDomainCrudDataProvider<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>,
        S extends BaseCountAllDomainCrudService<ID, USER, D, DTO, C, R>
                & BaseDeleteAllDomainCrudService<ID, USER, D, DTO, C, R>
                & BaseSaveDomainCrudService<ID, USER, D, DTO, C, R>>
        extends BaseDomainCrudDataProviderParent<ID, USER, D, DTO, C, R, S> {

    /**
     * Deletes all entities from the repository.
     */
    default void cleanups() throws BadRequestException, DomainNotFoundException {
        getService().deleteAll(getSampleUser());
    }

    /**
     * Counts the total number of entities in the repository.
     *
     * @return the total entity count
     */
    default Long countDb() throws BadRequestException, DomainNotFoundException, ExecutionException,
            InterruptedException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        return getService().countAll(getSampleUser());
    }

    /**
     * Saves a new DTO in the repository.
     *
     * @return the saved DTO
     */
    default DTO saveNew() throws BadRequestException, DomainNotFoundException,
            ExecutionException, InterruptedException {
        return getService().save(getNewDto(), getSampleUser());
    }

    /**
     * Saves a new DTO with mandatory fields in the repository.
     *
     * @return the saved DTO
     */
    default DTO saveNewMandatory() throws BadRequestException, DomainNotFoundException,
            ExecutionException, InterruptedException {
        return getService().save(getMandatoryNewDto(), getSampleUser());
    }

}
