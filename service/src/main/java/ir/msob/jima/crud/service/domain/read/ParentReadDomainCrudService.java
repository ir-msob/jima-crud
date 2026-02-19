package ir.msob.jima.crud.service.domain.read;

import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.ParentDomainCrudService;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This interface defines a parent service for reading entities based on specific criteria.
 * It provides methods that can be overridden to customize the behavior before and after the get operation.
 *
 * @param <ID>   The type of entity IDs. It must be comparable and serializable.
 * @param <USER> The type of the user associated with the operations. It must extend BaseUser.
 * @param <D>    The type of the domain entities. It must extend BaseDomain.
 * @param <DTO>  The type of the DTO (Data Transfer Object) entities. It must extend BaseDto.
 * @param <C>    The type of the criteria used for filtering entities. It must extend BaseCriteria.
 * @param <R>    The type of the CRUD repository used for data access. It must extend BaseDomainCrudRepository.
 */
public interface ParentReadDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseDomain<ID>, DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>> extends ParentDomainCrudService<ID, USER, D, DTO, C, R> {

    /**
     * Enrich a collection of DTOs by setting dependent objects from a map.
     *
     * <p>For each DTO in the given collection, this method retrieves the corresponding dependent
     * object from the provided map using the ID obtained from the DTO, and then applies the
     * provided setter function to set the dependent object into the DTO.</p>
     *
     * @param dtos              the collection of DTOs to enrich; must not be null
     * @param map               a map of IDs to dependent objects; must not be null
     * @param dependentIdGetter a function that extracts the dependent ID from a DTO
     * @param setter            a function that sets the dependent object into a DTO
     * @param <DEPENDENT>       the type of the dependent objects to be set into the DTOs
     */
    default <DEPENDENT> void enrichFromMap(
            Collection<DTO> dtos,
            Map<ID, DEPENDENT> map,
            Function<DTO, ID> dependentIdGetter,
            BiConsumer<DTO, DEPENDENT> setter
    ) {
        if (dtos == null || map == null) return;

        dtos.forEach(dto -> {
            ID dependentId = dependentIdGetter.apply(dto);
            DEPENDENT dependent = map.get(dependentId);
            if (dependent != null) {
                setter.accept(dto, dependent);
            }
        });
    }


}