package ir.msob.jima.crud.restful.test.resource.domain.write;

import ir.msob.jima.crud.core.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.restful.test.resource.domain.ParentDomainCrudRestResourceTest;
import ir.msob.jima.crud.test.dataprovider.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.resource.domain.write.BaseSaveManyDomainCrudResourceTest;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.logger.Logger;
import ir.msob.jima.platform.api.logger.LoggerFactory;
import ir.msob.jima.platform.api.operation.Operations;
import ir.msob.jima.platform.api.operation.OperationsStatus;
import ir.msob.jima.platform.api.repository.BaseRepository;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.test.Assertable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The {@code BaseSaveManyChildDomainCrudRestResourceTest} interface represents a set of RESTful-specific test methods for saving multiple entities.
 * It extends both the {@code BaseSaveManyChildDomainCrudResourceTest} and {@code BaseEmbeddedDomainCrudRestResourceTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to save multiple entities using RESTful API. The result of the save operation is a collection of DTOs of the saved entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseSaveManyDomainCrudResourceTest
 * @see ParentDomainCrudRestResourceTest
 */
public interface BaseSaveManyDomainCrudRestResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseRepository<ID, D, C>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseSaveManyDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudRestResourceTest<ID, USER, D, DTO, C> {

    Logger log = LoggerFactory.getLogger(BaseSaveManyDomainCrudRestResourceTest.class);

    /**
     * Executes a RESTful request to save multiple entities and extracts the result from the response.
     *
     * @param dtos The collection of data transfer objects (DTOs) representing the entities to be saved.
     */
    @Override
    default void saveManyRequest(Collection<DTO> dtos, Assertable<Collection<DTO>> assertable) {

        HttpHeaders headers = new HttpHeaders();
        prepareHeader(headers);

        HttpEntity<Collection<DTO>> requestEntity = new HttpEntity<>(dtos, headers);

        log.info("➡️ REQUEST URL: {}/{}", getBaseUri(), Operations.SAVE_MANY);
        log.info("➡️ REQUEST HEADERS: {}", headers);
        log.info("➡️ REQUEST BODY: {}", dtos);

        ResponseEntity<Collection<DTO>> response = this.getTestRestTemplate()
                .exchange(
                        String.format("%s/%s", getBaseUri(), Operations.SAVE_MANY),
                        HttpMethod.POST,
                        requestEntity,
                        cast(getDtosReferenceType())
                );

        log.info("⬅️ RESPONSE STATUS: {}", response.getStatusCode());
        log.info("⬅️ RESPONSE HEADERS: {}", response.getHeaders());
        log.info("⬅️ RESPONSE BODY: {}", response.getBody());

        assertThat(response.getStatusCode().value())
                .isEqualTo(OperationsStatus.SAVE_MANY);

        assertable.assertThan(response.getBody());
    }
}