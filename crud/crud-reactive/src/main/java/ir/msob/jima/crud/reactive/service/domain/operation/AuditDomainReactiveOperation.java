package ir.msob.jima.crud.reactive.service.domain.operation;

import ir.msob.jima.platform.api.element.dto.BaseElementDto;
import ir.msob.jima.platform.api.embeddeddomain.model.auditdomain.AuditDomainAbstract;
import ir.msob.jima.platform.api.embeddeddomain.model.auditdomain.AuditDomainActionType;
import ir.msob.jima.platform.api.embeddeddomain.model.auditdomain.BaseAuditDomainContainer;
import ir.msob.jima.platform.api.embeddeddomain.model.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.security.BaseUser;
import ir.msob.jima.platform.reactive.operation.BaseReactiveLifecycleOperation;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 *
 */
@Component
public class AuditDomainReactiveOperation implements BaseReactiveLifecycleOperation {
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseElementDto<ID>> Mono<@NonNull Void> beforeUpdate(DTO previousDto, DTO dto, USER user) throws DomainNotFoundException, BadRequestException {
        addAudit(dto, AuditDomainActionType.UPDATE, user);
        return BaseReactiveLifecycleOperation.super.beforeUpdate(previousDto, dto, user);
    }

    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseElementDto<ID>> Mono<@NonNull Void> beforeSave(DTO dto, USER user) throws DomainNotFoundException, BadRequestException {
        addAudit(dto, AuditDomainActionType.CREATE, user);
        return BaseReactiveLifecycleOperation.super.beforeSave(dto, user);
    }


    /**
     * Adds audit information to a DTO.
     *
     * @param dto        The DTO to be audited.
     * @param actionType The type of audit action to record.
     * @param user       A user object.
     */
    private <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseElementDto<ID>> void addAudit(DTO dto, AuditDomainActionType actionType, USER user) {

        RelatedPartyAbstract<ID> relatedParty = new RelatedPartyAbstract<>() {
        };
        relatedParty.setRelatedId(user.getId());
        relatedParty.setName(user.getName());

        AuditDomainAbstract<ID, RelatedPartyAbstract<ID>> auditDomain = new AuditDomainAbstract<>() {
        };
        auditDomain.setActionDate(Instant.now());
        auditDomain.setActionType(actionType.name());
        auditDomain.setRelatedParty(relatedParty);
        auditDomain.setVersion(String.valueOf(new Date().getTime()));

        if (dto instanceof BaseAuditDomainContainer auditDomainContainer) {
            auditDomainContainer.getAuditDomains().add(auditDomain);
        }
    }
}
