package ir.msob.jima.crud.test.childdomain.objectvalidation;

import ir.msob.jima.core.commons.childdomain.ChildDomainUtil;
import ir.msob.jima.core.commons.childdomain.objectvalidation.ObjectValidationAbstract;
import ir.msob.jima.core.commons.childdomain.objectvalidation.ObjectValidationCriteriaAbstract;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.element.Elements;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.BaseChildDomainCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.childdomain.BaseChildCrudDataProvider;
import ir.msob.jima.crud.test.childdomain.ParentChildCrudResourceTest;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

/**
 * The {@code BaseDeleteDomainCrudResourceTest} interface defines test cases for the delete functionality of a CRUD resource.
 * It extends the {@code ParentDomainCrudResourceTest} interface and provides methods to test the delete operation for CRUD resources.
 * The tests include scenarios for normal delete and mandatory delete operations.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, R, S, and DP.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseDomainCrudRepository<ID, USER, D, C>}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseDomainCrudService<ID, USER, D, DTO, C, R>}.
 * @param <DP>   The type of the data provider associated with the resource, extending {@code BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>}.
 * @see ParentChildCrudResourceTest
 */
public interface BaseObjectValidationCrudResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,

        CD extends ObjectValidationAbstract<ID>,
        CC extends ObjectValidationCriteriaAbstract<ID, CD>,

        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>,
        CS extends BaseChildDomainCrudService<ID, USER, DTO>,
        CDP extends BaseChildCrudDataProvider<ID, USER, CD, DTO, CS>>
        extends ParentChildCrudResourceTest<ID, USER, CD, CC, D, DTO, C, R, S, DP, CS, CDP> {


    @Test
    @Rollback
    default void deleteByName() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.DELETE_BY_NAME))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildDomainClass(), getSampleUser())
                .subscribe(dto ->
                        deleteByNameRequest(dto.getId()
                                , ChildDomainUtil.getFunction(getChildDomainClass(), getDtoClass()).apply(dto).first().getName()
                                , getDeleteAssertable())
                );
    }

    void deleteByNameRequest(@NotNull ID parentId, @NotBlank String key, Assertable<DTO> assertable);


    @Test
    @Rollback
    default void updateByName() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.UPDATE_BY_NAME))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildDomainClass(), getSampleUser())
                .subscribe(dto -> {
                            CD toUpdate = ChildDomainUtil.getFunction(getChildDomainClass(), getDtoClass()).apply(dto).first();
                            getChildDataProvider().updateChild(toUpdate);
                            updateByNameRequest(dto.getId()
                                    , toUpdate.getName()
                                    , toUpdate
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateByNameRequest(@NotNull ID parentId, @NotBlank String key, @NotNull @Valid CD child, Assertable<DTO> assertable);

    @Override
    default String getElement() {
        return Elements.OBJECT_VALIDATION;
    }

}