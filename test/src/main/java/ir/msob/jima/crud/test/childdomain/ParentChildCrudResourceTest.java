package ir.msob.jima.crud.test.childdomain;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.ChildDomainUtil;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.runtime.CommonRuntimeException;
import ir.msob.jima.core.commons.filter.Filter;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.core.test.BaseCoreResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * This interface is designed for testing resources childdomain to CRUD (Create, Read, Update, Delete) operations.
 * It defines methods to validate and assert various aspects of CRUD operations.
 *
 * @param <ID>   The type of the entity's ID, which must be comparable and serializable.
 * @param <USER> The type of the user object, typically representing the user performing CRUD operations.
 * @param <D>    The type of the domain entity being operated on.
 * @param <DTO>  The type of the data transfer object for the entity.
 * @param <C>    The type of criteria used for filtering entities.
 * @param <R>    The type of the repository used for CRUD operations.
 * @param <S>    The type of the service used for CRUD operations.
 * @param <DP>   The type of data provider used for CRUD operations.
 */
public interface ParentChildCrudResourceTest<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , CD extends BaseChildDomain<ID>
        , CC extends BaseChildCriteria<ID, CD>

        , D extends BaseDomain<ID>
        , DTO extends BaseDto<ID>
        , C extends BaseCriteria<ID>
        , R extends BaseDomainCrudRepository<ID, D, C>
        , S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        , DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>

        , CS extends BaseChildDomainCrudService<ID, USER, DTO>
        , CDP extends BaseChildCrudDataProvider<ID, USER, CD, DTO, CS>>
        extends BaseCoreResourceTest<ID, USER, D, DTO, C> {

    Logger log = LoggerFactory.getLogger(ParentChildCrudResourceTest.class);


    default Class<CD> getChildDomainClass() {
        return (Class<CD>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentChildCrudResourceTest.class, 2);
    }

    default Class<CC> getChildCriteriaClass() {
        return (Class<CC>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentChildCrudResourceTest.class, 3);
    }


    DP getDataProvider();

    CDP getChildDataProvider();

    CS getChildService();

    S getDomainService();

    String getElement();


    @Test
    @Rollback
    default void updateById() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.UPDATE_BY_ID))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            CD toUpdate = ChildDomainUtil.getFunction(getChildDomainClass(), getDtoClass()).apply(dto).first();
                            getChildDataProvider().updateChild(toUpdate);

                            updateByIdRequest(dto.getId()
                                    , toUpdate.getId()
                                    , toUpdate
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateByIdRequest(ID parentId, ID id, @NotNull @Valid CD child, Assertable<DTO> assertable);

    Assertable<DTO> getUpdateAssertable();


    @Test
    @Rollback
    default void update() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.UPDATE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            CD toUpdate = ChildDomainUtil.getFunction(getChildDomainClass(), getDtoClass()).apply(dto).first();
                            CC criteria;
                            try {
                                criteria = getChildCriteriaClass().getConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                     NoSuchMethodException e) {
                                throw new CommonRuntimeException(e);
                            }
                            criteria.setId(Filter.eq(toUpdate.getId()));

                            getChildDataProvider().updateChild(toUpdate);
                            updateRequest(dto.getId()
                                    , criteria
                                    , toUpdate
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateRequest(@NotNull ID parentId, @NotNull CC criteria, @NotNull @Valid CD child, Assertable<DTO> assertable);


    @Test
    @Rollback
    default void updateMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.UPDATE_MANY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            CD toUpdate = ChildDomainUtil.getFunction(getChildDomainClass(), getDtoClass()).apply(dto).first();
                            getChildDataProvider().updateChild(toUpdate);
                            updateManyRequest(dto.getId()
                                    , Collections.singleton(toUpdate)
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateManyRequest(ID parentId, Collection<CD> children, Assertable<DTO> assertable);

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
    @Rollback
    default void deleteById() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.DELETE_BY_ID))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildDomainClass(), getSampleUser())
                .subscribe(dto ->
                        deleteByIdRequest(dto.getId()
                                , ChildDomainUtil.getFunction(getChildDomainClass(), getDtoClass()).apply(dto).first().getId()
                                , getDeleteAssertable())
                );
    }

    void deleteByIdRequest(ID parentId, ID id, Assertable<DTO> assertable);

    @Test
    @Rollback
    default void delete() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.DELETE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            CD toUpdate = ChildDomainUtil.getFunction(getChildDomainClass(), getDtoClass()).apply(dto).first();
                            CC criteria;
                            try {
                                criteria = getChildCriteriaClass().getConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                     NoSuchMethodException e) {
                                throw new CommonRuntimeException(e);
                            }
                            criteria.setId(Filter.eq(toUpdate.getId()));

                            deleteRequest(dto.getId()
                                    , criteria
                                    , getDeleteAssertable());
                        }
                );
    }

    void deleteRequest(@NotNull ID parentId, @NotNull CC criteria, Assertable<DTO> assertable);


    @Test
    @Rollback
    default void deleteMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.DELETE_MANY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            CD toUpdate = ChildDomainUtil.getFunction(getChildDomainClass(), getDtoClass()).apply(dto).first();
                            CC criteria;
                            try {
                                criteria = getChildCriteriaClass().getConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                     NoSuchMethodException e) {
                                throw new CommonRuntimeException(e);
                            }
                            criteria.setId(Filter.eq(toUpdate.getId()));

                            deleteManyRequest(dto.getId()
                                    , criteria
                                    , getDeleteAssertable());
                        }
                );
    }

    void deleteManyRequest(@NotNull ID parentId, @NotNull CC criteria, Assertable<DTO> assertable);

    Assertable<DTO> getDeleteAssertable();


    @Test
    @Rollback
    default void save() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.SAVE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        saveRequest(savedDto.getId(), child, getSaveAssertable());
    }

    void saveRequest(@NotNull ID parentId, @NotNull @Valid CD child, Assertable<DTO> assertable);

    @Test
    @Rollback
    default void saveMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.SAVE_MANY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        saveManyRequest(savedDto.getId(), Collections.singleton(child), getSaveAssertable());
    }

    void saveManyRequest(@NotNull ID parentId, @NotEmpty Collection<@Valid CD> children, Assertable<DTO> assertable);

    Assertable<DTO> getSaveAssertable();

}
