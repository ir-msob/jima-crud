package ir.msob.jima.crud.reactive.test.resource.embeddeddomain;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.service.embeddeddomain.BaseEmbeddedDomainCrudReactiveService;
import ir.msob.jima.crud.reactive.test.dataprovider.domain.BaseDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.dataprovider.embeddeddomain.BaseEmbeddedDomainCrudReactiveDataProvider;
import ir.msob.jima.crud.reactive.test.resource.BaseCrudReactiveResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.embeddeddomain.EmbeddedDomainUtil;
import ir.msob.jima.platform.api.embeddeddomain.criteria.BaseEmbeddedCriteria;
import ir.msob.jima.platform.api.embeddeddomain.embeddeddomain.BaseEmbeddedDomain;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.exception.runtime.CommonRuntimeException;
import ir.msob.jima.platform.api.filter.Filter;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.util.GenericTypeUtil;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;
import ir.msob.jima.platform.test.Assertable;
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
public interface BaseEmbeddedDomainCrudReactiveResourceTest<
        ID extends Comparable<ID> & Serializable
        , USER extends BaseUser
        , ED extends BaseEmbeddedDomain<ID>
        , CC extends BaseEmbeddedCriteria<ID, ED>

        , D extends BaseDomain<ID>
        , DTO extends BaseDomainDto<ID>
        , C extends BaseDomainCriteria<ID>
        , R extends BaseReactiveRepository<ID, D, C>
        , S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>
        , DP extends BaseDomainCrudReactiveDataProvider<ID, USER, D, DTO, C, R, S>

        , EDS extends BaseEmbeddedDomainCrudReactiveService<ID, USER, DTO>
        , EDP extends BaseEmbeddedDomainCrudReactiveDataProvider<ID, USER, ED, DTO, EDS>>
        extends BaseCrudReactiveResourceTest<ID, USER, D, DTO, C> {

    Logger logger = LoggerFactory.getLogger(BaseEmbeddedDomainCrudReactiveResourceTest.class);


    default Class<ED> getEmbeddedDomainClass() {
        return (Class<ED>) GenericTypeUtil.resolveTypeArguments(this.getClass(), BaseEmbeddedDomainCrudReactiveResourceTest.class, 2);
    }

    default Class<CC> getEmbeddedCriteriaClass() {
        return (Class<CC>) GenericTypeUtil.resolveTypeArguments(this.getClass(), BaseEmbeddedDomainCrudReactiveResourceTest.class, 3);
    }


    DP getDataProvider();

    EDP getEmbeddedDomainDataProvider();

    EDS getEmbeddedDomainService();

    S getDomainService();

    String getEmbeddedDomainUri();


    @Test
    @Rollback
    default void updateById() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        saveRequest(savedDto.getId(), embeddedDomain, getSaveAssertable());
    }

    void saveRequest(@NotNull ID parentId, @NotNull @Valid ED embeddedDomain, Assertable<DTO> assertable);

    @Test
    @Rollback
    default void saveMany() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DTO savedDto = getDataProvider().saveNew();
        ED embeddedDomain = getEmbeddedDomainDataProvider().getNewEmbeddedDomain();

        saveManyRequest(savedDto.getId(), Collections.singleton(embeddedDomain), getSaveAssertable());
    }

    void saveManyRequest(@NotNull ID parentId, @NotEmpty Collection<@Valid ED> embeddedDomainren, Assertable<DTO> assertable);

    Assertable<DTO> getSaveAssertable();

}
