package ir.msob.jima.crud.service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.domain.SampleCriteria;
import ir.msob.jima.core.commons.domain.SampleDomain;
import ir.msob.jima.core.commons.domain.SampleDto;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.commons.repository.BaseRepository;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.domain.BeforeAfterComponent;
import ir.msob.jima.crud.service.domain.ParentDomainCrudService;

import java.util.Collection;

public class ConcreteParentDomainCrudService implements
        ParentDomainCrudService<String,
                BaseUser,
                SampleDomain<String>,
                SampleDto<String>,
                SampleCriteria<String>,
                BaseRepository<String, SampleDomain<String>>> {


    @Override
    public BaseRepository<String, SampleDomain<String>> getRepository() {
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
    public BeforeAfterComponent getBeforeAfterComponent() {
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
