package ir.msob.jima.crud.kafka.reactive.resource.domain;

import ir.msob.jima.crud.reactive.service.domain.BaseDomainCrudReactiveService;
import ir.msob.jima.platform.api.channel.BaseChannelTypeReference;
import ir.msob.jima.platform.api.domain.criteria.BaseDomainCriteria;
import ir.msob.jima.platform.api.domain.domain.BaseDomain;
import ir.msob.jima.platform.api.domain.dto.BaseDomainDto;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.api.util.GenericTypeUtil;
import ir.msob.jima.platform.kafka.reactive.event.client.BaseKafkaReactiveListener;
import ir.msob.jima.platform.reactive.channel.ChannelMessageReactivePublisher;
import ir.msob.jima.platform.reactive.repository.BaseReactiveRepository;

import java.io.Serializable;

/**
 * Interface for a listener that handles CRUD operations.
 *
 * @param <ID>   The type of the ID, which must be Comparable and Serializable.
 * @param <USER> The type of the User, which must extend BaseUser.
 * @param <D>    The type of the Domain, which must extend BaseDomain.
 * @param <DTO>  The type of the DTO, which must extend BaseDomainDto.
 * @param <C>    The type of the Criteria, which must extend BaseDomainCriteria.
 * @param <R>    The type of the Repository, which must extend BaseReactiveRepository.
 * @param <S>    The type of the Service, which must extend BaseChildDomainCrudService.
 */
public interface ParentDomainCrudKafkaListener<
        ID extends Comparable<ID> & Serializable,
        USER extends BaseUser,
        D extends BaseDomain<ID>,
        DTO extends BaseDomainDto<ID>,
        C extends BaseDomainCriteria<ID>,
        R extends BaseReactiveRepository<ID, D, C>,
        S extends BaseDomainCrudReactiveService<ID, USER, D, DTO, C, R>>
        extends BaseKafkaReactiveListener<ID, USER>,
        BaseChannelTypeReference<ID, USER, DTO, C> {

    /**
     * Returns the service that handles the CRUD operations.
     *
     * @return The service.
     */
    S getService();

    ChannelMessageReactivePublisher getChannelMessagePublisher();

    /**
     * Returns the class of the domain.
     *
     * @return The class of the domain.
     */
    default Class<D> getDomainClass() {
        return (Class<D>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentDomainCrudKafkaListener.class, 2);
    }

    /**
     * Returns the class of the DTO.
     *
     * @return The class of the DTO.
     */
    default Class<DTO> getDtoClass() {
        return (Class<DTO>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentDomainCrudKafkaListener.class, 3);
    }

    /**
     * Returns the class of the criteria.
     *
     * @return The class of the criteria.
     */
    default Class<C> getCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentDomainCrudKafkaListener.class, 4);
    }

    /**
     * Returns the class of the repository.
     *
     * @return The class of the repository.
     */
    default Class<R> getRepositoryClass() {
        return (Class<R>) GenericTypeUtil.resolveTypeArguments(this.getClass(), ParentDomainCrudKafkaListener.class, 5);
    }

}