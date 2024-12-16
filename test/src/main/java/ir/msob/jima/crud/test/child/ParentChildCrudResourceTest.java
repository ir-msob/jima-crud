package ir.msob.jima.crud.test.child;

import ir.msob.jima.core.commons.child.BaseChild;
import ir.msob.jima.core.commons.child.BaseChildCriteria;
import ir.msob.jima.core.commons.child.BaseContainer;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.core.test.BaseCoreResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.child.ParentChildService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

/**
 * This interface is designed for testing resources child to CRUD (Create, Read, Update, Delete) operations.
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
public interface ParentChildCrudResourceTest<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CHILD extends BaseChild<ID>
        , CHILD_C extends BaseChildCriteria<ID, CHILD>
        , CNT extends BaseContainer

        , D extends BaseDomain<ID>
        , DTO extends BaseDto<ID> & BaseContainer
        , C extends BaseCriteria<ID>
        , Q extends BaseQuery
        , R extends BaseDomainCrudRepository<ID, USER, D, C, Q>
        , S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>
        , DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>

        , CHILD_S extends ParentChildService<ID, USER, CHILD, CHILD_C, CNT, DTO>
        , CHILD_DP extends BaseChildCrudDataProvider<ID, USER, CHILD, CHILD_C, CNT, DTO, CHILD_S>>
        extends BaseCoreResourceTest<ID, USER, D, DTO, C> {

    Logger log = LoggerFactory.getLogger(ParentChildCrudResourceTest.class);

    default Class<CHILD_C> getChildCriteriaClass() {
        return (Class<CHILD_C>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentChildCrudResourceTest.class, 3);
    }

    DP getDataProvider();

    CHILD_DP getChildDataProvider();

    CHILD_S getChildService();

    S getDomainService();

    Function<DTO, SortedSet<CHILD>> getChildGetter();

    String getElement();


    @Test
    @Transactional
    default void updateById() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.UPDATE_BY_ID))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildGetter(), getSampleUser())
                .subscribe(dto ->
                        updateByIdRequest(dto.getId()
                                , getChildGetter().apply(dto).first().getId()
                                , getUpdateAssertable())
                );
    }

    void updateByIdRequest(ID parentId, ID id, Assertable<DTO> assertable);

    Assertable<DTO> getUpdateAssertable();


    @Test
    @Transactional
    default void update() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.UPDATE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildGetter(), getSampleUser())
                .subscribe(dto -> {
                            CHILD toUpdate = getChildGetter().apply(dto).first();
                            CHILD_C criteria;
                            try {
                                criteria = getChildCriteriaClass().getConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                     NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                            criteria.setId(Filter.eq(toUpdate.getId()));

                            getChildDataProvider().getUpdateChild(toUpdate);
                            updateRequest(dto.getId()
                                    , criteria
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateRequest(@NotNull ID parentId, @NotNull CHILD_C criteria, Assertable<DTO> assertable);


    @Test
    @Transactional
    default void updateMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.UPDATE_MANY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildGetter(), getSampleUser())
                .subscribe(dto -> {
                            CHILD toUpdate = getChildGetter().apply(dto).first();
                            getChildDataProvider().getUpdateChild(toUpdate);
                            updateManyRequest(dto.getId()
                                    , Collections.singleton(toUpdate)
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateManyRequest(ID parentId, Collection<CHILD> children, Assertable<DTO> assertable);

    /**
     * Tests the delete operation, asserting that the returned ID is as expected.
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
    default void deleteById() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.DELETE_BY_ID))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildGetter(), getSampleUser())
                .subscribe(dto ->
                        deleteByIdRequest(dto.getId()
                                , getChildGetter().apply(dto).first().getId()
                                , getDeleteAssertable())
                );
    }

    void deleteByIdRequest(ID parentId, ID id, Assertable<DTO> assertable);

    @Test
    @Transactional
    default void delete() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.DELETE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildGetter(), getSampleUser())
                .subscribe(dto -> {
                            CHILD toUpdate = getChildGetter().apply(dto).first();
                            CHILD_C criteria;
                            try {
                                criteria = getChildCriteriaClass().getConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                     NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                            criteria.setId(Filter.eq(toUpdate.getId()));

                            deleteRequest(dto.getId()
                                    , criteria
                                    , getDeleteAssertable());
                        }
                );
    }

    void deleteRequest(@NotNull ID parentId, @NotNull CHILD_C criteria, Assertable<DTO> assertable);


    @Test
    @Transactional
    default void deleteMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.DELETE_MANY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildGetter(), getSampleUser())
                .subscribe(dto -> {
                            CHILD toUpdate = getChildGetter().apply(dto).first();
                            CHILD_C criteria;
                            try {
                                criteria = getChildCriteriaClass().getConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                     NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                            criteria.setId(Filter.eq(toUpdate.getId()));

                            deleteManyRequest(dto.getId()
                                    , criteria
                                    , getDeleteAssertable());
                        }
                );
    }

    void deleteManyRequest(@NotNull ID parentId, @NotNull CHILD_C criteria, Assertable<DTO> assertable);

    Assertable<DTO> getDeleteAssertable();


    @Test
    @Transactional
    default void save() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.SAVE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        saveRequest(savedDto.getId(), child, getSaveAssertable());
    }

    void saveRequest(@NotNull ID parentId, @NotNull @Valid CHILD child, Assertable<DTO> assertable);

    @Test
    @Transactional
    default void saveMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.SAVE_MANY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        saveManyRequest(savedDto.getId(), Collections.singleton(child), getSaveAssertable());
    }

    void saveManyRequest(@NotNull ID parentId, @NotEmpty Collection<@Valid CHILD> children, Assertable<DTO> assertable);

    Assertable<DTO> getSaveAssertable();

}
