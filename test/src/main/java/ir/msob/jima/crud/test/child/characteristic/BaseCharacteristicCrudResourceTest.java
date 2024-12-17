package ir.msob.jima.crud.test.child.characteristic;

import ir.msob.jima.core.commons.child.characteristic.BaseCharacteristicContainer;
import ir.msob.jima.core.commons.child.characteristic.Characteristic;
import ir.msob.jima.core.commons.child.characteristic.CharacteristicCriteria;
import ir.msob.jima.core.commons.criteria.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.element.Elements;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.repository.BaseQuery;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.child.characteristic.BaseCharacteristicCrudService;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.child.BaseChildCrudDataProvider;
import ir.msob.jima.crud.test.child.ParentChildCrudResourceTest;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

/**
 * The {@code BaseDeleteDomainCrudResourceTest} interface defines test cases for the delete functionality of a CRUD resource.
 * It extends the {@code ParentDomainCrudResourceTest} interface and provides methods to test the delete operation for CRUD resources.
 * The tests include scenarios for normal delete and mandatory delete operations.
 * The interface is generic, allowing customization for different types such as ID, USER, D, DTO, C, Q, R, S, and DP.
 *
 * @param <ID>   The type of the resource ID, which should be comparable and serializable.
 * @param <USER> The type of the user associated with the resource, extending {@code BaseUser}.
 * @param <DTO>  The type of the data transfer object associated with the resource, extending {@code BaseDto<ID>}.
 * @param <C>    The type of criteria associated with the resource, extending {@code BaseCriteria<ID, USER>}.
 * @param <D>    The type of the resource domain, extending {@code BaseDomain<ID>}.
 * @param <R>    The type of the CRUD repository associated with the resource, extending {@code BaseDomainCrudRepository<ID, USER, D, C, Q>}.
 * @param <Q>    The type of the query associated with the resource, extending {@code BaseQuery}.
 * @param <S>    The type of the CRUD service associated with the resource, extending {@code BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>}.
 * @param <DP>   The type of the data provider associated with the resource, extending {@code BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>}.
 * @see ParentChildCrudResourceTest
 */
public interface BaseCharacteristicCrudResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,

        CHILD extends Characteristic<ID>,
        CHILD_C extends CharacteristicCriteria<ID, CHILD>,
        CNT extends BaseCharacteristicContainer<ID, CHILD>,

        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID> & BaseCharacteristicContainer<ID, CHILD>,
        C extends BaseCriteria<ID>,
        Q extends BaseQuery,
        R extends BaseDomainCrudRepository<ID, USER, D, C, Q>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, Q, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, Q, R, S>,

        CHILD_S extends BaseCharacteristicCrudService<ID, USER, CHILD, CHILD_C, CNT, DTO>,
        CHILD_DP extends BaseChildCrudDataProvider<ID, USER, CHILD, CHILD_C, CNT, DTO, CHILD_S>>
        extends ParentChildCrudResourceTest<ID, USER, CHILD, CHILD_C, CNT, D, DTO, C, Q, R, S, DP, CHILD_S, CHILD_DP> {


    @Test
    @Transactional
    default void deleteByKey() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.DELETE_BY_KEY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildGetter(), getSampleUser())
                .subscribe(dto ->
                        deleteByKeyRequest(dto.getId()
                                , dto.getCharacteristics().first().getKey()
                                , getDeleteAssertable())
                );
    }

    void deleteByKeyRequest(@NotNull ID parentId, @NotBlank String key, Assertable<DTO> assertable);


    @Test
    @Transactional
    default void updateByKey() throws BadRequestException, DomainNotFoundException, ExecutionException, InterruptedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (ignoreTest(getElement(), Operations.UPDATE_BY_KEY))
            return;
        DTO savedDto = getDataProvider().saveNew();
        CHILD child = getChildDataProvider().getNewChild();

        getChildService().save(savedDto.getId(), child, getChildGetter(), getSampleUser())
                .subscribe(dto -> {
                            CHILD toUpdate = dto.getCharacteristics().first();
                            getChildDataProvider().getUpdateChild(toUpdate);

                            updateByKeyRequest(dto.getId()
                                    , toUpdate.getKey()
                                    , toUpdate
                                    , getUpdateAssertable());
                        }
                );
    }

    void updateByKeyRequest(@NotNull ID parentId, @NotBlank String key, @NotNull @Valid CHILD child, Assertable<DTO> assertable);


    @Override
    default String getElement() {
        return Elements.CONTACT_MEDIUM;
    }

    @Override
    default Function<DTO, SortedSet<CHILD>> getChildGetter() {
        return DTO::getCharacteristics;
    }
}