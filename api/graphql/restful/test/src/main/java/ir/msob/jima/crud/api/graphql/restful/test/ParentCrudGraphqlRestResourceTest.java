package ir.msob.jima.crud.api.graphql.restful.test;

import ir.msob.jima.core.api.graphql.test.BaseCoreGraphqlResourceTest;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.service.BaseIdService;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * The {@code ParentCrudGraphqlRestResourceTest} interface serves as a parent interface for GraphQL-specific test classes focusing on CRUD operations.
 * It extends {@link ir.msob.jima.core.api.graphql.test.BaseCoreGraphqlResourceTest}, providing additional methods and utilities tailored for testing CRUD functionality.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @see ir.msob.jima.core.api.graphql.test.BaseCoreGraphqlResourceTest
 */
public interface ParentCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>>
        extends BaseCoreGraphqlResourceTest<ID, USER, D, DTO, C> {

    /**
     * Gets the {@link ir.msob.jima.core.commons.service.BaseIdService} associated with the entity's ID type.
     *
     * @return The {@link ir.msob.jima.core.commons.service.BaseIdService} instance.
     */
    BaseIdService getIdService();

    /**
     * Converts an object to its JSON string representation.
     *
     * @param o The object to be converted.
     * @return The JSON string representation of the object.
     */
    @SneakyThrows
    default String convertToString(Object o) {
        return getObjectMapper().writeValueAsString(o);
    }

    /**
     * Converts a collection of objects to a list of their JSON string representations.
     *
     * @param result The collection of objects to be converted.
     * @return The list of JSON string representations.
     */
    default List<String> convertToStrings(Collection<?> result) {
        return result.stream()
                .map(this::convertToString)
                .toList();
    }

    /**
     * Converts a list of DTO JSON string representations to a collection of DTOs.
     *
     * @param dtos The list of DTO JSON string representations.
     * @return The collection of DTOs.
     */
    default Collection<DTO> convertToDtos(List<String> dtos) {
        return dtos.stream()
                .map(this::convertToDto)
                .toList();
    }

    /**
     * Converts a DTO JSON string representation to a DTO object.
     *
     * @param dto The DTO JSON string representation.
     * @return The DTO object.
     */
    @SneakyThrows
    default DTO convertToDto(String dto) {
        return getObjectMapper().reader().readValue(dto, getDtoClass());
    }

    /**
     * Converts a page JSON string representation to a Spring {@link org.springframework.data.domain.Page} object.
     *
     * @param page The page JSON string representation.
     * @return The Spring {@link org.springframework.data.domain.Page} object.
     */
    @SneakyThrows
    default Page<DTO> convertToPage(String page) {
        return getObjectMapper().reader().readValue(page, Page.class);
    }
}

