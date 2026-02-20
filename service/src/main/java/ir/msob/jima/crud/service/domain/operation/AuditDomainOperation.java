package ir.msob.jima.crud.service.domain.operation;

import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.embeddeddomain.auditdomain.AuditDomainAbstract;
import ir.msob.jima.core.commons.embeddeddomain.auditdomain.AuditDomainActionType;
import ir.msob.jima.core.commons.embeddeddomain.auditdomain.BaseAuditDomainContainer;
import ir.msob.jima.core.commons.embeddeddomain.relatedobject.relatedparty.RelatedPartyAbstract;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterOperation;
import ir.msob.jima.core.commons.security.BaseUser;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 *
 */
@Component
public class AuditDomainOperation implements BaseBeforeAfterOperation {
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void beforeUpdate(DTO previousDto, DTO dto, USER user) throws DomainNotFoundException, BadRequestException {
        addAudit(dto, AuditDomainActionType.UPDATE, user);
        BaseBeforeAfterOperation.super.beforeUpdate(previousDto, dto, user);
    }

    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void beforeSave(DTO dto, USER user) throws DomainNotFoundException, BadRequestException {
        addAudit(dto, AuditDomainActionType.CREATE, user);
        BaseBeforeAfterOperation.super.beforeSave(dto, user);
    }


    /**
     * Adds audit information to a DTO.
     *
     * @param dto        The DTO to be audited.
     * @param actionType The type of audit action to record.
     * @param user       A user object.
     */
    private <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void addAudit(DTO dto, AuditDomainActionType actionType, USER user) {

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
