package ir.msob.jima.crud.api.kafka.test.domain.read;

import ir.msob.jima.core.commons.channel.ChannelMessage;
import ir.msob.jima.core.commons.channel.message.PageableMessage;
import ir.msob.jima.core.commons.domain.BaseCriteria;
import ir.msob.jima.core.commons.domain.BaseDomain;
import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.Operations;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.shared.ModelType;
import ir.msob.jima.core.commons.shared.PageDto;
import ir.msob.jima.core.commons.shared.PageableDto;
import ir.msob.jima.core.commons.util.CriteriaUtil;
import ir.msob.jima.core.test.Assertable;
import ir.msob.jima.crud.api.kafka.test.domain.ParentDomainCrudKafkaListenerTest;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.BaseDomainCrudService;
import ir.msob.jima.crud.test.domain.BaseDomainCrudDataProvider;
import ir.msob.jima.crud.test.domain.read.BaseGetPageDomainCrudResourceTest;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;

/**
 * The {@code BaseGetPageDomainCrudKafkaListenerTest} interface represents a set of RESTful-specific test methods for retrieving a page of entities.
 * It extends both the {@code BaseGetPageDomainCrudResourceTest} and {@code ParentDomainCrudKafkaListenerTest} interfaces, providing RESTful-specific testing capabilities.
 * <p>
 * The interface includes an implementation for making a request to retrieve a page of entities using RESTful API. The result of the get operation is a page of DTOs of the retrieved entities.
 *
 * @param <ID>   The type of entity ID.
 * @param <USER> The type of the user (security context).
 * @param <D>    The type of the domain entity.
 * @param <DTO>  The type of the data transfer object (DTO) for the entity.
 * @param <C>    The type of criteria used for querying entities.
 * @param <R>    The type of the CRUD repository.
 * @param <S>    The type of the CRUD service.
 * @param <DP>   The type of data provider for CRUD testing.
 * @see BaseGetPageDomainCrudResourceTest
 * @see ParentDomainCrudKafkaListenerTest
 */
public interface BaseGetPageDomainCrudKafkaListenerTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>,
        R extends BaseDomainCrudRepository<ID, D>,
        S extends BaseDomainCrudService<ID, USER, D, DTO, C, R>,
        DP extends BaseDomainCrudDataProvider<ID, USER, D, DTO, C, R, S>>
        extends BaseGetPageDomainCrudResourceTest<ID, USER, D, DTO, C, R, S, DP>,
        ParentDomainCrudKafkaListenerTest<ID, USER, D, DTO, C> {

    /**
     * Executes a RESTful request to retrieve a page of entities and extracts the result from the response.
     *
     * @param savedDto The data transfer object (DTO) representing the entities to be retrieved.
     * @throws DomainNotFoundException If the entity is not found.
     * @throws BadRequestException     If the request is not valid.
     */
    @SneakyThrows
    @Override
    default void getPageRequest(DTO savedDto, Assertable<PageDto<DTO>> assertable) throws DomainNotFoundException, BadRequestException {
        // Send a GET request to the GET_PAGE operation URI with the ID of the entities to be retrieved
        // Prepare the request header
        // Expect the status to be equal to the GET_PAGE operation status
        // Expect the content type to be JSON
        // Expect the body to be of type Page
        // Return the response body
        String topic = prepareTopic(Operations.GET_PAGE);
        PageableMessage<ID, C> data = new PageableMessage<>();
        data.setCriteria(CriteriaUtil.idCriteria(getCriteriaClass(), savedDto.getId()));
        data.setPageable(PageableDto.from(PageRequest.of(0, Integer.MAX_VALUE)));

        ChannelMessage<USER, PageableMessage<ID, C>> channelMessage = ChannelMessage.<USER, PageableMessage<ID, C>>builder()
                .user(getSampleUser())
                .data(data)
                .callback(ChannelMessage.<USER, ModelType>builder()
                        .channel(prepareCallbackTopic())
                        .build())
                .build();

        String message = getObjectMapper().writeValueAsString(channelMessage);
        getKafkaTemplate().executeInTransaction(operations -> operations.send(topic, message));

        startListener(channelMessage.getCallbacks().getFirst().getChannel(), s -> assertable.assertThan(cast(s, getChannelMessagePageReferenceType()).getData().getPage()));

    }
}