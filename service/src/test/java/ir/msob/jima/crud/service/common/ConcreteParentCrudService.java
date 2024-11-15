package ir.msob.jima.crud.service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.data.BaseRepository;
import ir.msob.jima.core.commons.model.criteria.SampleCriteria;
import ir.msob.jima.core.commons.model.domain.SampleDomain;
import ir.msob.jima.core.commons.model.dto.SampleDto;
import ir.msob.jima.core.commons.operation.BaseBeforeAfterDomainOperation;
import ir.msob.jima.core.commons.security.BaseUser;
import ir.msob.jima.crud.service.BeforeAfterComponent;
import ir.msob.jima.crud.service.ParentCrudService;

import java.util.Collection;

public class ConcreteParentCrudService implements
        ParentCrudService<String,
                BaseUser,
                SampleDomain<String>,
                SampleDto<String>,
                SampleCriteria<String>,
                BaseRepository<String,
                        BaseUser,
                        SampleDomain<String>>> {


    @Override
    public BaseRepository<String, BaseUser, SampleDomain<String>> getRepository() {
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
