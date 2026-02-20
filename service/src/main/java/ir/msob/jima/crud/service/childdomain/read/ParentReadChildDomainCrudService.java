package ir.msob.jima.crud.service.childdomain.read;

import ir.msob.jima.core.commons.childdomain.BaseChildDomain;
import ir.msob.jima.core.commons.childdomain.BaseChildDto;
import ir.msob.jima.core.commons.childdomain.criteria.BaseChildCriteria;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.childdomain.ParentChildDomainCrudService;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

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
public interface ParentReadChildDomainCrudService<ID extends Comparable<ID> & Serializable, USER extends BaseUser,
        D extends BaseChildDomain<ID>, DTO extends BaseChildDto<ID>,
        C extends BaseChildCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D, C>> extends ParentChildDomainCrudService<ID, USER, D, DTO, C, R> {

    /**
     * Enriches a collection of DTOs by setting a dependent object from the provided map.
     *
     * <p>For each DTO, the dependent ID is extracted using {@code dependentIdGetter}.
     * If a matching entry exists in the map, the {@code setter} is invoked to inject
     * the dependent object into the DTO.</p>
     *
     * <p>This method is typically used when the dependent relationship is directly
     * located on the root DTO level.</p>
     *
     * @param dtos              the DTO collection to enrich (ignored if null)
     * @param map               the map containing dependent objects keyed by ID (ignored if null)
     * @param dependentIdGetter extracts the dependent ID from a DTO
     * @param setter            sets the dependent object into the DTO
     * @param <DEPENDENT>       the type of the dependent object
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

    /**
     * Enriches nested child objects inside a collection of DTOs using a lookup map.
     *
     * <p>For each DTO, child elements are extracted using {@code childExtractor}.
     * The dependent ID is obtained from each child via {@code idGetter}.
     * If a corresponding entry exists in the map, the {@code setter} injects
     * the dependent object into the child.</p>
     *
     * <p>This method is useful for enriching nested structures such as
     * collections within DTOs (e.g. stages inside workflows).</p>
     *
     * @param dtos           the DTO collection to process (ignored if null)
     * @param map            the map containing dependent objects keyed by ID (ignored if null)
     * @param childExtractor extracts child elements from each DTO as a Stream
     * @param idGetter       extracts the dependent ID from each child
     * @param setter         sets the dependent object into the child
     * @param <CHILD>        the type of the nested element
     * @param <DEPENDENT>    the type of the dependent object
     */
    default <CHILD, DEPENDENT> void enrichNestedFromMap(
            Collection<DTO> dtos,
            Map<ID, DEPENDENT> map,
            Function<DTO, Stream<CHILD>> childExtractor,
            Function<CHILD, ID> idGetter,
            BiConsumer<CHILD, DEPENDENT> setter
    ) {
        if (dtos == null || map == null) return;

        dtos.stream()
                .filter(Objects::nonNull)
                .flatMap(childExtractor)
                .forEach(child -> {
                    ID dependentId = idGetter.apply(child);
                    if (dependentId == null) return;

                    DEPENDENT dependent = map.get(dependentId);
                    if (dependent != null) {
                        setter.accept(child, dependent);
                    }
                });
    }


}