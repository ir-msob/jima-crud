package ir.msob.jima.crud.service.related.relatedobject;

import ir.msob.jima.core.commons.criteria.filter.Filter;
import ir.msob.jima.core.commons.dto.BaseDto;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.methodstats.MethodStats;
import ir.msob.jima.core.commons.related.relatedobject.BaseRelatedObjectDto;
import ir.msob.jima.core.commons.related.relatedobject.RelatedObjectAbstract;
import ir.msob.jima.core.commons.related.relatedobject.RelatedObjectCriteriaAbstract;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.core.commons.util.GenericTypeUtil;
import ir.msob.jima.crud.service.related.ParentRelatedService;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.function.Function;

public interface ParentRelatedObjectService<
        ID extends Comparable<ID> & Serializable
        , RID extends Comparable<RID> & Serializable
        , USER extends BaseUser
        , DTO extends BaseDto<ID>
        , RO extends RelatedObjectAbstract<ID, RID>
        , C extends RelatedObjectCriteriaAbstract<ID, RID, RO>
        , RODTO extends BaseRelatedObjectDto<ID>>
        extends ParentRelatedService<ID, USER, DTO, RO, C, RODTO> {

    default Class<C> getRelatedModelCriteriaClass() {
        return (Class<C>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentRelatedObjectService.class, 4);
    }

    default Class<RO> getRelatedObjectClass() {
        return (Class<RO>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentRelatedObjectService.class, 5);
    }

    default Class<RODTO> getRelatedObjectDtoClass() {
        return (Class<RODTO>) GenericTypeUtil.resolveTypeArguments(getClass(), ParentRelatedObjectService.class, 6);
    }


    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> updateByRelatedId(@NotNull ID parentId, @NotNull RID relatedId, RO relatedObject, Function<DTO, SortedSet<RO>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getRelatedModelCriteriaClass().getConstructor().newInstance();
        criteria.setRelatedId(Filter.eq(relatedId));
        return update(parentId, relatedObject, criteria, getter, user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> deleteByRelatedId(@NotNull ID parentId, @NotNull RID relatedId, Function<DTO, SortedSet<RO>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getRelatedModelCriteriaClass().getConstructor().newInstance();
        criteria.setRelatedId(Filter.eq(relatedId));
        return delete(parentId, criteria, getter, user);
    }
}
