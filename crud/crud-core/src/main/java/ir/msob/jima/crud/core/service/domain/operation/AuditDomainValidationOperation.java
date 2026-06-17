package ir.msob.jima.crud.core.service.domain.operation;

import ir.msob.jima.platform.api.element.dto.BaseElementDto;
import ir.msob.jima.platform.api.embeddeddomain.model.auditdomain.AuditDomainAbstract;
import ir.msob.jima.platform.api.embeddeddomain.model.auditdomain.BaseAuditDomainContainer;
import ir.msob.jima.platform.api.exception.badrequest.BadRequestException;
import ir.msob.jima.platform.api.exception.domainnotfound.DomainNotFoundException;
import ir.msob.jima.platform.api.exception.runtime.CommonRuntimeException;
import ir.msob.jima.platform.api.operation.BaseLifecycleOperation;
import ir.msob.jima.platform.api.security.BaseUser;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 */
@Component
public class AuditDomainValidationOperation implements BaseLifecycleOperation {
    @Override
    public <ID extends Comparable<ID> & Serializable, USER extends BaseUser, DTO extends BaseElementDto<ID>> void beforeUpdate(DTO previousDto, DTO dto, USER user) throws DomainNotFoundException, BadRequestException {
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
        BaseLifecycleOperation.super.beforeUpdate(previousDto, dto, user);
    }
}
