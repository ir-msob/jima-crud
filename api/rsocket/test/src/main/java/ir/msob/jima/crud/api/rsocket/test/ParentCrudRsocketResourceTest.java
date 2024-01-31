package ir.msob.jima.crud.api.rsocket.test;

import ir.msob.jima.core.api.rsocket.commons.BaseRSocketRequesterMetadata;
import ir.msob.jima.core.api.rsocket.test.BaseCoreRsocketResourceTest;
import ir.msob.jima.core.commons.model.criteria.BaseCriteria;
import ir.msob.jima.core.commons.model.domain.BaseDomain;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.security.BaseUser;

import java.io.Serializable;

/**
 * This interface extends the BaseCoreRsocketResourceTest interface.
 * It provides a method for getting the base URI and a method for getting the URI for a specific action.
 *
 * @param <ID>   the type of the ID of the domain object, which must be comparable and serializable
 * @param <USER> the type of the user, which extends BaseUser
 * @param <D>    the type of the domain object, which extends BaseDomain
 * @param <DTO>  the type of the DTO object, which extends BaseDto
 * @param <C>    the type of the criteria object, which extends BaseCriteria
 */
public interface ParentCrudRsocketResourceTest<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser<ID>,
        D extends BaseDomain<ID>,
        DTO extends BaseDto<ID>,
        C extends BaseCriteria<ID>>
        extends BaseCoreRsocketResourceTest<ID, USER, D, DTO, C> {

    /**
     * This method should return the base URI for the RSocket resource.
     *
     * @return the base URI as a string
     */
    String getBaseUri();

    /**
     * This method returns the URI for a specific action by appending the action to the base URI.
     *
     * @param action the action for which the URI is required
     * @return the URI as a string
     */
    default String getUri(String action) {
        return String.format("%s.%s", getBaseUri(), action);
    }


    BaseRSocketRequesterMetadata getRSocketRequesterMetadata();

}