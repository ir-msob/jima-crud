package ir.msob.jima.graphql.restful.reactive.test.domain;

import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.id.BaseIdService;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.shared.PageDto;
import ir.msob.jima.platform.graphql.test.BaseCoreGraphqlResourceTest;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * The {@code ParentDomainCrudGraphqlRestResourceTest} interface serves as a parent interface for GraphQL-specific test classes focusing on CRUD operations.
 * It extends {@link BaseCoreGraphqlResourceTest}, providing additional methods and utilities tailored for testing CRUD functionality.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @see BaseCoreGraphqlResourceTest
 */
public interface ParentDomainCrudGraphqlRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>>
        extends BaseCoreGraphqlResourceTest<ID, USER, D, DTO, C> {

    /**
     * Gets the {@link BaseIdService} associated with the entity's ID type.
     *
     * @return The {@link BaseIdService} instance.
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
     * Converts a page JSON string representation to a Spring {@link PageDto} object.
     *
     * @param page The page JSON string representation.
     * @return The Spring {@link PageDto} object.
     */
    @SneakyThrows
    default PageDto<DTO> convertToPage(String page) {
        return getObjectMapper().reader().readValue(page, PageDto.class);
    }
}

