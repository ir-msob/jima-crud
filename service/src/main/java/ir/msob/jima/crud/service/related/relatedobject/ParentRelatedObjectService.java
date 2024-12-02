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
        , C extends RelatedObjectCriteriaAbstract<ID, RID>
        , RO extends RelatedObjectAbstract<ID, RID>
        , RODTO extends BaseRelatedObjectDto<ID>>
        extends ParentRelatedService<ID, USER, DTO, C, RO, RODTO> {

    default Class<C> getCriteriaClass() {
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
        C criteria = getCriteriaClass().getConstructor().newInstance();
        criteria.setRelatedId(Filter.eq(relatedId));
        return update(parentId, relatedObject, criteria, getter, user);
    }

    @SneakyThrows
    @Transactional
    @MethodStats
    default Mono<DTO> deleteByRelatedId(@NotNull ID parentId, @NotNull RID relatedId, Function<DTO, SortedSet<RO>> getter, USER user) throws DomainNotFoundException, BadRequestException {
        C criteria = getCriteriaClass().getConstructor().newInstance();
        criteria.setRelatedId(Filter.eq(relatedId));
        return delete(parentId, criteria, getter, user);
    }

    default boolean isMatching(C criteria, RO relatedObject) {
        if (criteria == null || relatedObject == null) {
            return false;
        }

        if (criteria.getName() != null) {
            if (!criteria.getName().isMatching(relatedObject.getName())) return false;
        }

        if (criteria.getRelatedId() != null) {
            if (!criteria.getRelatedId().isMatching(relatedObject.getRelatedId())) return false;
        }

        if (criteria.getRole() != null) {
            if (!criteria.getRole().isMatching(relatedObject.getRole())) return false;
        }

        if (criteria.getReferringType() != null) {
            if (!criteria.getReferringType().isMatching(relatedObject.getReferringType())) return false;
        }

        if (criteria.getStatus() != null) {
            if (!criteria.getStatus().isMatching(relatedObject.getStatus())) return false;
        }

        if (criteria.getEnabled() != null) {
            if (!criteria.getEnabled().isMatching(relatedObject.getEnabled())) return false;
        }

        if (criteria.getValidFor() != null) {
            if (!criteria.getValidFor().isMatching(relatedObject.getValidFor()))
                return false;
        }

        if (criteria.getAuditInfo() != null) {
            return criteria.getAuditInfo().isMatching(relatedObject.getAuditInfo());
        }

        return true;
    }

}
