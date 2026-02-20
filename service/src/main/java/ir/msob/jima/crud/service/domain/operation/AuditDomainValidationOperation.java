package ir.msob.jima.crud.service.domain.operation;

import ir.msob.jima.core.commons.domain.BaseDto;
import ir.msob.jima.core.commons.embeddeddomain.auditdomain.AuditDomainAbstract;
import ir.msob.jima.core.commons.embeddeddomain.auditdomain.BaseAuditDomainContainer;
import ir.msob.jima.core.commons.exception.badrequest.BadRequestException;
import ir.msob.jima.core.commons.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.core.commons.exception.runtime.CommonRuntimeException;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterOperation;
import ir.msob.jima.core.commons.security.BaseUser;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 */
@Component
public class AuditDomainValidationOperation implements BaseBeforeAfterOperation {
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseDto<ID>> void beforeUpdate(DTO previousDto, DTO dto, USER user) throws DomainNotFoundException, BadRequestException {
        if (dto instanceof BaseAuditDomainContainer<?, ?, ?> baseAuditDomainContainer) {
            if (previousDto instanceof BaseAuditDomainContainer<?, ?, ?> previousAuditDomainContainer) {
                AuditDomainAbstract<?, ?> auditDomainAbstract = baseAuditDomainContainer.getAuditDomains().last();
                AuditDomainAbstract<?, ?> previousAuditDomainAbstract = previousAuditDomainContainer.getAuditDomains().last();
                if (auditDomainAbstract != null && previousAuditDomainAbstract != null) {
                    if (previousAuditDomainAbstract.getVersion().compareTo(auditDomainAbstract.getVersion()) > 0) {
                        throw new CommonRuntimeException("Dto version validation failed, previous version {}, current version {} ", previousAuditDomainAbstract.getVersion(), auditDomainAbstract.getVersion());
                    }
                }
            }
        }
        BaseBeforeAfterOperation.super.beforeUpdate(previousDto, dto, user);
    }
}
