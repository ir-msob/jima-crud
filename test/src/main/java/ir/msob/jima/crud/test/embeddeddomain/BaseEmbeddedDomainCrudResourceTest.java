package ir.msob.jima.crud.test.embeddeddomain;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.core.commons.embeddeddomain.EmbeddedDomainUtil;
import ir.msob.jima.core.commons.embeddeddomain.criteria.BaseEmbeddedCriteria;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.runtime.CommonRuntimeException;
import ir.msob.jima.core.commons.filter.Filter;
import ir.msob.jima.core.commons.logger.Logger;
import ir.msob.jima.core.commons.logger.LoggerFactory;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.core.test.BaseCoreResourceTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.service.embeddeddomain.BaseEmbeddedDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

/**
 * This interface is designed for testing resources embeddeddomain to CRUD (Create, Read, Update, Delete) operations.
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
public interface BaseEmbeddedDomainCrudResourceTest<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , ED extends BaseEmbeddedDomain<ID>
        , CC extends BaseEmbeddedCriteria<ID, ED>

        , D extends BaseDomain<ID>
        , DTO extends BaseDto<ID>
        , C extends BaseCriteria<ID>
        , R extends BaseDomainCrudRepository<ID, D, C>
        , S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>
        , DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>

        , EDS extends BaseEmbeddedDomainCrudService<ID, USER, DTO>
        , EDP extends BaseEmbeddedDomainCrudDataProvider<ID, USER, ED, DTO, EDS>>
        extends BaseCoreResourceTest<ID, USER, D, DTO, C> {

    Logger logger = LoggerFactory.getLogger(BaseEmbeddedDomainCrudResourceTest.class);


    default Class<ED> getEmbeddedDomainClass() {
        return (Class<ED>) GenericTypeUtil.resolveTypeArguments(this.getClass(), BaseEmbeddedDomainCrudResourceTest.class, 2);
    }

    default Class<CC> getEmbeddedCriteriaClass() {
        return (Class<CC>) GenericTypeUtil.resolveTypeArguments(this.getClass(), BaseEmbeddedDomainCrudResourceTest.class, 3);
    }


    DP getDataProvider();

    EDP getEmbeddedDomainDataProvider();

    EDS getEmbeddedDomainService();

    S getDomainService();

    String getEmbeddedDomainUri();


    @Test
    @Rollback
    default void updateById() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.UPDATE_BY_ID))
            return;
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        getEmbeddedDomainService().save(savedDto.getId(), embeddedDomain, getEmbeddedDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            ED toUpdate = EmbeddedDomainUtil.getFunction(getEmbeddedDomainClass(), getDtoClass()).apply(dto).first();
                            getEmbeddedDomainDataProvider().updateEmbeddedDomain(toUpdate);

                            updateByIdRequest(dto.getId()
                                    , toUpdate.getId()
                                    , toUpdate
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateByIdRequest(ID parentId, ID id, @NotNull @Valid ED embeddedDomain, Assertable<DTO> assertable);

    Assertable<DTO> getUpdateAssertable();


    @Test
    @Rollback
    default void update() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.UPDATE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        getEmbeddedDomainService().save(savedDto.getId(), embeddedDomain, getEmbeddedDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            ED toUpdate = EmbeddedDomainUtil.getFunction(getEmbeddedDomainClass(), getDtoClass()).apply(dto).first();
                            CC criteria;
                            try {
                                criteria = getEmbeddedCriteriaClass().getConstructor().newInstance();
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                     NoSuchMethodException e) {
                                throw new CommonRuntimeException(e);
                            }
                            criteria.setId(Filter.eq(toUpdate.getId()));

                            getEmbeddedDomainDataProvider().updateEmbeddedDomain(toUpdate);
                            updateRequest(dto.getId()
                                    , criteria
                                    , toUpdate
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateRequest(@NotNull ID parentId, @NotNull CC criteria, @NotNull @Valid ED embeddedDomain, Assertable<DTO> assertable);


    @Test
    @Rollback
    default void updateMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.UPDATE_MANY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        getEmbeddedDomainService().save(savedDto.getId(), embeddedDomain, getEmbeddedDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            ED toUpdate = EmbeddedDomainUtil.getFunction(getEmbeddedDomainClass(), getDtoClass()).apply(dto).first();
                            getEmbeddedDomainDataProvider().updateEmbeddedDomain(toUpdate);
                            updateManyRequest(dto.getId()
                                    , Collections.singleton(toUpdate)
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateManyRequest(ID parentId, Collection<ED> embeddedDomainren, Assertable<DTO> assertable);

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
        if (ignoreTest(Operations.DELETE_BY_ID))
            return;
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        getEmbeddedDomainService().save(savedDto.getId(), embeddedDomain, getEmbeddedDomainClass(), getSampleUser())
                .subscribe(dto ->
                        deleteByIdRequest(dto.getId()
                                , EmbeddedDomainUtil.getFunction(getEmbeddedDomainClass(), getDtoClass()).apply(dto).first().getId()
                                , getDeleteAssertable())
                );
    }

    void deleteByIdRequest(ID parentId, ID id, Assertable<DTO> assertable);

    @Test
    @Rollback
    default void delete() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.DELETE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        getEmbeddedDomainService().save(savedDto.getId(), embeddedDomain, getEmbeddedDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            ED toUpdate = EmbeddedDomainUtil.getFunction(getEmbeddedDomainClass(), getDtoClass()).apply(dto).first();
                            CC criteria;
                            try {
                                criteria = getEmbeddedCriteriaClass().getConstructor().newInstance();
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
        if (ignoreTest(Operations.DELETE_MANY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        getEmbeddedDomainService().save(savedDto.getId(), embeddedDomain, getEmbeddedDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            ED toUpdate = EmbeddedDomainUtil.getFunction(getEmbeddedDomainClass(), getDtoClass()).apply(dto).first();
                            CC criteria;
                            try {
                                criteria = getEmbeddedCriteriaClass().getConstructor().newInstance();
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
        if (ignoreTest(Operations.SAVE))
            return;
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        saveRequest(savedDto.getId(), embeddedDomain, getSaveAssertable());
    }

    void saveRequest(@NotNull ID parentId, @NotNull @Valid ED embeddedDomain, Assertable<DTO> assertable);

    @Test
    @Rollback
    default void saveMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(Operations.SAVE_MANY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        saveManyRequest(savedDto.getId(), Collections.singleton(embeddedDomain), getSaveAssertable());
    }

    void saveManyRequest(@NotNull ID parentId, @NotEmpty Collection<@Valid ED> embeddedDomainren, Assertable<DTO> assertable);

    Assertable<DTO> getSaveAssertable();

}
