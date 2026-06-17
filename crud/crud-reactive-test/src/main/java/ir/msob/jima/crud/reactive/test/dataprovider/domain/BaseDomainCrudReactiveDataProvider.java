package ir.msob.jima.crud.reactive.test.dataprovider.domain;

import ir.msob.jima.crud.reactive.service.domain.read.BaseCountAllDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.service.domain.write.BaseDeleteAllDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.service.domain.write.BaseSaveDomainCrudReactiveService;
import ir.msob.jima.crud.test.dataprovider.domain.BaseDomainCrudDataProviderParent;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.dataprovider.BaseDataProvider;
import lombok.SneakyThrows;

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
public interface BaseDomainCrudReactiveDataProvider<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseCountAllDomainCrudReactiveService<ID, USER, D, DTO, C, R>
                & BaseDeleteAllDomainCrudReactiveService<ID, USER, D, DTO, C, R>
                & BaseSaveDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        >
        extends BaseDomainCrudDataProviderParent<ID, USER, D, DTO, C, R, S> {

    /**
     * Deletes all entities from the repository.
     */
    @SneakyThrows
    default void cleanups() throws BadRequestException, DomainNotFoundException {
        getService().deleteAll(getSampleUser()).toFuture().get();
    }

    /**
     * Counts the total number of entities in the repository.
     *
     * @return the total entity count
     */
    default Long countDb() throws BadRequestException, DomainNotFoundException, ExecutionException,
            InterruptedException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        return getService().countAll(getSampleUser()).toFuture().get();
    }

    /**
     * Saves a new DTO in the repository.
     *
     * @return the saved DTO
     */
    default DTO saveNew() throws BadRequestException, DomainNotFoundException,
            ExecutionException, InterruptedException {
        return getService().save(getNewDto(), getSampleUser()).toFuture().get();
    }

    /**
     * Saves a new DTO with mandatory fields in the repository.
     *
     * @return the saved DTO
     */
    default DTO saveNewMandatory() throws BadRequestException, DomainNotFoundException,
            ExecutionException, InterruptedException {
        return getService().save(getMandatoryNewDto(), getSampleUser()).toFuture().get();
    }
}
