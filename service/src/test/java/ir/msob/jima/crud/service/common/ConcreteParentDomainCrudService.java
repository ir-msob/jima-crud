package ir.msob.jima.crud.service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.domain.SampleCriteria;
import ir.msob.jima.core.commons.domain.SampleDomain;
import ir.msob.jima.core.commons.domain.SampleDto;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.commons.domain.BaseDomainCrudRepository;
import ir.msob.jima.crud.service.domain.ParentDomainCrudService;
import ir.msob.jima.crud.service.domain.operation.BeforeAfterOperationComponent;

import java.util.Collection;

public class ConcreteParentDomainCrudService implements
        ParentDomainCrudService<String,
                BaseUser,
                SampleDomain<String>,
                SampleDto<String>,
                SampleCriteria<String>,
                BaseDomainCrudRepository<String, SampleDomain<String>, SampleCriteria<String>>> {


    @Override
    public BaseDomainCrudRepository<String, SampleDomain<String>, SampleCriteria<String>> getRepository() {
        return null;
    }

    @Override
    public SampleDto<String> toDto(SampleDomain<String> domain, BaseUser user) {
        return null;
    }

    @Override
    public SampleDomain<String> toDomain(SampleDto<String> dto, BaseUser user) {
        return null;
    }

    @Override
    public BeforeAfterOperationComponent getBeforeAfterOperationComponent() {
        return null;
    }

    @Override
    public Collection<BaseBeforeAfterDomainOperation<String, BaseUser, SampleDto<String>, SampleCriteria<String>>> getBeforeAfterDomainOperations() {
        return null;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return null;
    }
}
